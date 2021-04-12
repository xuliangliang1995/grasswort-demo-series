package jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/12
 */
public class JedisHaSentinelDemo {

    public static void main(String[] args) {
        // sentinels
        Set<String> sentinels = new HashSet<>();
        sentinels.add("172.16.13.2:16379");
        sentinels.add("172.16.13.2:16380");
        sentinels.add("172.16.13.2:16381");

        // pool config
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMaxTotal(20);

        // create jedis sentinel pool
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, poolConfig);

        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set("sentinel", "sentinelVal");

        jedis.close();
        jedisSentinelPool.destroy();
        jedisSentinelPool.close();
    }
}
