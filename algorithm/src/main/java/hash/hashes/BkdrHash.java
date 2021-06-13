package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class BkdrHash implements Hash {

    /**
     * 这个算法来自Brian Kernighan 和 Dennis Ritchie的 The C Programming Language。
     * 这是一个很简单的哈希算法,使用了一系列奇怪的数字,形式如31,3131,31...31,看上去和DJB算法很相似。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long seed = 131;
        // 31 131 1313 13131 131313 etc..
        long hash = 0;
        for(int i = 0; i < s.length(); i++)
        {
            hash = (hash * seed) + s.charAt(i);
        }
        return hash;
    }
}
