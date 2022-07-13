package learn.spring.dependency.domain;

import java.util.Date;

/**
 * User.
 */
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Date createTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }

    public static User createUser() {
        final User user = new User();
        user.setName("Simon");
        user.setCreateTime(new Date());
        return user;
    }
}
