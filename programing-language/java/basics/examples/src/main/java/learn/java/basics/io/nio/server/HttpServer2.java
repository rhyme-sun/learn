package learn.java.basics.io.nio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpServer2（线程池优化）.
 */
@Slf4j
public class HttpServer2 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8083, 10);
        final ExecutorService service = Executors.newFixedThreadPool(10);
        while (true) {
            // 线程会被阻塞在 accept 方法
            Socket client = server.accept();
            log.info("Client's port: {}", client.getPort());
            service.execute(() -> service(client));
        }
    }

    private static void service(final Socket socket) {
        try {
            Thread.sleep(10);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while (true) {
                line = reader.readLine();
                if (Objects.isNull(line) || line.isBlank()) {
                    break;
                }
                builder.append(line + "\n");
            }
            log.info("Data from client: {}", builder);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            reader.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
