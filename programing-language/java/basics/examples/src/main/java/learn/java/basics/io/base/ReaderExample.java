package learn.java.basics.io.base;

import java.io.CharArrayReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * ReaderExample.
 */
@Slf4j
public class ReaderExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
//        example4();
        example5();
    }

    private static void example1() {
        try (Reader reader = new FileReader("README.md", StandardCharsets.UTF_8)) {
            int n;
            // n 表示读取的字符，其值的范围为 0~65535，-1 表示没有数据了
            while ((n = reader.read()) != -1) {
                log.info("next char: {}", n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try (Reader reader = new FileReader("README.md", StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int n;
            // n 为每次读取的字符数量，-1 表示没有数据了
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        char[] charArray = new char[]{'H', 'e', 'l', 'l', 'o', '!'};
        char[] buffer = new char[charArray.length];
        try (Reader reader = new CharArrayReader(charArray)) {
            int n;
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
                log.info("{}", new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example4() {
        String hello = "Hello!";
        char[] buffer = new char[hello.length()];
        try (Reader reader = new StringReader(hello)) {
            int n;
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
                log.info("{}", new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example5() {
        // 使用 try (resource) 结构时，当我们关闭 Reader 时
        // 它会在内部自动调用 InputStream 的 close() 方法，所以，只需要关闭最外层的 Reader 对象即可
        try (Reader reader = new InputStreamReader(new FileInputStream("README.md"), StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int n;
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}