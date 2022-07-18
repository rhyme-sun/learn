package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportByImportBeanDefinitionRegistrar.
 */
public class ImportByImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder
                .genericBeanDefinition(ImportedBean.class)
                .addPropertyValue("value", "ImportByImportBeanDefinitionRegistrar")
                .getBeanDefinition();
        registry.registerBeanDefinition("importByImportBeanDefinitionRegistrar", beanDefinition);
    }
}
