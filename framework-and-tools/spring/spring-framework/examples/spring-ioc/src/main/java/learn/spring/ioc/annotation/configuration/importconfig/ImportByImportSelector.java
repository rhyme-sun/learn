package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportByImportSelector.
 */
public class ImportByImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"learn.spring.ioc.annotation.configuration.importconfig.ImportedBean"};
    }
}
