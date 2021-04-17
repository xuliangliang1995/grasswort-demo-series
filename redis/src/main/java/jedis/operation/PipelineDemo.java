package jedis.operation;

import jedis.JedisSentinelPoolFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class PipelineDemo {

    public static void main(String[] args) {
        JedisSentinelPool sentinelPool = JedisSentinelPoolFactory.sentinelPool();
        Jedis jedis = sentinelPool.getResource();

        String key1 = "pipeline_key_1";
        String key2 = "pipeline_key_2";

        jedis.set(key1, "1");
        jedis.set(key2, "2");

        Pipeline pipeline = jedis.pipelined();
        Response<String> key2Val = pipeline.get(key2);
        pipeline.set(key1, "2");
        List<Object> res = pipeline.syncAndReturnAll();
        res.forEach(System.out::println);

        System.out.println(key2Val.get());

        jedis.close();
        sentinelPool.destroy();
        sentinelPool.close();
    }
}
