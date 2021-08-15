package codetop;

/**
 * @author xuliangliang
 * @Description 最长回文子串（对数器）
 * @Date 2021/8/15
 */
public class LongestPalindromeDetector {

    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] arr = s.toCharArray();
        int maxLength = 1;
        int L = 0;
        int R = 0;
        for (int l = 0; l < arr.length; l++) {
            int r = arr.length - 1;
            while (r >= l) {
                boolean isPalindrome = isPalindrome(arr, l, r);
                if (isPalindrome) {
                    int length = r - l + 1;
                    if (length > maxLength) {
                        maxLength = length;
                        L = l;
                        R = r;
                    }
                    break;
                }
                r--;
            }
        }
        char[] palindrome = new char[maxLength];
        System.arraycopy(arr, L, palindrome, 0, maxLength);
        return new String(palindrome);
    }

    public static boolean isPalindrome(String s) {
        return isPalindrome(s.toCharArray(), 0, s.length() - 1);
    }

    private static boolean isPalindrome(char[] arr, int l, int r) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        while (l <= r) {
            if (arr[l++] != arr[r--]) {
                return false;
            }
        }
        return true;
    }
}
