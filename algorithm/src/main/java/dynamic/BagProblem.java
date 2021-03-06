package dynamic;

import java.util.Arrays;

/**
 * @author ðºxuliangliangðº
 * @Description èåé®é¢
 * @Date 2021/6/12
 */
public class BagProblem {

    /**
     * é®é¢æè¿°
     * è®¾ U = {U1,U2,U3, ... Un} æ¯ä¸ä¸ªç©åçéå
     * Sj è¡¨ç¤ºç©å Uj çä½ç§¯
     * Vj è¡¨ç¤ºç©å Uj çä»·å¼
     * C è¡¨ç¤ºèåå®¹é
     * æ¯ç§ç©åæ°éæ é, æ±å°ä»»æç»åçç©åæ¾å°èåä¸­çæå¤§ä»·å¼.
     */
    public static void main(String[] args) {
        int[] sArr = {2, 3, 4, 7};
        int[] vArr = {1, 3, 5, 9};
        int C = 10;
        // å¨æè§å1 : èªé¡¶åä¸
        int[] maxV = new int[C + 1];
        Arrays.fill(maxV, -1);
        System.out.println(process001(sArr, vArr, C, maxV));
        // å¨æè§å2 : èªåºåä¸
        System.out.println(process002(sArr, vArr, C));
    }

    /**
     * èªé¡¶åä¸
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
     * èªåºåä¸
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
