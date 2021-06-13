package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class RsHash implements Hash {

    /**
     * 从Robert Sedgwicks的 Algorithms in C一书中得到了。 我(原文作者)已经 添加了一些简单的优化 的 算法，以加快其散列过程。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for(int i = 0; i < s.length(); i++) {
            hash = hash * a + s.charAt(i);
            a    = a * b;
        }
        return hash;
    }
}
