package sort;

import sort.util.IntArrayUtil;

import java.util.Random;

/**
 * @author xuliangliang
 * @Description 快速排序 O(N * logN)
 * @Date 2020/7/18
 */
public class QuickSort implements IMathArraySort {


    private Random random = new Random();
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
     * @param arr
     * @param L
     * @param R
     */
    private void quickSort(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        IntArrayUtil.swap(arr, R, random.nextInt(R - L + 1) + L);
        int number = arr[R];
        int lessCursor = L - 1;
        int rightCursor = R + 1;
        int i = L;
        while (i < rightCursor) {
            if (arr[i] < number) {
                IntArrayUtil.swap(arr, i, ++lessCursor);
            } else if (arr[i] > number) {
                IntArrayUtil.swap(arr, i, --rightCursor);
                i--;
            }
            i++;
        }
        quickSort(arr, L, lessCursor);
        quickSort(arr, rightCursor, R);
    }
}
