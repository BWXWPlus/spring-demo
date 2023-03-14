package com.example.demo;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication

public class DemoApplication {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        //因为singletonObjects是私有的，所以通过反射，获取DefaultSingletonBeanRegistry类中的名为singletonObjects的属性。
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        //设置私有属性在类外面允许被访问
        singletonObjects.setAccessible(true);
        //获取当前Springboot实例的Bean Factory
        ConfigurableListableBeanFactory beanFactory = run.getBeanFactory();
        //根据beanFactory对象获取其对应的单例Bean的Map 键表示Bean的名称，值表示Bean的实例
        Map<String,Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        //输出key value
        //map.forEach((k,v)->System.out.println(k + "=" + v));
       // EnvironmentCapable
       // System.out.println(run.getEnvironment().getProperty("server.port"));
    }

}
