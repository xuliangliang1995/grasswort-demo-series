package dynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/5
 */
public class StickerProblem {
    /**
     * 问题描述
     * 给定一个字符串 str
     * 给定一个字符串数组 arr , 出现的都是小写英文, 每个字符串可以视为一张贴纸.
     * 贴纸可以剪开, 每种贴纸都有无数张.
     * 目的是用这些贴纸拼出 str 来.
     * 求 : 最少需要几张贴纸 ?
     */
    public static void main(String[] args) {
        String[] arr = {"dd", "aa", "cc", "b"};
        String str = "abbbc";
        System.out.println(stickers(arr, str));
    }

    /**
     * 至少需要几张贴纸, -1 表示永远拼不出来
     * @param arr
     * @param str
     * @return
     */
    private static int stickers(String[] arr, String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int[][] stickers = new int[arr.length][26];
        for (int i = 0; i < arr.length; i++) {
            stickers[i] = wordFrequencyStatistics(arr[i]);
        }
        int stickersCount = process(stickers, str, new HashMap<>(str.length()));
        return stickersCount == Integer.MAX_VALUE ? -1 : stickersCount;
    }


    private static int process(int[][] stickers, String str, Map<String, Integer> cacheMap) {
        if (str.length() == 0) {
            return 0;
        }
        if (cacheMap.containsKey(str)) {
            return cacheMap.get(str);
        }
        int q = Integer.MAX_VALUE;
        int[] strWf = wordFrequencyStatistics(str);
        for (int i = 0; i < strWf.length; i++) {
            // 贪心
            if (strWf[i] > 0) {
                for (int[] sticker : stickers) {
                    if (sticker[i] > 0) {
                        for (int j = 0; j < sticker.length; j++) {
                            strWf[j] = strWf[j] - sticker[j];
                        }
                        int subQ = 1 + process(stickers, wordFrequencyStrToStr(strWf), cacheMap);
                        q = Math.min(q, subQ);
                    }
                }
                cacheMap.put(str, q);
                break;
            }
        }
        return q;
    }


    /**
     * 词频数组转字符串
     * @param wf
     * @return
     */
    private static String wordFrequencyStrToStr(int[] wf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wf.length; i++) {
            int f = wf[i];
            if (f > 0) {
                char c = (char) (i + 'a');
                for (int j = 0; j < f; j++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 词频统计
     * @param str
     * @return
     */
    private static int[] wordFrequencyStatistics(String str) {
        int[] result = new int[26];
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            int i = aChar - 'a';
            result[i] = result[i] + 1;
        }
        return result;
    }


}
