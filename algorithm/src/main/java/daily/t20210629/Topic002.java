package daily.t20210629;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/29
 */
public class Topic002 {

    public static void main(String[] args) {
        int[] arr1 = {3, 1, 2, 5, 2, 4};
        int[] arr2 = {4, 5, 1, 3, 2};
        System.out.println(process(arr1));
        System.out.println(process(arr2));
    }

    /**
     * O(n)
     * @param arr
     * @return
     */
    private static int process(int[] arr) {
        int[] leftMax = new int[arr.length];
        int[] rightMax = new int[arr.length];

        int leftMaxNum = 0;
        for (int i = 0; i < arr.length; i++) {
            leftMaxNum = Math.max(leftMaxNum, arr[i]);
            leftMax[i] = leftMaxNum;
        }

        int rightMaxNum = 0;
        for (int i = arr.length - 1; i >= 0 ; i--) {
            rightMaxNum = Math.max(rightMaxNum, arr[i]);
            rightMax[i] = rightMaxNum;
        }

        int sum = 0;
        for (int i = 1; i < arr.length - 1; i++) {
            sum += Math.max(Math.min(leftMax[i - 1], rightMax[i + 1]) - arr[i], 0);
        }
        return sum;
    }
}
