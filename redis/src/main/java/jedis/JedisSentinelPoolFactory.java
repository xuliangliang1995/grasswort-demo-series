package jedis;

import constants.IpConstants;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2021/4/13.
 *
 * @author xuliangliang
 */
public class JedisSentinelPoolFactory {

    public static JedisSentinelPool sentinelPool() {
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
        return new JedisSentinelPool("mymaster", sentinels, poolConfig);
    }
}
