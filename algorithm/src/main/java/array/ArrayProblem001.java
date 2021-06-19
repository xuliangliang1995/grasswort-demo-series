package array;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/19
 */
public class ArrayProblem001 {

    /**
     * 非负数组 arr
     * 一个数字 K
     * 求 arr 子数组之和 = K 的最长子数组长度
     */
    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 4, 6, 7, 10, 12, 1, 6};
        int K = 7;
        System.out.println(process(arr, K));
    }


    /**
     *
     * @param arr 非负数组
     * @param K
     * @return 子数组之和 == K 的最长子数组长度
     */
    private static int process(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int L = 0;
        int R = 0;
        int sum = arr[0];
        int maxLength = 0;
        while (L < arr.length) {
            if (R < L) {
                R = L;
                sum = arr[L];
            }
            if (sum < K) {
                if (R + 1 < arr.length) {
                    sum += arr[++R];
                } else {
                    break;
                }
            }
            if (sum >= K) {
                if (sum == K) {
                    maxLength = Math.max(maxLength, R - L + 1);
                }
                sum -= arr[L++];
            }
        }
        return maxLength;
    }
}
