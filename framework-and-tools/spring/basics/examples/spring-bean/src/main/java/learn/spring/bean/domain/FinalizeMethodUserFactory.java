package learn.spring.bean.domain;

/**
 * DefaultUserFactory.
 */
public class FinalizeMethodUserFactory implements UserFactory {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalize ...");
    }
}
