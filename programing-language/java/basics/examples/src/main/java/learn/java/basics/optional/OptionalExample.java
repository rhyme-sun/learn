package learn.java.basics.optional;

import java.util.Optional;

/**
 * OptionalExample.
 */
public class OptionalExample {
    public static void main(String[] args) {

    }
}

class Car {

    private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }

    /**
     * 一个车可能没有保险
     */
    public Optional<Insurance> getOptionalInsurance() {
        return Optional.ofNullable(this.insurance);
    }
}

/**
 * 保险类
 */
class Insurance {

    private String name;

    /**
     * 保险必须有名字
     */
    public String getName() {
        return name;
    }
}

class Person {

    private Car car;

    public Car getCar() {
        return car;
    }

    /**
     * 一个人可能没有车
     */
    public Optional<Car> getOptionalCar() {
        return Optional.ofNullable(this.car);
    }

    /**
     * 获取一个人的车的保险的名字，如果 person， getCar()，getInsurance() 都有可能为 null 引用，可能抛出 NullPointerException 异常
     */
    public String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    public String getCarInsuranceNameNotNullThenContinue(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    public String getCarInsuranceNameNullThenReturn(Person person) {
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }

    public String getCarInsuranceNameOptional(Person person) {
        return Optional.ofNullable(person)
                .flatMap(Person::getOptionalCar)
                .flatMap(Car::getOptionalInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }
}