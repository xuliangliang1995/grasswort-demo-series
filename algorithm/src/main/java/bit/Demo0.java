package bit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/7
 */
public class Demo0 {

    /**
     * bit operation
     * @param args
     */
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        System.out.println(xor(a, b));
        System.out.println(xnor(a, b));
        System.out.println(and(a, b));
        System.out.println(or(a, b));
    }

    /**
     * 异或
     * @param a
     * @param b
     * @return
     */
    private static int xor(int a, int b) {
        return a ^ b;
    }

    /**
     * 同或
     * @param a
     * @param b
     * @return
     */
    private static int xnor(int a, int b) {
        return ~(a ^ b);
    }

    /**
     * 与
     * @param a
     * @param b
     * @return
     */
    private static int and(int a, int b) {
        return a & b;
    }

    /**
     * 或
     * @param a
     * @param b
     * @return
     */
    private static int or(int a, int b) {
        return a | b;
    }
}
