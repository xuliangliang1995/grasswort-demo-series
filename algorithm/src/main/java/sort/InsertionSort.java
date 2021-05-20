package sort;

/**
 * @author xuliangliang
 * @Description 插入排序 O(N ^ 2)
 * @Date 2020/7/12
 */
public class InsertionSort implements IMathArraySort {
    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j--];
            }
            array[j + 1] = key;
        }
    }

    /*public void sort2(int[] array) {
        // 0, 1 先保证小区间有序性
        // 0, 2 再逐步扩大区间
        // 0, ... n-1
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && array[j] < array[j - 1] ; j--) {
                IntArrayUtil.swap(array, j, j - 1);
            }
        }
    }*/
}
