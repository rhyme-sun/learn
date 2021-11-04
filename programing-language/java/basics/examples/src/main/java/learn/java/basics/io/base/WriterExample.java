package learn.java.basics.io.base;

import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * WriterExample.
 */
@Slf4j
public class WriterExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
        example4();
    }

    private static void example1() {
        try (Writer writer = new FileWriter("README.md", StandardCharsets.UTF_8)) {
            writer.write('H');
            writer.write("Hello");
            writer.write("Simon".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try (CharArrayWriter writer = new CharArrayWriter()) {
            writer.write("Hello".toCharArray());
            final char[] chars = writer.toCharArray();
            log.info("{}", new String(chars));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        try (StringWriter writer = new StringWriter()) {
            writer.write("Hello");
            final String s = writer.toString();
            log.info("{}", s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example4() {
        // 使用 try-resources 是，关闭 Writer 是会自动调用 FileOutputStream 的 close 方法
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("README.md"), StandardCharsets.UTF_8)) {
            writer.write("Hello Simon, Hello Alice!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}