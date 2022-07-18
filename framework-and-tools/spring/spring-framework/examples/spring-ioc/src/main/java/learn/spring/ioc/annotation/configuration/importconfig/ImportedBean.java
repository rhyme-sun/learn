package learn.spring.ioc.annotation.configuration.importconfig;

/**
 * ImportBean.
 */
public class ImportedBean {

    private String value;

    public ImportedBean() {
    }

    public ImportedBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ImportedBean{" +
                "value='" + value + '\'' +
                '}';
    }
}
