package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class JsHash implements Hash {

    /**
     * Justin Sobel写的一个位操作的哈希函数。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = 1315423911;
        for(int i = 0; i < s.length(); i++)
        {
            hash ^= ((hash << 5) + s.charAt(i) + (hash >> 2));
        }
        return hash;
    }
}
