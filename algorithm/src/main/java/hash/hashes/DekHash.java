package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class DekHash implements Hash {

    /**
     * 由伟大的Knuth在《编程的艺术 第三卷》的第六章排序和搜索中给出。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long hash = s.length();
        for(int i = 0; i < s.length(); i++)
        {
            hash = ((hash << 5) ^ (hash >> 27)) ^ s.charAt(i);
        }
        return hash;
    }
}
