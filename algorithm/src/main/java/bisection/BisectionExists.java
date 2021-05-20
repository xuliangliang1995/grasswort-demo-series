package bisection;

/**
 * @author xuliangliang
 * @Description 二分法判断是一个有序数组中是否包含某个数 O(logN)
 * @Date 2020/7/12
 */
public class BisectionExists {

    /**
     * 判断是一个有序数组(从小到大)中是否包含某个数
     * @param sortedArray
     * @param num
     * @return
     */
    public static boolean exists(int[] sortedArray, int num) {
        int L = 0;
        int R = sortedArray.length - 1;
        int mid;

        while (L < R) {
            mid = L + ((R - L) >> 1);

            if (sortedArray[mid] == num) {
                return true;
            }

            if (sortedArray[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }

        return sortedArray[L] == num;
    }
}
