# 调用服务组件

## 引入依赖
```xml
<!--openfeign-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```


## 启动入口应用@EnableFeignClients

```java
@SpringBootApplication
@EnableFeignClients
public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class, args);
    }
}
```

## 编写FeignClient接口

```java
@Service
@FeignClient(/**指定微服务名称**/value = "CLOUD-PAYMENT-SERVICE")
public interface FeignPaymentService {

    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(/**value值必填**/@PathVariable(value = "id") Long id);
}
```


# 编写日志级别

## 返回一个Level的bean
```java
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level getLogLevel(){
        return Logger.Level.FULL;
    }
}

```


## 编写配置文件
```yaml
logging:
  level:
    com.atguigu.springcloud.service.FeignPaymentService: debug

```
