package nio;

import constants.Ports;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/21
 */
public class SelectorServerDemo {

    public static void main(String[] args) throws IOException {
        SocketAddress serverAddr = new InetSocketAddress("localhost", Ports.SERVER_PORT);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(serverAddr, 3);


        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            int selectResult = selector.select(10000);
            System.out.println("select result : " + selectResult);
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                try {
                    boolean acceptable = selectionKey.isAcceptable();
                    if (acceptable) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = server.accept();
                        System.out.println(client.getRemoteAddress() + "已建立连接");
                        client.configureBlocking(false);

                        client.register(selector, SelectionKey.OP_READ);
                    }

                    boolean readable = selectionKey.isReadable();
                    if (readable) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                        int readResult = client.read(buffer);
                        boolean bye = readResult == -1;

                        if (! bye) {
                            buffer.flip();
                            byte[] readBytes = new byte[buffer.limit()];
                            buffer.get(readBytes);
                            String content = new String(readBytes);
                            System.out.println("recv : " + content);
                            bye = "bye".equals(content);
                            buffer.clear();
                            buffer.put(content.getBytes());
                            buffer.flip();

                            client.write(buffer);
                        }

                        if (bye) {
                            selectionKey.cancel();
                            System.out.println(client.getRemoteAddress() + "已断开连接");
                            //client.finishConnect();
                            //client.close();
                        }
                    }
                } finally {
                    iterator.remove();
                }
            }

        }
    }
}
