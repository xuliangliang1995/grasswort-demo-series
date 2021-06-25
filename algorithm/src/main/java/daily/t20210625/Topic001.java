package daily.t20210625;

import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/25
 */
public class Topic001 {

    /**
     * 给定一个由 "(" 和 ")" 组成的字符串,判断该字符串中的最长有效括号子串长度
     */
    public static void main(String[] args) {
        String[] arr = {")()())))()", "(()))", "()", ")(", ")))", ")((()))",
        ")))((()(()"};
        for (String s : arr) {
            printResult(s);
        }
    }

    private static void printResult(String str) {
        System.out.println(str + " 中最长有效括号子串长度为 " + process001(str));
        System.out.println(str + " 中最长有效括号子串长度为 " + process002(str));
    }

    /**
     * 解法 1 : 正反各来一遍
     * @param str
     * @return
     */
    private static int process001(String str) {
        if (str == null || str.length() < 2) {
            return 0;
        }
        char[] arr = str.toCharArray();
        int leftCount = 0;
        int rightCount = 0;

        int ans = 0;

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ('(' == c) {
                leftCount++;
            } else {
                rightCount++;
            }
            if (leftCount <= rightCount) {
                ans = Math.max(ans, leftCount * 2);
                if (leftCount < rightCount) {
                    leftCount = 0;
                    rightCount = 0;
                }
            }
        }

        leftCount = 0;
        rightCount = 0;

        for (int i = arr.length - 1; i >= 0; i--) {
            char c = arr[i];
            if ('(' == c) {
                leftCount++;
            } else {
                rightCount++;
            }
            if (leftCount >= rightCount) {
                ans = Math.max(ans, rightCount * 2);
                if (leftCount > rightCount) {
                    leftCount = 0;
                    rightCount = 0;
                }
            }
        }
        return ans;
    }

    /**
     * 解法 2 : 动态规划
     * @param str
     * @return
     */
    private static int process002(String str) {
        if (str == null || str.length() < 2) {
            return 0;
        }
        char[] arr = str.toCharArray();
        int[] dp = new int[arr.length];
        int ans = 0;

        for (int i = 1; i < arr.length; i++) {
            int subA = 0;
            if (')' == arr[i]) {
                int pre = i - 1 - dp[i - 1];
                if (pre >= 0 && '(' == arr[pre]) {
                    subA = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            dp[i] = subA;
            ans = Math.max(ans, subA);
        }

        return ans;
    }

    @RepeatedTest(100)
    public void test() {
        char[] arr = new char[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.random() > 0.5 ? ')' : '(';
        }
        String str = new String(arr);
        if (process001(str) != process002(str)) {
            System.out.println(str);
        }
        assertEquals(process001(str), process002(str));
    }

}
