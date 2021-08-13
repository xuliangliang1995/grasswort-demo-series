package daily.t20210809;

import sort.util.IntArrayUtil;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/8/12
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        System.out.println(longestPalindrome.longestPalindrome("xaabacxcabaaxcabaax"));
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] charArr = s.toCharArray();
        char[] charArrPlus = toPalindromeCharArray(charArr);
        int[] charArrPlusPalindromeRadius = toRadiusArr(charArrPlus);
        int maxRadius = 0;
        int start = 0;
        int length = 1;

        for (int i = 0; i < charArrPlus.length; i++) {
            int radius = charArrPlusPalindromeRadius[i];
            if (radius > maxRadius) {
                // a b c
                // #a#b#c#
                start = (i - radius + 2) / 2;
                length = radius - 1;
                maxRadius = radius;
            }
        }

        char[] longestPalindrome = new char[length];
        for (int i = 0; i < length; i ++) {
            longestPalindrome[i] = charArr[start + i];
        }

        return new String(longestPalindrome);
    }

    // @x@a@a@b@a@c@  x @c@a@b@a@a@x@c@a@b@a@a@x@
    // 1212321412121(14)12121412
    public int[] toRadiusArr(char[] charArrPlus) {
        int[] radius = new int[charArrPlus.length];
        int C = -1;
        int R = 0;
        for (int i = 0; i < charArrPlus.length; i++) {
            if (i < R) {
                int mirrorIndex = 2 * C - i;
                int mirrorIndexRadius = radius[mirrorIndex];
                int mirrorLeftEdgeIndex = mirrorIndex - mirrorIndexRadius + 1;
                int C_left = 2 * C - R;
                radius[i] = mirrorLeftEdgeIndex >= C_left ? mirrorIndexRadius : (R - i + 1);
            }
            while (R < charArrPlus.length && (2 * i - R) >= 0 && charArrPlus[R] == charArrPlus[2 * i - R]) {
                C = i;
                R++;
                radius[i] = radius[i] + 1;
            }
        }
        return radius;
    }

    // xaabacxcabaaxcabaax -> @x@a@a@b@a@c@x@c@a@b@a@a@x@c@a@b@a@a@x@
    public char[] toPalindromeCharArray(char[] charArr) {
        char[] newCharArr = new char[charArr.length * 2 + 1];
        // 0 1(0) 2 3(1) 4 5(2)
        for (int i = 0; i < newCharArr.length; i++) {
            newCharArr[i] = (i % 2 == 0) ? '@' : charArr[i / 2];
        }
        System.out.println(new String(newCharArr));
        return newCharArr;
    }
}
