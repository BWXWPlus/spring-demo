package com.example.demo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * @author wuwenshuo
 */
@Component
public class LifeCycleBeanTest {
    private static final Logger log = LoggerFactory.getLogger(LifeCycleBeanTest.class);

    public LifeCycleBeanTest() {
        log.debug("构造方法");
    }
    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String javacHome){
        log.debug("依赖注入：{}",javacHome);
    }
    @PostConstruct
    public void init(){
        log.debug("初始化");
    }
    @PreDestroy
    public void destroy(){
        log.debug("销毁");
    }
}
