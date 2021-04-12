package jedis;

import redis.clients.jedis.Jedis;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/12
 */
public class JedisSingleNodeDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.16.13.2");
        jedis.set("key", "value");
        jedis.close();
    }
}
