package codetop;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/8/15
 */
public class LongestPalindrome002Test {

    private final LongestPalindrome002 longestPalindrome002 = new LongestPalindrome002();

    @RepeatedTest(1000)
    public void test() {
        String randomString = RandomStringUtils.randomAlphabetic(30);
        String str1 = LongestPalindromeDetector.longestPalindrome(randomString);
        String str2 = longestPalindrome002.longestPalindrome(randomString);
        assertEquals(str1.length(), str2.length());
        assertTrue(LongestPalindromeDetector.isPalindrome(str2));
    }
}
