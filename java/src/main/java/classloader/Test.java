package classloader;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/30
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        App001ClassLoader classLoader001 = new App001ClassLoader();
        App002ClassLoader classLoader002 = new App002ClassLoader();

        String className = "classloader.TestClass";

        Object object001 = classLoader001.loadClass(className).newInstance();
        Object object002 = classLoader002.loadClass(className).newInstance();

        System.out.println(object001 instanceof TestClass);
        System.out.println(object002 instanceof TestClass);
        System.out.println(object001.getClass().equals(object002.getClass()));
        System.out.println(object001.getClass());
        System.out.println(object002.getClass());
        System.out.println(TestClass.class);
    }
}
