package com.alibaba.csp.sentinel.starter.zookeeper.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static final Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static final <T> T getBean(Class<T> beanClazz){
        return applicationContext.getBean(beanClazz);
    }
}
