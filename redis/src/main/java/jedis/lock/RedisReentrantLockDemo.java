package jedis.lock;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.concurrent.TimeUnit;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class RedisReentrantLockDemo {

    public static volatile int count = 10;

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        RedisReentrantLock lock = new RedisReentrantLock(sentinelPool);
        String lockKey = "lock_key";
        jedis.del(lockKey);

        // 先测试一下,是否支持可重入
        lock.lock(lockKey);
        lock.unlock(lockKey);
        lock.lock(lockKey);
        lock.unlock(lockKey);

        // 再测试是否可以起到互斥的作用
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    lock.lock(lockKey);
                    // do something
                    System.out.println(Thread.currentThread().getId() + " get the lock ..");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        count--;
                        System.out.println(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock(lockKey);
                }
            }, "TEST_THREAD");
            thread.start();
        }

        while (true) {
            if (count == 0) {
                break;
            }
        }
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
