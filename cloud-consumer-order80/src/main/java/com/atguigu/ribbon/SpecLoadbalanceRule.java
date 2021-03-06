package com.atguigu.ribbon;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecLoadbalanceRule {

    @Bean
    public IRule specRule(){
        return new RandomRule();
    }
}
