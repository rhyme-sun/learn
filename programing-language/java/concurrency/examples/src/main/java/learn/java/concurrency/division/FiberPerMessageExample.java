package learn.java.concurrency.division;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import lombok.extern.slf4j.Slf4j;

/**
 * FiberPerMessageExample.
 * <p>
 * 压测命令：sb -u http://localhost:8080/ -n 50000 -c 50 -B
 */
@Slf4j
public class FiberPerMessageExample {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                // Coroutine(Fiber)-Per-Message
                new Fiber(() -> service(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Suspendable
    private static void service(Socket socket) {
        try {
            // 模拟处理请求
            Fiber.park(2000, TimeUnit.MILLISECONDS);
            log.info("Deal with fiber: {}", Fiber.currentFiber().getName());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "Hello Simon!";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException | SuspendExecution e) {
            e.printStackTrace();
        }
    }
}