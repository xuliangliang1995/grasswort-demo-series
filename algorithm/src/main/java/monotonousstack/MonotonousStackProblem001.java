package monotonousstack;

import java.util.Stack;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/8
 */
public class MonotonousStackProblem001 {
    /**
     * 问题描述：
     * 1. 给定一个数组 arr , 数组元素皆为正数， 且不重复
     * 2. 打印出每个位置左右两侧离它最近的比它小的值。（没有用 -1 表示）
     * 例如： arr = [1, 3, 2]
     * 0 号位置 ： 左 ： -1， 右 ： -1
     * 1 号位置 ： 左 ： 1，  右 ： 2
     * 2 号位置 ： 左 ： 1，  右 ： -1
     */
    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 6, 8, 2};
        int[][] result = process(arr);
        for (int i = 0; i < result.length; i++) {
            System.out.printf("%d:[%d, %d]\n", i, result[i][0], result[i][1]);
        }
    }

    /**
     * 返回二维数组
     * 【思路： 利用单调栈结构可以快速找到左右两侧最近的小于（或大于）当前位置的值】
     * [
     *  0 : [左， 右],
     *  1 : [左， 右]
     * ]
     * 时间复杂度是 O(n)
     * @param arr
     * @return
     */
    private static int[][] process(int[] arr) {
        int[][] result = new int[arr.length][2];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];
            while (! stack.isEmpty() && arr[stack.peek()] > val) {
                Integer lastIndex = stack.pop();
                result[lastIndex][1] = val;
                result[lastIndex][0] = stack.isEmpty() ? -1 : arr[stack.peek()];
            }
            stack.push(i);
        }

        while (! stack.isEmpty()) {
            Integer lastIndex = stack.pop();
            result[lastIndex][1] = -1;
            result[lastIndex][0] = stack.isEmpty() ? -1 : arr[stack.peek()];
        }
        return result;
    }
}
