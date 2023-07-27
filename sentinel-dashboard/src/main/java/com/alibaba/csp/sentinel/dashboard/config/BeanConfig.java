package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.biz.bean.SpringBeanUtil;
import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
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


    @Bean
    public SpringBeanUtil initSpringBeanUtil(){
        SpringBeanUtil springBeanUtil = new SpringBeanUtil();
        return springBeanUtil;
    }




}
