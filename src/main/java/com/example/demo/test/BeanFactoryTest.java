package com.example.demo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


public class BeanFactoryTest {
    public static void main(String[] args) {
        //创建一个factory实例对象，当前的factory中并没有任何的Bean
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        //Bean的定义(class、scope、初始化、销毁等)，定义一些Bean放入到factory中
        //BeanDefinitionBuilder是一个工具类，用于构架一个BeanDefinition对象
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).getBeanDefinition();
        //将定义的Bean加入到factory中，需要为其命名
        factory.registerBeanDefinition("config", beanDefinition);
        //注册注解处理器，注册Spring上下问的注解，向beanFactory中注册所需的注解处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(factory);

        /**
         * BeanFactoryPostProcessor 是一种特殊的 Bean，
         * 用于在 Spring 容器实例化所有其他 Bean 之前，对 BeanFactory 进行修改。
         * 这些修改可能包括添加、修改或删除 Bean 的属性，修改 Bean 的定义等
         */
        //获取BeanFactoryPostProcessor相关的已经注入的Bean，@Configuration、@Bean
        factory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(item -> item.postProcessBeanFactory(factory)
        );
         System.out.println("================================================");
        //bean后置处理器，针对的是Bean的生命周期的各个阶段提供扩展。如@Autowired、@Resource等
        factory.getBeansOfType(BeanPostProcessor.class).values()
                .stream().sorted(factory.getDependencyComparator()).
                forEach(item->{
            System.out.println(item);
            factory.addBeanPostProcessor(item);
        });

        System.out.println("================================================");
        System.out.println(factory.getBean(Bean1.class).getInter());

        // Arrays.stream(factory.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.debug("bean1");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Resource(name = "bean3")
        @Autowired
        private Inter bean4;
        public Inter getInter() {
            return bean4;
        }


    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean2() {
            log.debug("bean2");
        }
    }

    interface Inter {
    }

    static class Bean3 implements Inter {

    }

    static class Bean4 implements Inter {

    }

}
