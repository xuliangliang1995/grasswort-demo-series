package jedis.string;

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

        jedis.set("string", "中国");
        String value = jedis.get("string");

        System.out.println(value);
    }
}
