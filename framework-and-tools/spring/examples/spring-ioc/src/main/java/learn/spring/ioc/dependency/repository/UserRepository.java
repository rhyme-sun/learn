package learn.spring.ioc.dependency.repository;

import java.util.Collection;

import learn.spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;

/**
 * UserRepository.
 */
public class UserRepository {

    private Collection<User> users;

    private BeanFactory beanFactory;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
