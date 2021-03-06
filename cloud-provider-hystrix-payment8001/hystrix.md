# 服务提供者端降级和熔断

## 降级

### 引入依赖

```xml
        <!--hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```

### 启动类加@EnableCircuitBreaker

```java

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class PaymentHystrixMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8001.class, args);
    }
}
```

### 在服务类中需要熔断降级的方法中使用 @HystrixCommand编写熔断降级代码

```java
@Service
@Slf4j
public class PaymentService {
    /**
     * 正常访问，肯定OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id)
    {
        return "线程池:  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * 模拟超时和异常中断
     * @param id
     * @return
     */
    @HystrixCommand(/*指定回调方法，fallbackMethod方法的前面要和此方法的签名一致*/fallbackMethod = "paymentInfo_TimeOut_Handler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        //int age = 10/0;
        int timeout = 1;
        for(int i=5;i>0;i--){
            log.info("第{}此循环，等待{}秒",i,timeout);
            try { TimeUnit.SECONDS.sleep(timeout); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        return "线程池:  "+Thread.currentThread().getName()+" id:  "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒): ";
    }

    public String paymentInfo_TimeOut_Handler(Integer id){
        return "线程池:  "+Thread.currentThread().getName()+"  8001系统繁忙或者运行报错，请稍后再试,id:  "+id+"\t"+"o(╥﹏╥)o";
    }
}

```