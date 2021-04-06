package io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created on 2021/4/6.
 *
 * @author xuliangliang
 */
public class OutputStreamDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write("徐靓靓".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String str = os.toString(StandardCharsets.UTF_8.toString());
        System.out.println(str);
    }
}
