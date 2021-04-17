package jedis.encoder;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/17
 */
public class SHA1EncoderTest {

    public static void main(String[] args) {
        String sha1 = DigestUtils.sha1Hex("return redis.pcall('set', KEYS[1], ARGV[1])");
        System.out.println(sha1);
    }
}
