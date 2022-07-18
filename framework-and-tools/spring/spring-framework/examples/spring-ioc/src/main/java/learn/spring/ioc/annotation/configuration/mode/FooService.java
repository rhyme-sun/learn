package learn.spring.ioc.annotation.configuration.mode;

/**
 * FooService.
 */
public class FooService {

    private FooRepository fooRepository;

    public FooService(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    public FooRepository getFooRepository() {
        return fooRepository;
    }

    public void setFooRepository(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }
}
