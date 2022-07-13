package learn.spring.ioc.refresh.xml;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RefreshExample.
 * AbstractApplicationContext#refresh debug
 */
public class RefreshExample {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:refresh.xml");
        //MyClassPathXmlApplicationContext ac = new MyClassPathXmlApplicationContext("classpath:refresh.xml");

        System.out.println(ac.getBean("foo"));
    }

    static class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

        public MyClassPathXmlApplicationContext(String... configLocations) {
            super(configLocations);
        }

        @Override
        protected void initPropertySources() {
            // 扩展 initPropertySource 行为，配置环境信息
            System.out.println("扩展 initPropertySource");
            getEnvironment().setRequiredProperties("username");
        }

        @Override
        protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
            // 设置容器是否允许覆盖 Bean 定义（allowBeanDefinitionOverriding）（默认 true）
            // 是否允许循环依赖（allowCircularReferences）（默认 true）
            super.setAllowBeanDefinitionOverriding(false);
            super.setAllowCircularReferences(false);

            super.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
            super.customizeBeanFactory(beanFactory);
        }

        @Override
        public void setValidating(boolean validating) {
            // 设置是否对 XML 开启格式验证（validating）（默认 true）
            super.setValidating(false);
        }

        @Override
        protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            System.out.println("扩展实现 postProcessBeanFactory 方法");
        }
    }
}
