package daily.t20210626;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/26
 */
public class Topic002 {


    public static void main(String[] args) {
        int[] result = process001(10);
        for (int i : result) {
            System.out.println(i);
        }
    }

    @Test
    public void runTest() {
        for (int i = 0; i <= 20; i++) {
            assertTrue(test(process001(i)));
            assertTrue(test(process002(i)));
        }
    }


    /**
     * 分治
     * @param M
     * @return
     */
    private static int[] process001(int M) {
        if (M == 0) {
            return new int[0];
        }
        if (M == 1) {
            return new int[]{1};
        }
        int half = (M + 1) / 2;
        int[] base = process001(half);
        int[] arr = new int[M];
        for (int i = 0; i < M; i++) {
            if (i < half) {
                arr[i] = base[i] * 2 - 1;
            } else {
                arr[i] = base[i - half] * 2;
            }
        }
        return arr;
    }

    /**
     * 直接返回 等比数列 1, 2, 4, 8
     * @param M
     * @return
     */
    private static int[] process002(int M) {
        if (M == 0) {
            return new int[0];
        }
        int[] arr = new int[M];
        arr[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i - 1] * 2;
        }
        return arr;
    }


    /**
     * 暴力测试
     * @param arr
     * @return
     */
    private static boolean test(int[] arr) {
        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i < j; i++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (arr[i] + arr[k] == 2 * arr[j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
