package hash.hashes;

import hash.Hash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public class PjwHash implements Hash {
    /**
     * 该散列 算法是基于贝尔实验室的 彼得J 温伯格 的的研究 。在Compilers一书中（原则，技术和工具），建议采用这个算法的散列函数的哈希方法。
     * @param s
     * @return
     */
    @Override
    public long hash(String s) {
        long bitsInUnsignedInt = 4 * 8;
        long threeQuarters     = (bitsInUnsignedInt  * 3) / 4;
        long oneEighth         = bitsInUnsignedInt / 8;
        long highBits          = (long)(0xFFFFFFFF) << (bitsInUnsignedInt - oneEighth);
        long hash              = 0;
        long test;
        for(int i = 0; i < s.length(); i++)
        {
            hash = (hash << oneEighth) + s.charAt(i);
            if((test = hash & highBits)  != 0)
            {
                hash = (( hash ^ (test >> threeQuarters)) & (~highBits));
            }
        }
        return hash;
    }
}
