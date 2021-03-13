# nacos配置中心


## 搭建

### 引入依赖

```xml
    <!--nacos-config-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
```

### 编写配置文件
application.yml


```yaml

#引用的配置文件在服务端的全限定名称为${spring.application.name}-${profile}.${file-extension:properties}
spring:
  profiles:
    active: dev # 表示开发环境
    #active: test # 表示测试环境
    #active: info


```

bootstrap.yml

```yaml

# nacos配置
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #Nacos服务注册中心地址
#        增加配置中心
      config:
        server-addr: localhost:8848 #Nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
#        group: DEV_GROUP
#        namespace: 7d8f0f5a-6a53-4785-9686-dd460158e5d4


# ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
# nacos-config-client-dev.yaml

# nacos-config-client-test.yaml   ----> config.inf
```