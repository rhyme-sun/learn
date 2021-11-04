package learn.java.basics.io.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * SerializationExample.
 */
@Slf4j
public class SerializationExample {

    public static void main(String[] args) {
//        example1();
        example2();
    }

    private static void example1() {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            // 写入 int:
            output.writeInt(12345);
            // 写入 String:
            output.writeUTF("Hello");
            // 写入 Object:
            output.writeObject(Double.valueOf(123.456));
            log.info(Arrays.toString(buffer.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try (InputStream inputStream = serialize();
             ObjectInputStream input = new ObjectInputStream(inputStream)) {
            int n = input.readInt();
            String s = input.readUTF();
            Double d = (Double) input.readObject();
            // 12345
            log.info("{}", n);
            // hello
            log.info("{}", s);
            // 123.456
            log.info("{}", d);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static InputStream serialize() {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            // 写入 int:
            output.writeInt(12345);
            // 写入 String:
            output.writeUTF("Hello");
            // 写入 Object:
            output.writeObject(Double.valueOf(123.456));
            return new ByteArrayInputStream(buffer.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}