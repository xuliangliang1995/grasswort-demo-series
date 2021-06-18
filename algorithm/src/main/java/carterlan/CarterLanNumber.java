package carterlan;

/**
 * @author xuliangliang
 * @Description 卡特兰数
 * @Date 2021/6/18
 */
public class CarterLanNumber {

    /**
     * K(0) = 1, K(1) = 1
     * K(N) = K(0) * K(n - 1) + K(1) * K(n - 2) + ... + K(n - 2) * K(1) + K(n - 1） * K(0)
     * 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440, 9694845, 35357670, 129644790,
     * 477638700, 1767263190, 6564120420, 24466267020, 91482563640, 343059613650, 1289904147324, 4861946401452
     */
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(k1(i));
            System.out.println(k2(i));
        }
    }

    /**
     * 公式 1
     * @param n
     * @return
     */
    public static long k1(int n) {
        if (n == 0 || n == 1) {
            return 1L;
        }
        long[] arr = new long[n + 1];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            long number = 0;
            for (int j = 0; j <= (i - 1)/2; j++) {
                int _j = i - 1 - j;
                number += arr[j] * arr[_j] * (j == _j ? 1 : 2);
            }
            arr[i] = number;
        }

        return arr[n];
    }

    /**
     * 公式 2
     * @param n
     * @return
     */
    public static long k2(int n) {
        if (n == 0) {
            return 1;
        }
        return new Double(c(2 * n, n) - c(2 * n , n - 1)).longValue();
    }

    /**
     * 已超出 long 类型范围，用 double
     * @param a
     * @param b
     * @return
     */
    public static double c(int a, int b) {
        double total = 1;
        double repeated = 1;
        for (int i = 0; i < b; i++) {
            total = total * (a - i);
            repeated = repeated * (b - i);
        }
        return total / repeated;
    }
}
