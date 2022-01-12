package learn.spring.dependency.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.Resource;

/**
 * Foo.
 */
public class Foo {

    private FooEnum fooEnum;
    private Resource resource;
    private Locale date;
    private FooEnum[] fooEnums;
    private List<FooEnum> fooEnumList;
    private Set<FooEnum> fooEnumSet;
    private Map<String, FooEnum> fooEnumMap;

    public FooEnum getFooEnum() {
        return fooEnum;
    }

    public void setFooEnum(FooEnum fooEnum) {
        this.fooEnum = fooEnum;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Locale getDate() {
        return date;
    }

    public void setDate(Locale date) {
        this.date = date;
    }

    public FooEnum[] getFooEnums() {
        return fooEnums;
    }

    public void setFooEnums(FooEnum[] fooEnums) {
        this.fooEnums = fooEnums;
    }

    public List<FooEnum> getFooEnumList() {
        return fooEnumList;
    }

    public void setFooEnumList(List<FooEnum> fooEnumList) {
        this.fooEnumList = fooEnumList;
    }

    public Set<FooEnum> getFooEnumSet() {
        return fooEnumSet;
    }

    public void setFooEnumSet(Set<FooEnum> fooEnumSet) {
        this.fooEnumSet = fooEnumSet;
    }

    public Map<String, FooEnum> getFooEnumMap() {
        return fooEnumMap;
    }

    public void setFooEnumMap(Map<String, FooEnum> fooEnumMap) {
        this.fooEnumMap = fooEnumMap;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "fooEnum=" + fooEnum +
                ", resource=" + resource +
                ", date=" + date +
                ", fooEnums=" + Arrays.toString(fooEnums) +
                ", fooEnumList=" + fooEnumList +
                ", fooEnumSet=" + fooEnumSet +
                ", fooEnumMap=" + fooEnumMap +
                '}';
    }
}
