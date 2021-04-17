package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Created on 2021/4/13.
 *
 * @author xuliangliang
 */
public class StringOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        // 字符串
        jedis.set("string", "中国");
        String strVal = jedis.get("string");
        System.out.println(strVal);

        // 数值
        String activeUserCountKey = "activeUserCount";
        jedis.set(activeUserCountKey, "0");
        jedis.incr(activeUserCountKey);
        String activeUserCount = jedis.get(activeUserCountKey);
        System.out.println(activeUserCount);

        // 位图(用一个 bit 来记录 1/0(true/false) 两种状态,节省内存,提高效率)
        String bitmapKey = "bitmap";
        jedis.setbit(bitmapKey, 0L, true);
        jedis.setbit(bitmapKey, 1L, true);
        jedis.setbit(bitmapKey, 2L, true);

        System.out.printf("%s[%s]:%s\n", bitmapKey, 0L, jedis.getbit(bitmapKey, 0L));
        System.out.printf("%s[%s]:%s\n", bitmapKey, 3L, jedis.getbit(bitmapKey, 3L));

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
