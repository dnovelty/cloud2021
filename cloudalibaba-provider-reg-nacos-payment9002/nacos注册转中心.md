# nacos注册中心


## 配置


### 引入依赖包

```xml
        <!--SpringCloud ailibaba nacos -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

### 编写配置文件

```yaml

server:
  port: 9001
  
spring:
  application:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #配置Nacos地址
        
        
management:
  endpoints:
    web:
      exposure:
        include: '*'


```


### 编写启动类

```java

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentRegNacosMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentRegNacosMain9001.class);
    }
}

```