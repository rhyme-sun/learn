package learn.computer.network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpServer1.
 */
@Slf4j
public class HttpServer1 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081, 10);

        while (true) {
            // 线程会被阻塞在 accept 方法
            Socket client = server.accept();
            log.info("Client's port: {}", client.getPort());
            while (true) {
                service(client);
            }
        }
    }

    private static void service(final Socket socket) {
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello";
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
