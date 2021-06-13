package hash.hashes;

import hash.Hash;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 🌺xuliangliang🌺
 * @Description 取模 hash(缩小一下范围)
 * @Date 2021/6/13
 */
public class SelfHash implements Hash {

    private final Hash hash = new ElfHash();

    private final int mod;

    public SelfHash(int mod) {
        this.mod = mod;
    }

    @Override
    public long hash(String s) {
        return Math.abs(hash.hash(DigestUtils.md5Hex(s)) % mod);
    }
}
