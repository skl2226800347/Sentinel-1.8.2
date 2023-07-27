package com.alibaba.csp.sentinel.demo.zookeeper.lock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @ConfigurationProperties("sentinel.rule.config")
    @Bean("sentinelRuleConfig")
    public SentinelRuleConfig initSentinelRuleConfig(){
        SentinelRuleConfig sentinelRuleConfig = new SentinelRuleConfig();
        return sentinelRuleConfig;
    }

}
