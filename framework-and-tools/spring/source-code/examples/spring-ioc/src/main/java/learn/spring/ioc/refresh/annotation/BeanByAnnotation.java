package learn.spring.ioc.refresh.annotation;

/**
 * BeanByAnnotation.
 */
public class BeanByAnnotation {

    private BeanByAnnotation inner;

    public BeanByAnnotation getInner() {
        return inner;
    }

    public void setInner(BeanByAnnotation inner) {
        this.inner = inner;
    }
}
