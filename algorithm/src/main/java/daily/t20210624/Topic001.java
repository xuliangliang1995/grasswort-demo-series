package daily.t20210624;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/24
 */
public class Topic001 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 6, 7, 9, 10, 11, 12, 13};
        int k = 19;
        System.out.println(maxPoint001(arr, k));
        System.out.println(right(arr, k));
    }
    /**
     *
     * @param arr
     * @param k
     * @return
     */
    private static int maxPoint001(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (k < 0) {
            return -1;
        }
        if (k == 0) {
            return 1;
        }
        int l = 0;
        int r = 0;
        int point = 1;
        while (r < arr.length) {
            if (arr[r] - arr[l] <= k) {
                r++;
                point = Math.max(point, r - l);
            } else {
                l++;
            }
        }
        return point;
    }

    /**
     * 暴力解法
     * @param arr
     * @param k
     * @return
     */
    private static int right(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (k < 0) {
            return -1;
        }
        if (k == 0) {
            return 1;
        }
        int point = 1;
        for (int i = 0; i < arr.length; i++) {
            int j = i;
            while (j < arr.length && arr[j] <= arr[i] + k) {
                j++;
            }
            point = Math.max(point, j - i);
        }
        return point;
    }

    @RepeatedTest(100)
    public void test() {
        int[] arr = new int[100];
        Random random = new Random();
        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i - 1] + random.nextInt(10);
        }
        int k = random.nextInt(30);
        assertEquals(maxPoint001(arr, k), right(arr, k));
    }
}
