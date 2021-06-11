package kmp;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author 🌺xuliangliang🌺
 * @Description 经典字符串匹配算法
 * @Date 2021/6/8
 */
public class Kmp {

    public static void main(String[] args) {
        System.out.println(indexOf("abcddkdlsdk", "dkd"));
        System.out.println(indexOf("hahah", "a"));
        System.out.println(indexOf("a", "a"));

        // 再随机生成 1000 组数据进行测试
        for (int i = 0; i < 1000; i++) {
            String s1 = RandomStringUtils.randomAlphabetic(20);
            String s2 = RandomStringUtils.randomAlphabetic(5);
            assert s1.indexOf(s2) == indexOf(s1, s2);
        }

    }

    /**
     * kmp
     * @param s1
     * @param s2
     * @return
     */
    private static int indexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() > s1.length()) {
            return -1;
        }
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[] nextArray = nextArray(arr2);

        int x = 0, y = 0;

        while (x < arr1.length && y < arr2.length) {
            if (arr1[x] != arr2[y]) {
                y = nextArray[y];
                if (y >= 0) {
                    continue;
                }
            }
            x++;
            y++;
        }
        return y == arr2.length ? (x - s2.length()) : -1;
    }


    /**
     * nextArray
     * @param arr
     * @return
     */
    private static int[] nextArray(char[] arr) {
        int[] nextArray = new int[arr.length];
        if (arr.length >= 1) {
            nextArray[0] = -1;
        }
        if (arr.length >= 2) {
            nextArray[1] = 0;
        }

        for (int i = 2; i < arr.length; i++) {
            int cursor = i - 1;
            char c = arr[cursor];
            while (true) {
                cursor = nextArray[cursor];
                if (cursor < 0) {
                    nextArray[i] = 0;
                    break;
                }
                if (arr[cursor] == c) {
                    nextArray[i] = cursor + 1;
                    break;
                }
            }
        }
        return nextArray;
    }
}
