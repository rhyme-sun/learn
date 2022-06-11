package learn.design.pattern.creational.factory.dicontainer;

/**
 * 测试入口类
 *
 * @author ykthree
 * @date 2020/6/4 19:21
 */
public class Client {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
        rateLimiter.test();
    }
}
