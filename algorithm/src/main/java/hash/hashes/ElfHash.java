package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class ElfHash implements Hash {

    /**
     * 和PJW很相似，在Unix系统中使用的较多。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = 0;
        long x    = 0;
        for(int i = 0; i < s.length(); i++)
        {
            hash = (hash << 4) + s.charAt(i);
            if((x = hash & 0xF0000000L) != 0)
            {
                hash ^= (x >> 24);
            }
            hash &= ~x;
        }
        return hash;
    }
}
