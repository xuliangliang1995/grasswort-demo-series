package nio;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * Created on 2021/4/6.
 *
 * @author xuliangliang
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        int bufferSize = 5;
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.put("xll".getBytes());

        System.out.printf("position => %d\n", buffer.position());
        buffer.position(0);
        System.out.printf("position => %d\n", buffer.position());

        while (true) {
            try {
                byte b = buffer.get();
                System.out.printf("position => %d\n", buffer.position());
                System.out.printf("%s -> %s\n", b, (char) b);
            } catch (BufferUnderflowException e) {
                e.printStackTrace();
                break;
            }
        }

        buffer.compact();
        System.out.printf("position => %d\n", buffer.position());

        buffer.put("zzz".getBytes());
        System.out.printf("position => %d\n", buffer.position());

        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));
        System.out.printf("position => %d\n", buffer.position());

    }
}
