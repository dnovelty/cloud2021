package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentRegNacosMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentRegNacosMain9001.class);
    }
}
