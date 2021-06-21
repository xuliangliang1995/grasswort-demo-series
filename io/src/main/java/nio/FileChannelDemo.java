package nio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/21
 */
public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
        Properties properties = System.getProperties();

        String tmpDir = properties.getProperty("java.io.tmpdir");

        File file = new File(tmpDir + "a.txt");
        File anotherFile = new File(tmpDir + "b.txt");

        file.delete();
        anotherFile.delete();

        FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.READ, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        buffer.put("grasswort".getBytes());
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        channel.write(buffer);

        buffer.clear();
        buffer.put(".com".getBytes());
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        channel.write(buffer);

        channel.force(false);

        FileChannel anotherChannel = FileChannel.open(anotherFile.toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

        // zero-copy 对应 sendFile()
        channel.transferTo(0, channel.size(), anotherChannel);

        channel.close();
        anotherChannel.close();

        System.out.println(file.getPath());
        System.out.println(anotherFile.getPath());
    }
}
