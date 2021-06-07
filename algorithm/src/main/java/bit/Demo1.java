package bit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/7
 */
public class Demo1 {

    /**
     * 怎么把一个 int 类型的数， 提取出最右侧的 1 来
     * 例如：【1100010】 =》 【0000010】
     */

    public static void main(String[] args) {
        System.out.println(process(1));
    }

    public static int process(int a) {
        return a & -a;
        // return a & (~a + 1);
    }
}
