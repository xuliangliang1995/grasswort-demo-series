package daily.t20210625;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/25
 */
public class Topic001 {

    public static void main(String[] args) {
        String[] arr = {")(())", "(()))", "()", ")(", ")))", ")((()))"};
        for (String s : arr) {
            printResult(s);
        }
    }

    private static void printResult(String str) {
        System.out.println(str + " 中最长有效括号子串长度为 " + process(str));
    }
    /**
     * 给定一个由 "(" 和 ")" 组成的字符串,判断该字符串中的最长有效括号子串长度
     */
    private static int process(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] arr = str.toCharArray();
        int leftCount = 0;
        int rightCount = 0;
        int max = 0;
        // 思路:
        // 1 . 当 rightCount <= leftCount 时, max = rightCount * 2;
        // 2 . 当 rightCount > leftCount 时, max = leftCount * 2, 且由于之前子串不可能与后面子串构成有效括号子串,故清 0 重新开始计算.
        for (char c : arr) {
            if ('(' == c) {
                leftCount++;
            } else {
                rightCount++;
            }
            if (leftCount < rightCount) {
                max = Math.max(max, leftCount * 2);
                leftCount = 0;
                rightCount = 0;
            }
        }

        return Math.max(max, rightCount * 2);
    }

}
