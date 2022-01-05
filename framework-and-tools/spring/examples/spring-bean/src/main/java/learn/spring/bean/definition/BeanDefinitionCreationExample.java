package learn.spring.bean.definition;

import learn.spring.bean.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * BeanDefinitionCreationExample.
 */
public class BeanDefinitionCreationExample {

    public static void main(String[] args) {
        // 1. 通过 BeanDefinitionBuilder 创建 BeanDefinition
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 1)
                .addPropertyValue("name", "Simon")
                .addPropertyValue("age", 11);
        final AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        // 2. 通过 AbstractBeanDefinition 的派生类创建 BeanDefinition
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);

        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues
                .add("id", 1)
                .add("name", "Simon")
                .add("age", 11);
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
    }
}
