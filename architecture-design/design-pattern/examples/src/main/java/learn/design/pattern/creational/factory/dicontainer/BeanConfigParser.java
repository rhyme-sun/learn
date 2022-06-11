package learn.design.pattern.creational.factory.dicontainer;

import java.io.InputStream;
import java.util.List;

@SuppressWarnings("rawtypes")
public interface BeanConfigParser  {
    List parse(InputStream inputStream);
    List parse(String configContent);
}
