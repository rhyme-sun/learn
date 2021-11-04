package learn.java.basics.io.base;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * OutputStreamExample.
 */
@Slf4j
public class OutputStreamExample {

    public static void main(String[] args) {
//        example1();
        example2();
    }

    private static void example1() {
        try (OutputStream output = new FileOutputStream("README.md")) {
            output.write("Hello Simon!".getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        byte[] data;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write("Hello ".getBytes(StandardCharsets.UTF_8));
            output.write("Simon!".getBytes(StandardCharsets.UTF_8));
            data = output.toByteArray();
            log.info(new String(data, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}