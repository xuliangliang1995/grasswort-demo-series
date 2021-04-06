package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created on 2021/4/6.
 *
 * @author xuliangliang
 */
public class InputStreamDemo {

    public static void main(String[] args) {
        ByteArrayInputStream is = new ByteArrayInputStream("徐靓靓".getBytes(StandardCharsets.UTF_8));

        int size = is.available();
        byte[] bytes = new byte[size];
        try {
            int readBytes = is.read(bytes);
            assert readBytes == size;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String readContent = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(readContent);
    }
}
