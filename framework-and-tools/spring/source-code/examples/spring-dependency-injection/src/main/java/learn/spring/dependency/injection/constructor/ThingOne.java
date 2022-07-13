package learn.spring.dependency.injection.constructor;

import org.springframework.stereotype.Component;

/**
 * ThingOne.
 */
@Component
public class ThingOne {

    private ThingTwo thingTwo;
    private ThingThree thingThree;

    public ThingOne() {
    }

    public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
        this.thingTwo = thingTwo;
        this.thingThree = thingThree;
    }

    public ThingTwo getThingTwo() {
        return thingTwo;
    }

    public void setThingTwo(ThingTwo thingTwo) {
        this.thingTwo = thingTwo;
    }

    public ThingThree getThingThree() {
        return thingThree;
    }

    public void setThingThree(ThingThree thingThree) {
        this.thingThree = thingThree;
    }

    // this is (unsurprisingly) the initialization callback method
    public void init() {
        System.out.println("Default init....");
    }
}
