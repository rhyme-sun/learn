package learn.spring.ioc.dependency.container;

import learn.spring.ioc.dependency.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AnnotationApplicationContextAsContainerExample.
 */
@Configuration
public class AnnotationApplicationContextAsContainerExample {

    public static void main(String[] args) {
        // 创建配置上下文容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册配置类
        applicationContext.register(AnnotationApplicationContextAsContainerExample.class);
        // 启动应用上下文
        applicationContext.refresh();
        final User annotationUser = applicationContext.getBean("annotationUser", User.class);
        System.out.println(annotationUser);
    }

    @Bean
    public User annotationUser() {
        User user = new User();
        user.setId(1);
        user.setName("Simon-Annotation");
        user.setAge(18);
        return user;
    }
}
