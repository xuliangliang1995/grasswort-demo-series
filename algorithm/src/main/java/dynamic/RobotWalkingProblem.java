package dynamic;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/3
 */
public class RobotWalkingProblem {
    /**
     * 问题描述:
     * 有 1 ~ N, N 个位置
     * 机器人只能向左或向右移动一步.(不能越出边界)
     * 给定机器人当前位置 Current
     * 给定目标位置为 aim
     * 指定步数 K
     * 求 : 机器人经过 K 步后 从 Current 走到 aim , 共有几种移动方式
     */

    public static void main(String[] args) {
        int N = 5;
        int cur = 2;
        int aim = 4;
        // 步数不要设置太大,不然会成指数级上升
        int K = 4;
        System.out.println(func1(cur, aim, K, N));
        System.out.println(func2(cur, aim, K, N));
        System.out.println(func3(cur, aim, K, N));
    }

    /**
     * 暴力递归
     * @param cur
     * @param aim
     * @param K
     * @param N
     * @return
     */
    private static int func1(int cur, int aim, int K, int N) {
        if (K == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return func1(2, aim, K - 1, N);
        }
        if (cur == N) {
            return func1(cur - 1, aim, K - 1, N);
        }
        return func1(cur + 1, aim, K - 1, N)
                + func1(cur - 1, aim, K - 1, N);
    }

    /**
     * 自顶向下动态规划
     * @param cur
     * @param aim
     * @param K
     * @param N
     * @return
     */
    private static int func2(int cur, int aim, int K, int N) {
        int[][] cache = new int[N + 1][K + 1];
        for (int i = 0; i <= cur; i++) {
            for (int j = 0; j <= K; j++) {
                cache[i][j] = -1;
            }
        }
        return process2(cur, aim, K, N, cache);
    }

    private static int process2(int cur, int aim, int K, int N, int[][] cache) {
        if (cache[cur][K] != -1) {
            return cache[cur][K];
        }
        int ans = 0;
        if (K == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = func1(2, aim, K - 1, N);
        } else if (cur == N) {
            ans = func1(cur - 1, aim, K - 1, N);
        } else {
            ans = func1(cur + 1, aim, K - 1, N)
                    + func1(cur - 1, aim, K - 1, N);
        }
        cache[cur][K] = ans;
        return ans;
    }


    /**
     * 自底向上动态规划
     * @param cur
     * @param aim
     * @param K
     * @param N
     * @return
     */
    private static int func3(int cur, int aim, int K, int N) {
        int[][] cache = new int[N + 1][K + 1];
        cache[aim][0] = 1;
        for (int i = 1; i <= K ; i++) {
            cache[1][i] = cache[2][i - 1];
            for (int j = 2; j < N ; j++) {
                cache[j][i] = cache[j - 1][i - 1]
                        + cache[j + 1][i - 1];
            }
            cache[N][i] = cache[N - 1][i - 1];
        }
        return cache[cur][K];
    }
}
