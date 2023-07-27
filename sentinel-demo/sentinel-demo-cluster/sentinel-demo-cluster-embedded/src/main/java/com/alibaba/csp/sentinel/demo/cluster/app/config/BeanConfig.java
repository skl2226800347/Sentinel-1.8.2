package com.alibaba.csp.sentinel.demo.cluster.app.config;

import com.alibaba.csp.sentinel.demo.cluster.app.bean.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public SpringBeanUtil initSpringBeanUtil(){
        SpringBeanUtil springBeanUtil = new SpringBeanUtil();
        return springBeanUtil;
    }
}
