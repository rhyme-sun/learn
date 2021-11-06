package learn.java.jvm.bytecode.enhancer.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import learn.java.jvm.bytecode.enhancer.Base;

/**
 * Base 增强代理类，基于 Javassist
 */
public class JavassistEnhancer {

    public static Base createEnhancedBase() throws Exception {
        String className = "learn.java.jvm.bytecode.enhancer.Base";
        String methodName = "process";
        String directoryName = "target/classes/";

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(className);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

        ctMethod.addLocalVariable("start", CtClass.longType);
        ctMethod.insertBefore("start = System.currentTimeMillis();");
        ctMethod.addLocalVariable("end", CtClass.longType);
        ctMethod.insertAfter("end = System.currentTimeMillis();");
        ctMethod.insertAfter("log.info(\"Duration: {}\", \"\" + (end - start));");
        Class<?> c = ctClass.toClass();
        ctClass.writeFile(directoryName);
        return (Base) c.getConstructor().newInstance();
    }
}
