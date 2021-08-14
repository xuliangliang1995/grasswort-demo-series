package codetop;

/**
 * @author xuliangliang
 * @Description 最长回文子串（O（n））
 * @Date 2021/8/14
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        String s = "xaabacxcabaaxcabaax";
        System.out.println(longestPalindrome.longestPalindrome(s));
    }

    /**
     * 最长回文子串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] arr = s.toCharArray();
        char[] extArr = toExtPalindromeArr(arr);
        int[] radiusArr = toRadiusArr(extArr);

        int maxRadius = 1;
        int start = 0;
        int length = 1;
        for (int i = 0; i < radiusArr.length; i++) {
            int radius = radiusArr[i];
            if (radius > maxRadius) {
                maxRadius = radius;
                start = (i - radius + 1) / 2;
                length = radius - 1;
            }
        }

        char[] longestPalindromeArr = new char[length];
        for (int i = 0; i < length; i++) {
            longestPalindromeArr[i] = arr[start + i];
        }
        return new String(longestPalindromeArr);
    }


    /**
     * Palindrome index arr
     * @param arr
     * @return
     */
    private int[] toRadiusArr(char[] arr) {
        int[] result = new int[arr.length];
        int cursor = -1, C = 0, R = 0;

        while (++cursor < arr.length) {
            if (cursor > R) {
                R++;
                C = cursor;
            } else if (cursor < R) {
                int mirrorCursor = 2 * C - cursor;
                int mirrorCursorRadius = result[mirrorCursor] > 0 ? result[mirrorCursor] : 1;
                int mirrorCursorLeft = mirrorCursor - mirrorCursorRadius + 1;
                int L = 2 * C - R;
                if (mirrorCursorLeft > L) {
                    result[cursor] = mirrorCursorRadius;
                    continue;
                }
            }
            while (true) {
                int nextR = R + 1;
                int nextL = 2 * cursor - nextR;
                if (nextL < 0 || nextR >= arr.length || arr[nextL] != arr[nextR]) {
                    break;
                }
                R++;
                C = cursor;
            }

            result[cursor] = R - cursor + 1;
        }
        return result;
    }

    /**
     * aba -> @a@b@a@
     * @param arr
     * @return
     */
    private char[] toExtPalindromeArr(char[] arr) {
        char[] newArr = new char[2 * arr.length + 1];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = i % 2 == 0 ? '@' : arr[i / 2];
        }
        return newArr;
    }
}
