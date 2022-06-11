package learn.design.pattern.creational.factory.dicontainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * bean定义对象
 */
public class BeanDefinition {

    private String id;
    private String className;
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return Objects.equals(Scope.SINGLETON, scope);
    }

    public void addConstructorArg(ConstructorArg constructorArg) {
        constructorArgs.add(constructorArg);
    }

    /**
     * 对象创建类型
     */
    public enum Scope {
        /**
         * 表示返回单例对象
         */
        SINGLETON,
        /**
         * 返回新创建的对象
         */
        PROTOTYPE
    }

    /**
     * 构造器
     */
    public static class ConstructorArg {

        private boolean isRef;
        private Class<?> type;
        private Object arg;

        public boolean isRef() {
            return isRef;
        }

        public void setRef(boolean ref) {
            isRef = ref;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }
}
