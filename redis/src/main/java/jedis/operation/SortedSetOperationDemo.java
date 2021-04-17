package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class SortedSetOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        String sortedSetKey = "sortedSet";
        jedis.zadd(sortedSetKey, 11, "jerry");
        jedis.zadd(sortedSetKey, 6.5, "tom");
        jedis.zadd(sortedSetKey, 10, "Jane");

        System.out.println(">>>>>");
        jedis.zrange(sortedSetKey, 0, -1)
                .forEach(System.out::println);

        System.out.println(">>>>>");
        jedis.zrangeByScore(sortedSetKey, 7, 11)
            .forEach(System.out::println);

        System.out.println(">>>>>");
        System.out.println(jedis.zrank(sortedSetKey, "jerry"));

        jedis.zrem(sortedSetKey, "jerry");
        System.out.println(">>>>>");
        jedis.zrange(sortedSetKey, 0, -1)
                .forEach(System.out::println);

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
