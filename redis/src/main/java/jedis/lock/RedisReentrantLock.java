package jedis.lock;

import jedis.lock.id.ThreadDistributedID;
import jedis.util.JedisLuaCacheableExecutor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class RedisReentrantLock {

    private final Pool<Jedis> jedisPool;

    private long defaultExpireSeconds = 1000L;

    public RedisReentrantLock(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取锁
     * @param lockKey
     */
    public void lock(String lockKey) {
        while (true) {
            boolean lockSuccess = tryLock(lockKey, defaultExpireSeconds);
            if (lockSuccess) {
                break;
            }
            // 无限循环直到获取锁,可以优化成二阶段锁,多次尝试无果后加入队列
        }
    }

    /**
     * 尝试获取锁
     * @param
     * @param lockKey
     * @param expireSeconds
     */
    public boolean tryLock(String lockKey, long expireSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            Object res = JedisLuaCacheableExecutor.evalLuaScript(jedis, LOCK_SCRIPT,
                    1, lockKey, ThreadDistributedID.id(), String.valueOf(expireSeconds));
            int lockCount = Integer.parseInt(String.valueOf(res));
            // System.out.println("[" + lockKey+ "] lock  >>>> " + lockCount);
            return lockCount > 0;
        }
    }


    /**
     * 释放锁
     * @param lockKey
     */
    public void unlock(String lockKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            Object res = JedisLuaCacheableExecutor.evalLuaScript(jedis, UNLOCK_SCRIPT,
                    1, lockKey, ThreadDistributedID.id());
            // System.out.println("[" + lockKey + "]unlock >>>> " + res);
        }
    }

    private final static String LOCK_SCRIPT = "local lock_key = KEYS[1]\n" +
            "local mutex_id = ARGV[1]\n" +
            "local expire_seconds = ARGV[2]\n" +
            "local lock_id = redis.call('HGET', lock_key, \"ID\");\n" +
            "if (lock_id) then\n" +
            "    if (lock_id == mutex_id) then\n" +
            "        redis.call(\"HINCRBY\", lock_key, \"lock_count\", \"1\");\n" +
            "        redis.call(\"EXPIRE\", lock_key, expire_seconds);\n" +
            "        local lock_count = redis.call(\"HGET\", lock_key, \"lock_count\");\n" +
            "        return lock_count;\n" +
            "    else\n" +
            "        return \"0\"\n" +
            "    end\n" +
            "else\n" +
            "    redis.call(\"HMSET\", lock_key, \"ID\", mutex_id, \"lock_count\", \"1\");\n" +
            "    redis.call(\"EXPIRE\", lock_key, expire_seconds);\n" +
            "    return \"1\"\n" +
            "end";

    private final static String UNLOCK_SCRIPT = "local lock_key = KEYS[1]\n" +
            "local mutex_id = ARGV[1]\n" +
            "local lock_id = redis.call('HGET', lock_key, \"ID\");\n" +
            "if (lock_id) then\n" +
            "    if (lock_id == mutex_id) then\n" +
            "        redis.call(\"HINCRBY\", lock_key, \"lock_count\", \"-1\");\n" +
            "        local lock_count = redis.call(\"HGET\", lock_key, \"lock_count\");\n" +
            "        if (lock_count == \"0\") then\n" +
            "            redis.call(\"DEL\", lock_key);\n" +
            "        end\n" +
            "        return lock_count;\n" +
            "    else\n" +
            "        return \"0\"\n" +
            "    end\n" +
            "else\n" +
            "    return \"0\"\n" +
            "end";
}
