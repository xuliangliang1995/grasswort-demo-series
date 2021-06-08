package monotonousstack;

import java.util.Stack;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/8
 */
public class MonotonousStackProblem002 {
    /**
     * 问题描述
     * 1， 给定一个正数数组 arr
     * 2， 其所有的子数组都有一个指标 A: 子数组元素之和 * 子数组中最小元素 A = sum(subArr) * min(subArr)
     * 3， 求其所有子数组中 A 指标最大的值
     */
    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 6, 10, 7, 2, 1};
        System.out.println(func1(arr));
    }


    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int func1(int[] arr) {
        int[][] result = func2(arr);
        int[] arrPlus = func3(arr);
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int hypothesisMinValue = arr[i];
            int left =  result[i][0] == -1 ? 0 : result[i][0] + 1;
            int right = result[i][1] == -1 ? arr.length - 1 : (result[i][1] - 1);

            int subQ = (arrPlus[right] - ((left - 1) >= 0 ? arrPlus[left - 1] : 0)) * hypothesisMinValue;
            q = Math.max(q, subQ);
        }
        return q;
    }


    /**
     * 预处理前缀和数组
     * O（n)
     * @param arr
     * @return
     */
    private static int[] func3(int[] arr) {
        int[] arrPlus = new int[arr.length];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
           sum = sum + arr[i];
           arrPlus[i] = sum;
        }
        return arrPlus;
    }


    /**
     * 利用单调栈结构求出每个索引位置，左右两侧距离它最近的比它值小索引位置
     * O(n)
     * @param arr
     * @return
     */
    private static int[][] func2(int[] arr) {
        int[][] result = new int[arr.length][2];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (! stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                Integer lastIndex = stack.pop();
                result[lastIndex][0] = stack.isEmpty() ? -1 : stack.peek();
                result[lastIndex][1] = i;
            }
            stack.push(i);
        }

        while (! stack.isEmpty()) {
            Integer lastIndex = stack.pop();
            result[lastIndex][0] = stack.isEmpty() ? -1 : stack.peek();
            result[lastIndex][1] = -1;
        }

        return result;
    }
}
