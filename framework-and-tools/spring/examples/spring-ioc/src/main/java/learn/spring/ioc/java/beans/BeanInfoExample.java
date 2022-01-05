package learn.spring.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.util.Objects;

/**
 * BeanInfoExample.
 */
public class BeanInfoExample {

    public static void main(String[] args) throws IntrospectionException {
        final BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            System.out.println(propertyDescriptor);
            final String name = propertyDescriptor.getName();
            if (Objects.equals("age", name)) {
                propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
            }
        }
    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {

        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.parseInt(text);
            setValue(value);
        }
    }
}
