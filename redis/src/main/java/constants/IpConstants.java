package constants;

/**
 * Created on 2021/4/13.
 *
 * @author xuliangliang
 */
public class IpConstants {

    private static final boolean useMac = false;

    private static final String MAC = "172.16.13.2";

    private static final String WINDOWS = "192.168.111.20";

    public static String ip() {
        return useMac ? MAC : WINDOWS;
    }

    public static String ipPort(int port) {
        return ip() + ":" + port;
    }
}
