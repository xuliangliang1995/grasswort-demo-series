package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import jedis.util.JedisLuaExecutor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/16
 */
public class LuaOperationDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        // example 1  解释器有缓存,不会每次都编译
        Object result1 = jedis.eval("return {KEYS[1], KEYS[2], ARGV[1], ARGV[2]}",
                2, "key1", "key2", "arg1", "arg2");
        System.out.println(result1);

        // example 2 evalsha 主要是为了节省带宽
        String sha1 = jedis.scriptLoad("return redis.pcall('set', KEYS[1], ARGV[1])");
        Object result2 = jedis.evalsha(sha1, 1, "evalKey", "evalValue");
        System.out.printf("evalsha %s >>> %s\n", sha1, result2);

        // example 3
        String luaScript = "return redis.call('set', KEYS[1], ARGV[1])";
        Object result3 = JedisLuaExecutor.evalLuaScript(jedis, luaScript, 1, "sha1Key", "sha1Val");
        String val = jedis.get("sha1Key");
        System.out.println(result3);
        System.out.println(val);

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
