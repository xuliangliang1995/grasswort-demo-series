package array;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/19
 */
public class ArrayProblem003 {

    /**
     * 数组 arr (可为负数)
     * 一个数字 K
     * 求 arr 子数组之和 <= K 的最长子数组长度
     */
    public static void main(String[] args) {
        int[] arr = {-1, -1, 1, -1, 1, 1, 4, 2, -5, 6, 2, -1, -1, 2, 0, 3};
        int K = 2;
        System.out.println(process(arr, K));
    }

    private static int process(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] endMin = new int[arr.length];
        int[] endMinIndex = new int[arr.length];
        endMin[arr.length - 1] = arr[arr.length - 1];
        endMinIndex[arr.length - 1] = arr.length - 1;


        for (int i = arr.length - 2; i >= 0 ; i--) {
            endMin[i] = arr[i] + Math.min(endMin[i + 1], 0);
            endMinIndex[i] = endMin[i + 1] < 0 ? endMinIndex[i + 1] : i;
        }
        int maxLength = 0;
        int L = 0;
        int R = 0;
        int sum = 0;
        while (R < arr.length) {
            int nextMin = endMin[R];
            if (sum + nextMin > K) {
                if (L == R) {
                    R = endMinIndex[R] + 1;
                    L = R;
                    sum = 0;
                } else {
                    sum -= arr[L];
                    L++;
                }

            } else {
                sum += endMin[R];
                R = endMinIndex[R] + 1;
                maxLength = Math.max(maxLength, R - L);
            }
        }
        return maxLength;
    }
}
