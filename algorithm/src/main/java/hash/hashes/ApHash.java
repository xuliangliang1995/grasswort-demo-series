package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class ApHash implements Hash {
    /**
     * 这是 Arash Partow 贡献的一个哈希函数
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = 0xAAAAAAAA;
        for(int i = 0; i < s.length(); i++)
        {
            if ((i & 1) == 0)
            {
                hash ^= ((hash << 7) ^ s.charAt(i) * (hash >> 3));
            }
            else
            {
                hash ^= (~((hash << 11) + s.charAt(i) ^ (hash >> 5)));
            }
        }
        return hash;
    }
}
