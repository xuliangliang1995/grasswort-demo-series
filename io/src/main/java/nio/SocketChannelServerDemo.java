package nio;

import constants.Ports;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/21
 */
public class SocketChannelServerDemo {

    // 此处设为阻塞 IO， 设为非阻塞没啥意义，只会造成大量系统调用（需要配合多路复用才可以避免循环系统调用）
    private static final boolean BLOCKING = true;

    public static void main(String[] args) throws IOException {
        SocketAddress serverAddr = new InetSocketAddress("localhost", Ports.SERVER_PORT);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(serverAddr, 3);

        serverSocketChannel.configureBlocking(true);

        while (true) {
            SocketChannel clientChannel = serverSocketChannel.accept();
            new ServerThread(clientChannel).start();
        }

    }

    static class ServerThread extends Thread {
        private final SocketChannel clientChannel;

        ServerThread(SocketChannel clientChannel) {
            this.clientChannel = clientChannel;
        }


        @Override
        public void run() {

            try {
                String clientInfo = clientChannel.getRemoteAddress().toString();
                System.out.println(clientInfo + "已连接。");

                clientChannel.configureBlocking(BLOCKING);
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (true) {
                    buffer.clear();
                    clientChannel.read(buffer);

                    System.out.println(buffer.position());
                    System.out.println(buffer.limit());

                    buffer.flip();

                    System.out.println(buffer.position());
                    System.out.println(buffer.limit());

                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);

                    String content = new String(bytes);
                    System.out.println("recv : " + content);

                    if ("bye".equals(content)) {
                        break;
                    }

                    buffer.clear();
                    buffer.put((content).getBytes());

                    buffer.flip();

                    clientChannel.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
