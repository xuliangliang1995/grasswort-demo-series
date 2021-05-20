package sort.util;

/**
 * @author xuliangliang
 * @Description 数组工具类
 * @Date 2020/7/12
 */
public class IntArrayUtil {

    /**
     * 随机数组
     * @param size
     * @param minValue
     * @param maxValue
     * @return
     */
    public static int[] randomArray(int size, int minValue, int maxValue) {
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * (maxValue - minValue + 1)) + minValue;
        }
        return array;
    }

    /**
     * 数组拷贝
     * @param array
     * @return
     */
    public static int[] copyArray(int[] array) {
        if (array == null) {
            return null;
        }
        int[] duplicate = new int[array.length];
        for (int i = 0; i < array.length ; i++) {
            duplicate[i] = array[i];
        }
        return duplicate;
    }

    /**
     * 数组比较
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * 打印数组
     * @param arr
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * 交换数组两个不同位置的数(没有检测索引 i j 是否越界)
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        if (i == j) {
            return;
        }
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}
