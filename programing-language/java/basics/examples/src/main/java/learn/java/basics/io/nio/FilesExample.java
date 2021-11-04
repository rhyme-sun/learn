package learn.java.basics.io.nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * FilesExample.
 */
@Slf4j
public class FilesExample {

    public static void main(String[] args) {
//        example1();
//        example2();
        example3();
    }

    private static void example1() {
        try {
            byte[] data = Files.readAllBytes(Paths.get("README.md"));
            log.info("{}", new String(data, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example2() {
        try {
            String file = "README.md";
            String content1 = Files.readString(Paths.get(file));
            log.info(content1);
            String content2 = Files.readString(Paths.get(file), StandardCharsets.ISO_8859_1);
            log.info(content2);
            List<String> lines = Files.readAllLines(Paths.get(file));
            log.info("{}", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        try {
            byte[] data = Files.readAllBytes(Paths.get("README.md"));
            Files.write(Paths.get("README_COPY1.md"), data);

            List<String> lines = Files.readAllLines(Paths.get("README.md"));
            Files.write(Paths.get("README_COPY2.md"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}