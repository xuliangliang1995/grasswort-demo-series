package sort;

import sort.util.IntArrayUtil;

/**
 * @author xuliangliang
 * @Description 快速排序 O(N * logN)
 * @Date 2020/7/18
 */
public class QuickSort implements IMathArraySort {
    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        int L = 0;
        int R = array.length - 1;
        quickSort(array, L, R);
    }

    /**
     * quick sort
     * @param array
     * @param L
     * @param R
     */
    private void quickSort(int[] array, int L, int R) {
        IntArrayUtil.swap(array, (int)(Math.random() * (R - L + 1)) + L, R);
        int mum = array[R];
        int less = L - 1;
        int more = R + 1;
        int i = L;
        while (i < more) {
            if (array[i] < mum) {
                IntArrayUtil.swap(array, i++, ++less);
            } else if (array[i] > mum) {
                IntArrayUtil.swap(array, i, more--);
            } else {
                IntArrayUtil.swap(array, i++, less + 1);
            }
        }
        if (L < less) {
            quickSort(array, L, less);
        }
        if (more < R) {
            quickSort(array, more, R);
        }
    }
}
