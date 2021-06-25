package daily.t20210625;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/25
 */
public class Topic002 {


    public static void main(String[] args) {
        int[] arr1 = {1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1};
        int[] arr2 = {1, 1, 1};
        int[] arr3 = {0, 0, 0};
        System.out.println(process(arr1));
        System.out.println(process(arr2));
        System.out.println(process(arr3));
    }

    /**
     * 1 代表红色 0 代表绿色
     * @param arr
     * @return 需要染几次色,可以使得,任何一个红色都比任意一个绿色离左侧近
     */
    private static int process(int[] arr) {
        int[] redCount = new int[arr.length];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            redCount[i] = sum;
        }

        int redTotal = redCount[arr.length - 1];
        int ans = redTotal;

        for (int i = 0; i < arr.length; i++) {
            // 以 i 为边界,左侧为红(包含 i),右侧为绿
            int subA = (i + 1 - redCount[i]) + (redTotal - redCount[i]);
            ans = Math.min(ans, subA);
        }

        return ans;
    }

}
