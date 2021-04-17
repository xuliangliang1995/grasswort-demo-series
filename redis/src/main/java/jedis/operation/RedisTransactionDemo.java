package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class RedisTransactionDemo {

    public static void main(String[] args) throws InterruptedException {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        jedis.set("test1", "1");
        jedis.set("test2", "1");

        jedis.watch("test1");

        Thread anotherThread = new Thread(() -> {
            Jedis jedis1 = sentinelPool.getResource();
            jedis1.set("test1", "1");
            // jedis1.set("test3", "1");
        });

        anotherThread.start();
        anotherThread.join();

        // 监测到 test1 被操作,将会放弃执行事务
        Transaction transaction = jedis.multi();
        transaction.incr("test1");
        transaction.incr("test2");
        transaction.exec();
        /// 放弃事务执行
        // transaction.discard();

        System.out.println(jedis.get("test1"));
        System.out.println(jedis.get("test2"));

        sentinelPool.destroy();
        sentinelPool.close();


    }
}
