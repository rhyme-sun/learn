package learn.spring.ioc.selftag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * SimonNamespaceHandler.
 */
public class SimonNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("simon", new SimonBeanDefinitionParser());
    }
}
