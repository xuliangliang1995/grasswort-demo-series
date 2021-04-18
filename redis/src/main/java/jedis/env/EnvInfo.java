package jedis.env;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/18
 */
public class EnvInfo {

    /**
     * 本机 MAC 地址
     */
    public final static String LOCAL_MAC = getMac();
    /**
     * 当前进程 ID
     */
    public final static int PROCESS_ID = getProcessId();

    /**
     * 获取当前进程 ID
     * @return
     */
    private static int getProcessId() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        return Integer.parseInt(runtimeMxBean.getName().split("@")[0]);
    }


    /**
     * 获取本机的Mac地址
     * @return
     */
    private static String getMac() {
        InetAddress ia;
        byte[] mac = null;
        try {
            // 获取本地IP对象
            ia = InetAddress.getByName(getIpAddress());
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    private static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(EnvInfo.LOCAL_MAC);
        System.out.println(EnvInfo.PROCESS_ID);
    }
}
