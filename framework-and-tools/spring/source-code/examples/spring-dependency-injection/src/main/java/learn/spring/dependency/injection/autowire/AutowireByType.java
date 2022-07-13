package learn.spring.dependency.injection.autowire;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * AutowireByType.
 */
public class AutowireByType {

    private AutowiredBean autowiredBean;
    private Map<String, Object> allBeanMap;
    private Map<String, AutowiredBean> beanMap;
    private Object object;
    private List<Object> allBeanList;
    private List<AutowiredBean> beanList;
    private Properties properties;

    public AutowiredBean getAutowiredBean() {
        return this.autowiredBean;
    }

    public void setAutowiredBean(AutowiredBean autowiredBean) {
        this.autowiredBean = autowiredBean;
    }

    public Map<String, Object> getAllBeanMap() {
        return allBeanMap;
    }

    public void setAllBeanMap(Map<String, Object> allBeanMap) {
        this.allBeanMap = allBeanMap;
    }

    public Map<String, AutowiredBean> getBeanMap() {
        return beanMap;
    }

    public void setBeanMap(Map<String, AutowiredBean> beanMap) {
        this.beanMap = beanMap;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getAllBeanList() {
        return allBeanList;
    }

    public void setAllBeanList(List<Object> allBeanList) {
        this.allBeanList = allBeanList;
    }

    public List<AutowiredBean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<AutowiredBean> beanList) {
        this.beanList = beanList;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
