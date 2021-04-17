package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description 发布/订阅模式
 * @Date 2021/4/17
 */
public class SubscribePublishDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();

        String topicKey = "generic_topic";

        Thread publisher = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            Random random = new Random();
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = String.valueOf(random.nextInt(99));
                System.out.println("PUBLISH : >>>>> " + message);
                jedis.publish(topicKey, message);
            }
        }, "PUBLISHER");

        Thread subscriber1 = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            // 阻塞方法
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.printf("[SUBSCRIBE_1] <<<<<< [%s] : %s\n", channel, message);
                }
            }, topicKey);
        }, "SUBSCRIBE_1");


        Thread subscriber2 = new Thread(() -> {
            Jedis jedis = sentinelPool.getResource();
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.printf("[SUBSCRIBE_2] <<<<<< [%s] : %s\n", channel, message);
                }
            }, topicKey);
        }, "SUBSCRIBE_2");

        publisher.start();
        subscriber1.start();
        subscriber2.start();

        LockSupport.park();
    }
}
