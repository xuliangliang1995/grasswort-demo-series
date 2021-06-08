package monotonousstack;

import java.util.Stack;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/8
 */
public class MonotonousStackProblem003 {
    /**
     * 1. 给定一个二维数组 arr ，其中的值不是 0 就是 1
     * 例如： [1, 0, 1, 1, 1]
     *       [1, 1, 1, 1, 1]
     *       [0, 1, 1, 1, 1]
     *       [1, 1, 1, 1, 1]
     * 2. 返回全部由 1 组成的最大子矩阵，里面有多少个 1 ？
     *
     */

    public static void main(String[] args) {
        int[] arr1 = {1, 0, 1, 1, 1};
        int[] arr2 = {1, 1, 1, 1, 1};
        int[] arr3 = {0, 1, 1, 1, 1};
        int[] arr4 = {1, 1, 1, 1, 1};
        int[][] arr = {arr1, arr2, arr3, arr4};
        System.out.println(func1(arr));
    }

    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int func1(int[][] arr) {
        int q = 0;
        for (int i = 0; i < arr.length; i++) {
            int max = func2(arr[i]);
            q = Math.max(max, q);
            if (i + 1 <= arr.length - 1) {
                int[] nextLevel = arr[i+1];
                for (int j = 0; j < nextLevel.length; j++) {
                    if (nextLevel[j] == 1) {
                        nextLevel[j] = nextLevel[j] + arr[i][j];
                    }
                }
            }
        }
        return q;
    }

    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int func2(int[] arr) {
        int[][] result = func1(arr);
        int q = 0;
        for (int i = 0; i < arr.length; i++) {
            int hypothesisMinValue = arr[i];
            int left =  result[i][0] == -1 ? 0 : result[i][0] + 1;
            int right = result[i][1] == -1 ? arr.length - 1 : (result[i][1] - 1);
            int subQ = (right - left + 1) * hypothesisMinValue;
            q = Math.max(q, subQ);
        }
        return q;
    }


    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int[][] func1(int[] arr) {
        int[][] result = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (! stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int lastIndex = stack.pop();
                result[lastIndex][0] = stack.isEmpty() ? -1 : stack.peek();
                result[lastIndex][1] = i;
            }
            stack.push(i);
        }

        while (! stack.isEmpty()) {
            int lastIndex = stack.pop();
            result[lastIndex][0] = stack.isEmpty() ? -1 : stack.peek();
            result[lastIndex][1] = -1;
        }
        return result;
    }
}
