package codetop;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author xuliangliang
 * @Description 最长回文字符串对数器（暴力）
 * @Date 2021/8/15
 */
public class LongestPalindromeTest {

    private final LongestPalindrome longestPalindrome = new LongestPalindrome();

    @RepeatedTest(1000)
    public void test() {
        String randomString = RandomStringUtils.randomAlphabetic(30);
        assertEquals(longestPalindrome(randomString), longestPalindrome.longestPalindrome(randomString));
    }


    public String longestPalindrome(String s) {
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

    private boolean isPalindrome(char[] arr, int l, int r) {
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
