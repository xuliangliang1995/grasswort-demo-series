package manacher;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/25
 */
public class ManacherUtil {

    /**
     * 最长回文子串长度 O(n)
     * @param str
     * @return
     */
    public static int maxManacherLength(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int[] radiusArr = generateManacherRadiusArr(toManacherStr(str.toCharArray()));
        int max = 0;
        for (int i = 0; i < radiusArr.length; i++) {
            int radius = radiusArr[i];
            max = Math.max(max, radius - 1);
        }
        return max;
    }

    /**
     * 打印回文字符串(并非所有)
     * @param s
     */
    private static void printManacher(String s) {
        System.out.println("==========" + s + "=========");
        int[] radiusArr = generateManacherRadiusArr(toManacherStr(s.toCharArray()));
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
    private static int[] generateManacherRadiusArr(char[] arr) {
        int[] radiusArr = new int[arr.length];
        if (arr.length == 0) {
            return radiusArr;
        }
        int cursor = -1;
        int R = 0;
        int C = 0;
        while (++cursor < arr.length) {
            if (cursor > R) {
                R++;
                C = cursor;
            } else {
                int mirrorCursor = 2 * C - cursor;
                int mirrorRadius = radiusArr[mirrorCursor] > 0 ? radiusArr[mirrorCursor] : 1;
                int mirrorCursorLeft = mirrorCursor - mirrorRadius + 1;
                if (mirrorCursorLeft > (2 * C - R)) {
                    radiusArr[cursor] = mirrorRadius;
                    continue;
                }
            }
            while (true) {
                int nextRight = R + 1;
                int nextLeft = 2 * cursor - nextRight;
                if (nextRight >= arr.length || nextLeft < 0 || arr[nextLeft] != arr[nextRight]) {
                    break;
                }
                R++;
                C = cursor;
            }
            radiusArr[cursor] = R - cursor + 1;
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
