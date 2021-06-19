package array;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/19
 */
public class ArrayProblem002 {

    /**
     * 数组 arr (可为负数)
     * 一个数字 K
     * 求 arr 子数组之和 = K 的最长子数组长度
     */
    public static void main(String[] args) {
        int[] arr = {-1, -1, 1, -1, 4, 2, -5, 6, 2, -1, -1, 2, 0, 3};
        int K = 2;
        System.out.println(process(arr, K));
    }

    /**
     *
     * @param arr
     * @param K
     * @return
     */
    private static int process(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] preSum = new int[arr.length];
        int sum = 0;
        Map<Integer, Integer> earliestSumIndexMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            preSum[i] = sum;
            if (! earliestSumIndexMap.containsKey(sum)) {
                earliestSumIndexMap.put(sum, i);
            }
        }

        int maxLength = 0;
        for (int i = 0; i < arr.length; i++) {
            int currentSum = preSum[i];
            int diff = currentSum - K;
            int earliestIndex = earliestSumIndexMap.getOrDefault(diff, -1);
            if (earliestIndex > -1 && earliestIndex < i) {
                maxLength = Math.max(maxLength, i - earliestIndex);
            }
        }
        return maxLength;
    }
}
