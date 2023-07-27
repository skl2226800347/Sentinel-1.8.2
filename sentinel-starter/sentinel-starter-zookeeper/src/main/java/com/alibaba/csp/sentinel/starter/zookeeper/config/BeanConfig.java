package com.alibaba.csp.sentinel.starter.zookeeper.config;

import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.starter.zookeeper.bean.SpringBeanUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public SpringBeanUtil initSpringBeanUtil(){
        SpringBeanUtil springBeanUtil = new SpringBeanUtil();
        return springBeanUtil;
    }
    @Bean
    @ConfigurationProperties("sentinel.rule.config")
    public SentinelRuleConfig initSentinelRuleConfig(){
        SentinelRuleConfig sentinelRuleConfig = new SentinelRuleConfig();
        return sentinelRuleConfig;
    }
}
