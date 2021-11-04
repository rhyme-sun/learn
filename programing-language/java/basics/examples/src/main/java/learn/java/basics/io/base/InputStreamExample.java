package learn.java.basics.io.base;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * InputStreamExample.
 */
@Slf4j
public class InputStreamExample {

    public static void main(String[] args) {
//        example1();
        example2();
//        example3();
    }

    private static void example1() {
        try (InputStream input = new FileInputStream("README.md")) {
            int n;
            // n 表示读取的字节，值的范围为 0~255，-1 表示没有数据了
            while ((n = input.read()) != -1) {
                log.info("next byte: {}", n);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try (InputStream input = new FileInputStream("README.md")) {
            byte[] buffer = new byte[input.available()];
            int n;
            // n 表示读取的字节数，-1 表示没有数据了
            while ((n = input.read(buffer)) != -1) {
                log.info("read bytes: {}", n);
                log.info("{}", new String(buffer));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        byte[] data = {72, 101, 108, 108, 111, 33};
        try (InputStream input = new ByteArrayInputStream(data)) {
            int n;
            while ((n = input.read()) != -1) {
                log.info("{}", (char) n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}