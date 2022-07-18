package learn.spring.boot.basics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(SpringBootDemoApplication.class, args);
        System.out.println(ac.getBean(MyBean.class).getName());
    }

    @RestController
    static class Controller {

        @GetMapping("/")
        public String hello() {
            return "Hello World!";
        }
    }

    @Component
    static class MyBean {

        @Value("${name}")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
