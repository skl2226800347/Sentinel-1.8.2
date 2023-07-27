package com.alibaba.csp.sentinel.demo.cluster.zookeeper.dubbo.config;


import com.alibaba.csp.sentinel.demo.cluster.zookeeper.dubbo.bean.SpringBeanUtil;
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
