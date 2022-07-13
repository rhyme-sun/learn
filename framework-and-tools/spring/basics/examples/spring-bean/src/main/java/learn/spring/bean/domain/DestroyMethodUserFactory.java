package learn.spring.bean.domain;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;

/**
 * 带有销毁方法的 UserFactory。
 */
public class DestroyMethodUserFactory implements UserFactory, DisposableBean {

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy 销毁 UserFactory ...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy 销毁 UserFactory ...");
    }

    public void annotationDestroyMethod() {
        System.out.println("Java 注解指定自定义销毁方法销毁 UserFactory ...");
    }

    public void apiDestroyMethod() {
        System.out.println("API 指定自定义销毁方法销毁 UserFactory ...");
    }
}
