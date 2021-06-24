package daily.t20210624;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/24
 */
public class Topic002 {

    public static void main(String[] args) {
        String[] arr = {"(())", ")(", "()", "(()", "())"};
        for (String s : arr) {
            print(s);
        }
    }

    /**
     * 是否有效括号字符串
     * 怎么判断一个字符串是否有效括号字符串?
     * 1. 任意前缀右括号数不能多于左括号数
     * 2. 整体右括号数 == 左括号数
     * @param str
     * @return
     */
    private static boolean isValidBracketsStr(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        char[] chars = str.toCharArray();
        int leftCount = 0;
        int rightCount = 0;
        final char leftBracket = '(';
        for (char aChar : chars) {
            boolean isLeft = leftBracket == aChar;
            if (isLeft) {
                leftCount++;
            } else {
                rightCount++;
            }
            if (rightCount > leftCount) {
                return false;
            }
        }
        return rightCount == leftCount;
    }

    /**
     * 至少需要添加多少个括号校正
     * @param str
     * @return
     */
    private static int needBracketCount(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int needBracketCount = 0;

        char[] chars = str.toCharArray();
        int leftCount = 0;
        int rightCount = 0;
        final char leftBracket = '(';
        for (char aChar : chars) {
            boolean isLeft = leftBracket == aChar;
            if (isLeft) {
                leftCount++;
            } else {
                rightCount++;
            }
            if (rightCount > leftCount) {
                leftCount++;
                needBracketCount++;
            }
        }
        return needBracketCount + (leftCount - rightCount);
    }

    private static void print(String str) {
        boolean isValid = isValidBracketsStr(str);
        if (isValid) {
            System.out.println(str + " 是有效括号字符串.");
        } else {
            System.out.println(str + " 不是有效括号字符串. 需要添加至少 " + needBracketCount(str) + "个括号.");
        }
    }

    @Test
    public void test001() {
        String str = "((()))";
        assertTrue(isValidBracketsStr(str));
    }

    @Test
    public void test002() {
        String str = ")()(";
        assertFalse(isValidBracketsStr(str));
        assertEquals(2, needBracketCount(str));
    }
}
