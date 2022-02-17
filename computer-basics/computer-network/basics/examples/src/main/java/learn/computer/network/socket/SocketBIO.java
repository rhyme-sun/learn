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
 * SocketBIO.
 */
@Slf4j
public class SocketBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081, 10);

        while (true) {
            // 线程会被阻塞在 accept 方法
            Socket client = server.accept();
            log.info("Client's port: {}", client.getPort());
            try (InputStream inputStream = client.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (true) {
                    // 线程阻塞在读取数据上
                    String readLine = reader.readLine();
                    if (readLine != null) {
                        log.info("Read from client: {}", readLine);
                    } else {
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
