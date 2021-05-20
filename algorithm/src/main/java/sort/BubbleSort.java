package sort;

import sort.util.IntArrayUtil;

/**
 * @author xuliangliang
 * @Description 冒泡排序 O(N ^ 2)
 * @Date 2020/7/12
 */
public class BubbleSort implements IMathArraySort {
    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        for (int i = array.length - 1; i > 0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    IntArrayUtil.swap(array, j, j + 1);
                }
            }
        }
    }
}
