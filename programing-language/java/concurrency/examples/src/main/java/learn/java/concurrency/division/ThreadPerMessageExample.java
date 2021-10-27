package learn.java.concurrency.division;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadPerMessageExample.
 *
 * 压测命令：sb -u http://localhost:8080/ -n 100000 -c 1000 -B
 */
@Slf4j
public class ThreadPerMessageExample {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                // Thread-Per-Message
                new Thread(() -> service(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            // 模拟处理请求
            Thread.sleep(2000);
            log.info("Deal with thread: {}", Thread.currentThread().getName());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "Hello Simon!";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}