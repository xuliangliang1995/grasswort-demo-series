package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description 利用 list 实现阻塞单播队列
 * @Date 2021/4/17
 */
public class BlockedUnicastQueueDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();

        String unicastQueueKey = "unicast_queue";
        sentinelPool.getResource().del(unicastQueueKey);

        Thread publisher = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            Random random = new Random();
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String randomIntVal = String.valueOf(random.nextInt(100));
                System.out.println("[PUBLISHER] : >>>>>> " + randomIntVal);
                jedis.rpush(unicastQueueKey, randomIntVal);
            }

        }, "publisher");

        Thread subscriber1 = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            while (true) {
                List<String> receives = jedis.blpop(unicastQueueKey, "60");
                if (receives.size() > 0) {
                    System.out.printf("SUBSCRIBE[1] receive from [%s] : %s\n", receives.get(0), receives.get(1));
                }
            }
        }, "subscriber_1");


        Thread subscriber2 = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            while (true) {
                List<String> receives = jedis.blpop(unicastQueueKey, "60");
                if (receives.size() > 0) {
                    System.out.printf("SUBSCRIBE[2] receive from [%s] : %s\n", receives.get(0), receives.get(1));
                }
            }
        }, "subscriber_2");

        subscriber1.start();
        subscriber2.start();
        publisher.start();
        // 阻塞住
        LockSupport.park();
    }
}
