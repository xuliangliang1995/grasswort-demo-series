package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class HashOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        String hashKey = "hash";
        jedis.del(hashKey);

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "jerry");
        fields.put("age", "8");
        fields.put("id", "1");
        fields.put("email", "grasswort@qq.com");

        Long rs = jedis.hset(hashKey, fields);
        System.out.println(rs);

        jedis.hgetAll(hashKey).forEach((key, value) -> System.out.printf("%s : %s\n", key, value));

        jedis.hincrBy(hashKey, "age", 1);
        String age = jedis.hget(hashKey, "age");
        System.out.println(age);

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
