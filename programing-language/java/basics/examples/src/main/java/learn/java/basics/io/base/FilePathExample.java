package learn.java.basics.io.base;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * FilePathExample.
 */
@Slf4j
public class FilePathExample {

    public static void main(String[] args) {
        try {
            File file = new File("../");
            // ..
            log.info("{}", file.getPath());
            // F:\LearnProjects\learn\programing-language\java\basics\examples\..
            log.info("{}", file.getAbsolutePath());
            // F:\LearnProjects\learn\programing-language\java\basics
            log.info("{}", file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}