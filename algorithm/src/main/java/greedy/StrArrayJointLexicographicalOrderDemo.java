package greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author 🌺xuliangliang🌺
 * @Description 字符串数组拼接最小字典序(贪心例子)
 * @Date 2021/5/29
 */
public class StrArrayJointLexicographicalOrderDemo {

    private static final String[] array = {
            "wor",
            "abd",
            "kks",
            "lsakjdjafsk",
            "kl",
            "qe",
            "oplx",
            "zks",
            "ss",
            "domal",
            "a"
    };

    public static void main(String[] args) {
        System.out.println(jointMinLexicographicalOrderStr(array));
    }

    /**
     * 用传入数组中的所有字符串拼接出最小字典序的字符串
     * @param array
     * @return
     */
    public static String jointMinLexicographicalOrderStr(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        Arrays.sort(array, new MyComparator());
        StringBuilder result = new StringBuilder();
        Arrays.stream(array).forEach(result::append);
        return result.toString();
    }

    static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }
}
