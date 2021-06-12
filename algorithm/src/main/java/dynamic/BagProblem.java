package dynamic;

import java.util.Arrays;

/**
 * @author 🌺xuliangliang🌺
 * @Description 背包问题
 * @Date 2021/6/12
 */
public class BagProblem {

    /**
     * 问题描述
     * 设 U = {U1,U2,U3, ... Un} 是一个物品的集合
     * Sj 表示物品 Uj 的体积
     * Vj 表示物品 Uj 的价值
     * C 表示背包容量
     * 每种物品数量无限, 求将任意组合的物品放到背包中的最大价值.
     */
    public static void main(String[] args) {
        int[] sArr = {2, 3, 4, 7};
        int[] vArr = {1, 3, 5, 9};
        int C = 10;
        // 动态规划1 : 自顶向下
        int[] maxV = new int[C + 1];
        Arrays.fill(maxV, -1);
        System.out.println(process001(sArr, vArr, C, maxV));
        // 动态规划2 : 自底向上
        System.out.println(process002(sArr, vArr, C));
    }

    /**
     * 自顶向下
     * @param sArr
     * @param vArr
     * @param C
     * @param maxV
     * @return
     */
    private static int process001(int[] sArr, int[] vArr, int C, int[] maxV) {
        if (maxV[C] != -1) {
            return maxV[C];
        }
        int v = 0;
        for (int i = 0; i < sArr.length; i++) {
            int s = sArr[i];
            if (s <= C) {
                int subV = vArr[i] + process001(sArr, vArr, C - s, maxV);
                v = Math.max(v, subV);
            }
        }
        maxV[C] = v;
        return v;
    }

    /**
     * 自底向上
     * @param sArr
     * @param vArr
     * @param C
     * @return
     */
    private static int process002(int[] sArr, int[] vArr, int C) {
        int[] maxV = new int[C + 1];
        for (int i = 0; i <= C; i++) {
            int q = 0;
            for (int j = 0; j < sArr.length; j++) {
                int subQ = sArr[j] <= i ? vArr[j] + maxV[i - sArr[j]] : 0;
                q = Math.max(q, subQ);
            }
            maxV[i] = q;
        }
        return maxV[C];
    }
}
