package daily.t20210629;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/29
 */
public class Topic001 {


    public static void main(String[] args) {
        int[] arr = {1, 5, 7, 9, 10, 2};
        System.out.println(process(arr));
    }


    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int process(int[] arr) {
        int[] leftMax = new int[arr.length];
        int leftMaxNum = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            leftMaxNum = Math.max(arr[i], leftMaxNum);
            leftMax[i] = leftMaxNum;
        }

        int[] rightMax = new int[arr.length];
        int rightMaxNum = Integer.MIN_VALUE;
        for (int i = arr.length - 1; i >= 0; i--) {
            rightMaxNum = Math.max(arr[i], rightMaxNum);
            rightMax[i] = rightMaxNum;
        }

        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            max = Math.max(Math.abs(leftMax[i] - rightMax[i + 1]), max);
        }
        return max;
    }
}
