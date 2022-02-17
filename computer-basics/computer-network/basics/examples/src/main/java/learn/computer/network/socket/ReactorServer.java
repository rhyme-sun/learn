package learn.computer.network.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;

/**
 * ReactorServer.
 */
@Slf4j
public class ReactorServer {

    public static void main(String[] args) throws IOException {
        server();
    }

    public static void server() throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8084));

        final Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 16);

        // selector.select()：阻塞到至少有一个管道处于就绪状态
        while (selector.select() > 0) {
            final Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                final SelectionKey selectedKey = selectedKeys.next();
                selectedKeys.remove();
                if (selectedKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectedKey.isReadable()) {
                    read(byteBuffer, selectedKey);
                } else if (selectedKey.isWritable()) {
                    SocketChannel socketChannel = write(byteBuffer, selectedKey);
                    socketChannel.close();
                }
            }
        }
    }

    private static void read(ByteBuffer byteBuffer, SelectionKey selectedKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectedKey.channel();

        byteBuffer.clear();
        StringBuilder request = new StringBuilder();
        int length;
        while ((length = socketChannel.read(byteBuffer)) > 0) {
            request.append(new String(byteBuffer.array(), 0, length));
            byteBuffer.clear();
        }
        log.info("Data from client: {}", request);
        selectedKey.interestOps(SelectionKey.OP_WRITE);
    }

    private static SocketChannel write(ByteBuffer byteBuffer, SelectionKey selectedKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
        byteBuffer.clear();
        byteBuffer.put(("HTTP/1.1 200 OK" + System.lineSeparator()).getBytes());
        byteBuffer.put(("Content-Type:text/html;charset=utf-8" + System.lineSeparator()).getBytes());
        String body = "hello";
        byteBuffer.put(("Content-Length:" + body.getBytes().length + System.lineSeparator()).getBytes());
        byteBuffer.put((System.lineSeparator()).getBytes());

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }
        return socketChannel;
    }
}
