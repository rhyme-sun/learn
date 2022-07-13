package learn.java.basics.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CompositeAnnotationExample.
 */
public class CompositeAnnotationExample {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyAnnotation1 {

    }

    @Target(ElementType.ANNOTATION_TYPE)
    @MyAnnotation1
    public @interface MyAnnotation2 {

    }

    public static void main(String[] args) {
        // 获取注解上的注解
        final Annotation[] annotations = CompositeAnnotationExample.MyAnnotation1.class.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}