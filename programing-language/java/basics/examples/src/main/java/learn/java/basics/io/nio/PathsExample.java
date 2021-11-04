package learn.java.basics.io.nio;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

/**
 * PathsExample.
 */
@Slf4j
public class PathsExample {

    public static void main(String[] args) {
        // 构造一个 Path 对象
        Path p1 = Paths.get(".", "foo", "bar");
        // .\foo\bar
        log.info("{}", p1);

        Path p2 = p1.toAbsolutePath();
        // F:\LearnProjects\learn\programing-language\java\basics\examples\.\foo\bar
        log.info("{}", p2);

        // 转换为规范路径
        Path p3 = p2.normalize();
        // F:\LearnProjects\learn\programing-language\java\basics\examples\foo\bar
        log.info("{}", p3);

        File f = p3.toFile();
        // F:\LearnProjects\learn\programing-language\java\basics\examples\foo\bar
        log.info("{}", f);
        // 获取各级目录
        final Path path = Paths.get("..").toAbsolutePath();
        for (Path p : path) {
            log.info("{}", p);
        }
    }
}