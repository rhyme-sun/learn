package learn.java.basics.io.base;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 合并读取结果：
 * MergeReadDataExample.
 */
@Slf4j
public class MergeReadDataExample {

    public static void main(String[] args) {
        example();
//        example2();
//        example3();
    }

    private static void example() {
        try (InputStream inputStream = new FileInputStream("README.md")) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n;
            // n 为每次读取的字符数量，-1 表示没有数据了
            while ((n = inputStream.read(buffer)) != -1) {
                log.info("read bytes: {}", n);
                outputStream.write(buffer);
            }
            log.info(new String(outputStream.toByteArray()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try (Reader reader = new FileReader("README.md", StandardCharsets.UTF_8)) {
            CharArrayWriter writer = new CharArrayWriter();
            char[] buffer = new char[1024];
            int n;
            // n 为每次读取的字符数量，-1 表示没有数据了
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
                writer.write(buffer);
            }
            log.info(new String(writer.toCharArray()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        try (Reader reader = new FileReader("README.md", StandardCharsets.UTF_8)) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[1024];
            int n;
            // n 为每次读取的字符数量，-1 表示没有数据了
            while ((n = reader.read(buffer)) != -1) {
                log.info("read chars: {}", n);
                builder.append(new String(buffer));
            }
            log.info(builder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
