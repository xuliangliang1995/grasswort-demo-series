package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class DjbHash implements Hash {

    /**
     * 这个算法是Daniel J.Bernstein 教授发明的，是目前公布的最有效的哈希函数。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = 5381;
        for(int i = 0; i < s.length(); i++)
        {
            hash = ((hash << 5) + hash) + s.charAt(i);
        }
        return hash;
    }
}
