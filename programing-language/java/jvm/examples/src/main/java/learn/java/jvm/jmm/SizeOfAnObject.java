package learn.java.jvm.jmm;

import learn.jvm.agent.ObjectSizeAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取一个对象的大小
 */
@Slf4j
public class SizeOfAnObject {

    public static void main(String[] args) {
        // 对象头（8） + ClassPointer（4） + 补齐（4）= 16
        log.info("{}", ObjectSizeAgent.sizeOf(new Object()));
        // 对象头（8） + ClassPointer（4） + 数组长度（4）+ 补齐（0） = 16
        log.info("{}", ObjectSizeAgent.sizeOf(new int[]{}));
        // 对象头（8） + ClassPointer（4） + 实例内容（19）+ 补齐（1） = 32
        log.info("{}", ObjectSizeAgent.sizeOf(new SizeObject()));
    }

    // -XX:+UseCompressedClassPointers：开启 ClassPointer 压缩，开启 4 个字节，不开启 8 个字节，默认开启
    // -XX:+UseCompressedOops：开启引用类型指针压缩，开启，开启 4 个字节，不开启 8 个字节，默认开启
    // Oops = ordinary object pointers
    static class SizeObject {
        // 8 Mark Word
        // 4 class pointer
        int id;         // 4
        int age;        // 4

        String name;    // 4 ordinary object pointers
        Object o;       // 4 ordinary object pointers

        byte b1;        // 1
        byte b2;        // 1
        byte b3;        // 1
        // 对齐，8 的倍数
    }
}
