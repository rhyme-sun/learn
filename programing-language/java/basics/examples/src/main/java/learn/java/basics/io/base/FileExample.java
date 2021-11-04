package learn.java.basics.io.base;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * FileExample.
 */
@Slf4j
public class FileExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
//        example4();
        example5();
    }

    private static void example1() {
        File f = new File("C:\\Windows\\test.exe");
        // C:\Windows\test.exe
        log.info("{}", f);
    }

    private static void example2() {
        File f1 = new File("C:\\Windows");
        File f2 = new File("C:\\Program Files\\Java\\jdk-11.0.10\\bin\\java.exe");
        File f3 = new File("C:\\Windows\\nothing.exe");

        // false
        log.info("{}", f1.isFile());
        // true
        log.info("{}", f1.isDirectory());
        // true
        log.info("{}", f2.isFile());
        // false
        log.info("{}", f2.isDirectory());
        // false
        log.info("{}", f3.isFile());
        // false
        log.info("{}", f3.isDirectory());
    }

    private static void example3() {
        try {
            final File file = new File("C:\\Users\\ykthree\\Desktop\\test.txt");
            file.createNewFile();

            // true
            log.info("{}", file.canRead());
            // true
            log.info("{}", file.canWrite());
            // true
            log.info("{}", file.canExecute());
            // 0
            log.info("{}", file.length());
            if (file.delete()) {
                log.info("file has deleted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example4() {
        try {
            File f = File.createTempFile("tmp-", ".txt");
            f.deleteOnExit();
            // true
            log.info("{}", f.isFile());
            // C:\Users\ykthree\AppData\Local\Temp\tmp-6146115438366304253.txt
            log.info("{}", f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void example5() {
        File f = new File("C:\\Windows");
        // 列出所有文件和子目录
        final File[] files = f.listFiles();
        printFiles(files);
        // 仅列出.exe文件
        File[] exeFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".exe");
            }
        });
        printFiles(exeFiles);
    }

    static void printFiles(File[] files) {
        if (files != null) {
            for (File f : files) {
                log.info("{}", f);
            }
        }
    }
}