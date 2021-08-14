package daily.t20210809;

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
        /*int[] radius = new int[charArrPlus.length];
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
        }*/
        PalindromeCursor cursor = new PalindromeCursor(charArrPlus);
        return cursor.radiusArr;
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

    public static class PalindromeCursor {
        private final char[] arr;
        private int[] radiusArr;
        private int rightPoint;
        private int centerPoint;
        private int cursorPoint = -1;

        public PalindromeCursor(char[] arr) {
            this.arr = arr;
            radiusArr = new int[arr.length];
            while (moveCursorPoint() != -1) {};
        }

        public int[] getRadiusArr() {
            return radiusArr;
        }
        /**
         * Current Pointer Can't less than Right Pointer
         * @return
         */
        private int moveCursorPoint() {
            if (cursorPoint == arr.length - 1) {
                return -1;
            }
            cursorPoint++;

            if (cursorPoint > rightPoint) {
                moveRightPoint();
            } else {
                int leftMirrorIndex = 2 * centerPoint - cursorPoint;
                int leftMirrorIndexRadius = Math.max(radiusArr[leftMirrorIndex], 1);
                int leftMirrorExtLeftIndex = leftMirrorIndex - leftMirrorIndexRadius + 1;
                if (leftMirrorExtLeftIndex > (2 * centerPoint - rightPoint)) {
                    radiusArr[cursorPoint] = radiusArr[leftMirrorIndex];
                    return cursorPoint;
                }
            }
            while (tryToMoveRightPoint(arr) != -1) {};
            radiusArr[cursorPoint] = currentExtLength();
            return cursorPoint;
        }

        /**
         *
         * @param arr
         * @return
         */
        private int tryToMoveRightPoint(char[] arr) {
            if (rightPoint == arr.length - 1) {
                return -1;
            }
            int right = rightPoint + 1;
            int left = 2 * cursorPoint - right;
            if (left >= 0 && arr[right] == arr[left]) {
                return moveRightPoint();
            }
            return -1;
        }

        /**
         * If right pointer move, center point should set to current pointer;
         * @return
         */
        private int moveRightPoint() {
            if (rightPoint == arr.length - 1) {
                return -1;
            }
            rightPoint++;
            centerPoint = cursorPoint;
            return rightPoint;
        }

        public int currentExtLength() {
            return rightPoint - cursorPoint + 1;
        }
    }
}
