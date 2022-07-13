package learn.spring.bean.definition;

import java.util.concurrent.TimeUnit;

import learn.spring.bean.domain.FinalizeMethodUserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanGarbageCollectorExample.
 */
@Configuration
public class BeanGarbageCollectorExample {

    public static void main(String[] args) throws InterruptedException {
        // 创建 Spring IoC 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class
        applicationContext.register(BeanGarbageCollectorExample.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 关闭 Spring 应用上下文
        applicationContext.close();
        // 执行 GC
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

    @Bean
    public FinalizeMethodUserFactory finalizeMethodUserFactory() {
        return new FinalizeMethodUserFactory();
    }
}
