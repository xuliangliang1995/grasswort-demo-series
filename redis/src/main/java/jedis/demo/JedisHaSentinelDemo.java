package jedis.demo;

import constants.IpConstants;
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
        sentinels.add(IpConstants.ipPort(16379));
        sentinels.add(IpConstants.ipPort(16380));
        sentinels.add(IpConstants.ipPort(16381));

        // pool config
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMaxTotal(20);

        // create jedis sentinel pool
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, poolConfig);

        try (Jedis jedis = jedisSentinelPool.getResource()) {
            jedis.set("sentinel", "sentinelVal");
        }

        jedisSentinelPool.destroy();
        jedisSentinelPool.close();
    }
}
