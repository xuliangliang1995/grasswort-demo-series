package bit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/7
 */
public class Demo2 {

    /**
     * 一个数组中一个数出现了奇数次，其他数出现了偶数次，打印出这个数
     */
    public static void main(String[] args) {
        int[] arr = {2, 1, 2, 4, 1, 3, 3};
        int eor = 0;
        for (int i : arr) {
            eor = eor ^ i;
        }
        System.out.println(eor);
    }
}
