package jedis.demo;

import constants.IpConstants;
import redis.clients.jedis.Jedis;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/12
 */
public class JedisSingleNodeDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis(IpConstants.ip());
        jedis.set("key", "value");
        jedis.close();
    }
}
