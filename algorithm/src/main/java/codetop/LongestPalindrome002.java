package codetop;

/**
 * @author xuliangliang
 * @Description 最长回文字符串（动态规划）
 * @Date 2021/8/15
 */
public class LongestPalindrome002 {

    public static void main(String[] args) {
        String s = "xaabacxcabaaxcabaax";
        System.out.println(new LongestPalindrome002().longestPalindrome(s));
    }

    /**
     * 最长回文字符串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        char[] arr = s.toCharArray();

        boolean[][] dp = new boolean[arr.length][arr.length];

        int maxLength = 1;
        int L = 0;

        for (int i = 0; i < arr.length; i++) {
            dp[i][i] = true;
        }

        for (int l = arr.length - 1; l >= 0; l--) {
            for (int r = l + 1; r < arr.length; r++) {
                if (r == l + 1) {
                    dp[l][r] = arr[l] == arr[r];
                } else {
                    dp[l][r] = l + 1 < arr.length && dp[l + 1][r - 1] && arr[l] == arr[r];
                }
                if (dp[l][r]) {
                    int length = r - l + 1;
                    if (length > maxLength) {
                        maxLength = length;
                        L = l;
                    }
                }
            }
        }

        char[] palindrome = new char[maxLength];
        System.arraycopy(arr, L, palindrome, 0, maxLength);
        return new String(palindrome);
    }


}
