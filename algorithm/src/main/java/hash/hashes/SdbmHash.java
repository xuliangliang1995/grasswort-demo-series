package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class SdbmHash implements Hash {
    /**
     * 这个算法在开源的SDBM中使用，似乎对很多不同类型的数据都能得到不错的分布。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = 0;
        for(int i = 0; i < s.length(); i++)
        {
            hash = s.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
}
