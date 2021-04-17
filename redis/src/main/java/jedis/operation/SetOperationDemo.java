package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class SetOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        String setKey = "set";
        jedis.sadd(setKey, "tom");
        jedis.sadd(setKey, "tom");
        jedis.sadd(setKey, "jerry");

        jedis.smembers(setKey)
                .forEach(System.out::println);

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
