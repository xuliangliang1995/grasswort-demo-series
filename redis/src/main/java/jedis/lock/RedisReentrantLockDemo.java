package jedis.lock;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class RedisReentrantLockDemo {


    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        RedisReentrantLock lock = new RedisReentrantLock(sentinelPool);
        String lockKey = "lock_key";
        jedis.del(lockKey);

        // 先测试一下,是否支持可重入
        lock.lock(lockKey);
        lock.lock(lockKey);
        lock.unlock(lockKey);
        lock.unlock(lockKey);

        CountDownLatch latch = new CountDownLatch(10);

        // 再测试是否可以起到互斥的作用
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    lock.lock(lockKey);
                    // do something
                    System.out.println(Thread.currentThread().getId() + " get the lock ..");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } finally {
                    lock.unlock(lockKey);
                    latch.countDown();
                }
            }, "TEST_THREAD");
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
