package learn.spring.bean.domain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;

/**
 * 带有初始化方法的 UserFactory。
 */
public class InitMethodUserFactory implements UserFactory, InitializingBean {

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct 初始化 UserFactory ...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet 初始化 UserFactory ...");
    }

    public void annotationInitMethod() {
        System.out.println("Java 注解指定自定义初始化方法初始化 UserFactory ...");
    }

    public void apiInitMethod() {
        System.out.println("API 指定自定义初始化方法初始化 UserFactory ...");
    }
}
