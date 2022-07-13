package learn.java.jvm.classloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadContextClassLoaderExample.
 */
@Slf4j
public class ThreadContextClassLoaderExample {

    public static void main(String[] args) {
        example1();
//        example2();
    }

    private static void example1() {
        final Thread thread = Thread.currentThread();
        // thread.setContextClassLoader(new CustomClassLoader());
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        // default is AppClassLoader
        log.info("Thread context class loader: {}", contextClassLoader);
    }

    private static void example2() {
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "";
        try (Connection conn = java.sql.DriverManager.getConnection(url, user, password)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select * from foo where id = 1")) {
                    while (rs.next()) {
                        // 注意：索引从1开始
                        long id = rs.getLong(1);
                        String bar = rs.getString(2);
                        log.info("id: {}, bar: {}", id, bar);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}