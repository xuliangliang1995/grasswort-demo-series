package dynamic;

/**
 * @author 🌺xuliangliang🌺
 * @Description 货币问题 1
 * @Date 2021/6/6
 */
public class CoinProblem001 {

    /**
     * 货币问题 001
     * 给定一个 arr 货币数组(都是正数,且不重复)
     * 每个值都认为是一种面额,且张数不限
     * 再给定一个正数 aim
     * 返回组成 aim 的方法数
     * 例如: arr = {1, 2}, aim = 4
     * 方法如下:
     * 1 + 1 + 1 + 1 = 4
     * 1 + 1 + 2 = 4
     * 2 + 2 = 4
     * 返回结果为 3
     */

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int aim = 5;
        // 暴力递归
        System.out.println(process1(arr, 0, aim));

        // 记忆化搜索
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = -1;
            }
        }
        System.out.println(process2(arr, 0, aim, dp));

        System.out.println(process3(arr, aim));
    }

    /**
     * 返回 arr 中 index 之后(包含 index) 范围内组成 rest 的方法数
     * @param arr
     * @param index
     * @param rest
     * @return
     */
    private static int process1(int[] arr, int index, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (index >= arr.length) {
            return 0;
        }
        int q = 0;
        int curCoin = arr[index];
        for (int zhang = 0; zhang <= rest / curCoin; zhang++) {
            q += process1(arr, index + 1, rest - curCoin * zhang);
        }
        return q;
    }

    /**
     * 自顶向下动态规划(记忆化查询)
     * @param arr
     * @param index
     * @param rest
     * @param dp
     * @return
     */
    private static int process2(int[] arr, int index, int rest, int[][] dp) {
        if (rest == 0) {
            return 1;
        }
        if (index >= arr.length) {
            return 0;
        }
        if (dp[index][rest] != -1) {
            return dp[index][rest];
        }
        int q = 0;
        int curCoin = arr[index];
        for (int zhang = 0; zhang <= rest / curCoin; zhang++) {
            q += process1(arr, index + 1, rest - curCoin * zhang);
        }
        dp[index][rest] = q;
        return q;
    }

    /**
     * 自底向上动态规划(严格表格依赖)
     * @param arr
     * @param rest
     * @return
     */
    private static int process3(int[] arr, int rest) {
        int[][] dp = new int[arr.length][rest + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = arr.length - 1; i >= 0 ; i--) {
            for (int j = 1; j <= rest; j++) {
                dp[i][j] = (i + 1 >= arr.length ? 0 : dp[i + 1][j])
                    + (j - arr[i] < 0 ? 0 : dp[i][j - arr[i]]);
            }
        }
        return dp[0][rest];
    }

}
