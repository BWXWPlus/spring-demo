package com.example.demo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wuwenshuo
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(MyBeanPostProcessor.class);
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<销毁前执行，如@PreDestory>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<实例化之前执行，在这里返回的对象可以替换掉原来的bean>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return  null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<实例化之后执行，如果这里返回false会跳过依赖注入阶段>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<依赖注入阶段执行，例如：@Autowired、@Value、@Resource>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<初始化之前执行，返回对象可替换原本的bean,如@PostConstruct 、@ConfigurationProperties>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("lifeCycleBeanTest".equals(beanName)){
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<初始化之后执行，返回的对象会替换掉原本的bean,如代理增强>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return bean;
    }
}
