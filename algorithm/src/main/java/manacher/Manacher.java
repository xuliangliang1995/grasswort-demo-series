package manacher;

/**
 * @author 🌺xuliangliang🌺
 * @Description 回文字符串查找算法
 * @Date 2021/6/9
 */
public class Manacher {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "gggg";
        printAllPalindrome(s);
    }

    /**
     * 打印所有回文字符串
     * @param s
     */
    private static void printAllPalindrome(String s) {
        int[] radiusArr = generatePalindromeRadiusArr(toManacherStr(s.toCharArray()));
        for (int i = 0; i < radiusArr.length; i++) {
            int radius = radiusArr[i];
            if (radius > 1) {
                int start = (i - radius + 2) / 2;
                String palindrome = s.substring(start, start + radius - 1);
                System.out.println(palindrome);
            }
        }
    }

    /**
     * 生成回文半径数组
     * @param arr
     * @return
     */
    private static int[] generatePalindromeRadiusArr(char[] arr) {
        int[] radiusArr = new int[arr.length];
        if (arr.length == 0) {
            return radiusArr;
        }
        int C = -1;
        int R = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i < R) {
                int mirrorIndex = 2 * C - i;
                int l = mirrorIndex + 1 - radiusArr[mirrorIndex];
                int L = 2 * C - R + 1;
                radiusArr[i] = l >= L ? radiusArr[mirrorIndex] : (R - i);
            }

            while (R < arr.length && 2 * i - R >= 0 && arr[R] == arr[2 * i - R]) {
                R++;
                C = i;
                radiusArr[i] = radiusArr[i] + 1;
            }
        }
        return radiusArr;
    }

    /**
     * 例如 abc => #a#b#c#
     * @param arr length > 0
     * @return
     */
    private static char[] toManacherStr(char[] arr) {
        char[] chars = new char[arr.length * 2 + 1];
        int j = 0;
        chars[j++] = '#';
        for (int i = 0; i < arr.length; i++) {
            chars[j++] = arr[i];
            chars[j++] = '#';
        }
        return chars;
    }
}
