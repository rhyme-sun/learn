package learn.spring.bean.domain;

/**
 * UserFactory.
 */
public interface UserFactory {

    default User createUser() {
        final User user = new User();
        user.setName("FactoryUser-Simon");
        return user;
    }
}
