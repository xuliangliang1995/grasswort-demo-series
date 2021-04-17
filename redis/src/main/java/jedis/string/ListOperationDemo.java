package jedis.string;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.List;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class ListOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        String listKey = "listKey";
        jedis.del(listKey);

        jedis.lpush(listKey, "tmp");
        jedis.lset(listKey, 0, "jerry");
        jedis.lpush(listKey, "jerry_l");
        jedis.rpush(listKey, "jerry_r");

        Long keySize = jedis.llen(listKey);
        System.out.println("size ==> " + keySize);
        // -1 表示直到 list 最后一个元素(redis 支持倒序索引)
        List<String> listValues = jedis.lrange(listKey, 0, -1);
        listValues.forEach(System.out::println);

        sentinelPool.destroy();
        sentinelPool.close();
    }
}
