package learn.spring.bean.domain;

import org.springframework.beans.factory.FactoryBean;

/**
 * UserFactoryBean.
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        final User user = new User();
        user.setName("FactoryBeanUser-Simon");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
