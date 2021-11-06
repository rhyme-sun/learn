package learn.java.jvm.bytecode.enhancer.asm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成字节码
 */
//@Slf4j
public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    public static void main(String[] args) throws IOException {
        String className = "learn/java/jvm/bytecode/enhancer/Base";
        String pathName = "target/classes/learn/java/jvm/bytecode/enhancer/Base.class";

        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        ClassVisitor classVisitor = new BaseClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();

        // 写入增强后的文件
        Files.write(Paths.get(pathName), data);
        log.info("generated");
    }
}