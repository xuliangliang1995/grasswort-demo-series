package classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/30
 */
public class App001ClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String fileName = name.substring(name.lastIndexOf(".") + 1).concat(".class");
        InputStream is = getClass().getResourceAsStream(fileName);
        try {
            if (null == is) {
                return super.loadClass(name);
            }

            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

