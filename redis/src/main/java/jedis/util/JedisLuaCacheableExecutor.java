package jedis.util;

import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class JedisLuaCacheableExecutor {

    private static final Map<String, String> scriptSha1Map = new HashMap<>(10);

    /**
     * 执行 lua 脚本
     * 1. 先对脚本 script 进行 sha1 加密算法进行加密获得 sha1
     * 2. 调用 evalsha(sha1) 执行 lua 脚本
     * 3. 如果抛异常,说明 redis 服务端未曾加载过该 lua 脚本
     * 4. 则调用 scriptLoad(script) => 返回 sha1
     * (此时也可以选则调用 eval(script) 直接执行脚本, 使用 evalsha 可以在 script 内容较多的时候节省带宽)
     * 5. 再次调用 evalsha(sha1) 执行 lua 脚本
     * @param jedis
     * @param script
     * @param keyCount
     * @param params
     * @return
     */
    public static Object evalLuaScript(Jedis jedis, String script, int keyCount, String... params) {
        if (script == null || script.length() == 0) {
            return null;
        }
        String sha1 = sha1(script);
        try {
            return jedis.evalsha(sha1, keyCount, params);
        } catch (JedisNoScriptException e) {
            // 第一次执行,没有加载脚本,会抛异常
            e.printStackTrace();
            String sha2 = jedis.scriptLoad(script);
            assert sha1.equals(sha2);
            return jedis.evalsha(sha1, keyCount, params);
        }
    }

    /**
     * 获取 sha1
     * @param script
     * @return
     */
    private static String sha1(String script) {
        if (! scriptSha1Map.containsKey(script)) {
            scriptSha1Map.put(script, DigestUtils.sha1Hex(script));
        }
        return scriptSha1Map.get(script);
    }
}
