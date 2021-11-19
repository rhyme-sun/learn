package learn.java.jvm.jmm;

/**
 * 字符串字面量，代码示例来自：
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-3.html#jls-3.10.5">jls-3.10.5</a>
 */
@SuppressWarnings("ALL")
public class StringLiterals {

    public static void main(String[] args) {
        String hello = "Hello", lo = "lo";
        // true，字符串常量池中同一个字面量，指向同一个字符串对象
        System.out.println(hello == "Hello");
        // true，字符串常量池中同一个字面量，指向同一个字符串对象
        System.out.println(Other.hello == hello);
        // true，"Hel" + "lo" 编译为 "Hello"
        System.out.println(hello == ("Hel" + "lo"));
        // false，运行时用一个字符串连接一个字符串字面量，此时创建了一个新的字符串对象
        System.out.println(hello == ("Hel" + lo));
        // false
        System.out.println(("Hel" + lo) == ("Hel" + lo));
        // String#intern 方法返回与此字符串（对象）值相同并来自于字符串常量池的唯一字符串
        // true
        System.out.println(hello == ("Hel" + lo).intern());
    }
}

class Other {
    static String hello = "Hello";
}