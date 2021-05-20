package sort;

import sort.util.IntArrayUtil;

/**
 * @author xuliangliang
 * @Description 选择排序 O(N ^ 2)
 * @Date 2020/7/12
 */
public class SelectionSort implements IMathArraySort {

    /**
     * 选择排序: 将 array 数组从小到大排序
     * 思路: 找出最小值的索引, 与第一位进行交换.
     */
    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int indexOfMinimum = i;
            for (int j = i + 1; j < array.length; j++) {
                indexOfMinimum = array[j] < array[indexOfMinimum] ? j : indexOfMinimum;
            }
            IntArrayUtil.swap(array, i, indexOfMinimum);
        }
    }




}
