# 消费者端服务降级和熔断

## 降级初始化

### 引入依赖

```xml
        <!--hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```

### 应用入口启动降级

```java
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableHystrix
public class OrderFeignHystrixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignHystrixMain80.class, args);
    }
}

```

## 控制层服务降级

#### 在需要进行降级保护的方法中使用@HystrixCommand

```java

public class OrderHystirxController {

    @Resource
    private PaymentHystrixService paymentHystrixService;
 
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(/**指定回调方法，fallbackMethod方法的前面要和此方法的签名一致**/fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id)
    {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id)
    {
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
}
    
```

## hystrix配置控制层默认降级方法

### 默认降级配置

在控制类中应用@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")和需要降级保护的方法中应用
@HystrixCommand()注解

```java
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystirxController {
    @HystrixCommand()
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id)
    {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }


    // 下面是全局fallback方法
    public String payment_Global_FallbackMethod()
    {
        return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
    }
}
```

## feign对hystrix的支持

### 在配置中启动feign的hystrix支持

```yaml
  
feign:
  hystrix:
    enabled: true
```

## 实现FeignClient接口的类，该类的所有实现方法都作为接口对应方法的fallback方法

```java

@Service
@FeignClient(value = "CLOUD-PAYMENT-SERVICE",fallback = PaymentHystrixFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable(value = "id") Integer id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable(value = "id") Integer id);
}

@Service
public class PaymentHystrixFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id)
    {
        return "-----PaymentFallbackService fall back-paymentInfo_OK ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id)
    {
        return "-----PaymentFallbackService fall back-paymentInfo_TimeOut ,o(╥﹏╥)o";
    }
}

```