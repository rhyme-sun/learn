package learn.spring.aop.annotation.service;

import org.springframework.stereotype.Service;

/**
 * MyCalculator.
 */
@Service
public class MyCalculator {

    public Integer add(Integer i, Integer j) {
        System.out.println("MyCalculator add...");
        Integer result = i+j;
        return result;
    }

    public Integer sub(Integer i, Integer j) {
        Integer result = i-j;
        return result;
    }

    public Integer mul(Integer i, Integer j) {
        Integer result = i*j;
        return result;
    }

    public Integer div(Integer i, Integer j) {
        Integer result = i/j;
        return result;
    }
}
