package bfprt;

import org.apache.commons.lang3.RandomStringUtils;
import sort.util.IntArrayUtil;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/11
 */
public class Bfprt {

    /**
     * 这个数可以任意定制,默认为 5
     */
    private static final int UNIT_ARRAY_SIZE = 5;

    /**
     * 问题描述:
     * 给定一个数组 arr, 返回其中第 K 个数(从小到大)的数值, 要求时间复杂度 O(n)
     */

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int[] arr = IntArrayUtil.randomArray(100, 1, 1000);
            int lastValue = 0;
            for (int j = 1; j <= arr.length; j++) {
                int value = bfprt(arr, 0, arr.length - 1, j);
                assert value >= lastValue;
                System.out.println(value);
            }
        }
    }

    /**
     * 返回 arr  数组, L -> R 之间第 k 个数(升序)的值
     * @param arr
     * @param L
     * @param R
     * @param k
     * @return
     */
    private static int bfprt(int[] arr, int L, int R, int k) {
        assert L >= 0 && L >= R && R < arr.length && k < R - L + 1;
        if (L == R) {
            return arr[L];
        }
        int length = R - L + 1;
        int[] array = new int[length / UNIT_ARRAY_SIZE + (length % UNIT_ARRAY_SIZE > 0 ? 1 : 0)];
        for (int i = 0; i < array.length; i++) {
            int left = L + i * UNIT_ARRAY_SIZE;
            int right = Math.min(left + UNIT_ARRAY_SIZE - 1, R);
            int median = median(arr, left, right);
            array[i] = median;
        }
        int p = medianOfMedian(array);
        int leftCursor = L - 1;
        int rightCursor = R + 1;
        for (int i = L; i < rightCursor ; i++) {
            if (arr[i] < p) {
                IntArrayUtil.swap(arr, i, ++leftCursor);
            } else if (arr[i] > p) {
                IntArrayUtil.swap(arr, i, --rightCursor);
                i--;
            }
        }

        if (leftCursor - L + 1 >= k) {
            return bfprt(arr, L, leftCursor, k);
        }
        if (rightCursor - L + 1 <= k) {
            return bfprt(arr, rightCursor, R, k - (rightCursor - L));
        }
        return p;
    }

    /**
     * 求数组的中位数
     * @param arr
     * @return
     */
    private static int medianOfMedian(int[] arr) {
        if (arr.length <= UNIT_ARRAY_SIZE) {
            return median(arr, 0, arr.length - 1);
        }
        return bfprt(arr, 0, arr.length - 1, (arr.length - 1) / 2);
    }

    /**
     * 获取数组的中位数(数组长度很小,可以视为 O(1) 时间复杂度)
     * @param arr
     * @param L
     * @param R
     * @return
     */
    private static int median(int[] arr, int L, int R) {
        assert R >= L;
        if (L == R) {
            return arr[L];
        }
        for (int i = L + 1; i <= R; i++) {
            int j = i;
            while (j - 1 >= L && arr[j] > arr[j - 1]) {
                IntArrayUtil.swap(arr, j, j - 1);
                j--;
            }
        }
        return arr[L + (R - L) / 2];
    }
}
