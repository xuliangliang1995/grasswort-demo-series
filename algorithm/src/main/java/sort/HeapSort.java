package sort;

import sort.util.IntArrayUtil;

/**
 * @author xuliangliang
 * @Description 堆排序 O(N* logN)
 * @Date 2020/7/18
 */
public class HeapSort implements IMathArraySort {
    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        for (int i = array.length - 1; i >= 0 ; i--) {
            heapify(array, i, array.length);
        }
        int heapSize = array.length;
        while (heapSize > 1) {
            IntArrayUtil.swap(array, 0, heapSize - 1);
            heapify(array, 0, --heapSize);
        }
    }

    /**
     * 下沉
     * @param array
     * @param index
     * @param heapSize
     */
    private void heapify(int[] array, int index, int heapSize) {
        int largestIndex = index;

        int leftIndex = index * 2 + 1;
        if (leftIndex < heapSize && array[leftIndex] > array[largestIndex]) {
            largestIndex = leftIndex;
        }

        int rightIndex = index * 2 + 2;
        if (rightIndex < heapSize && array[rightIndex] > array[largestIndex]) {
            largestIndex = rightIndex;
        }

        if (largestIndex == index) {
            return;
        }

        IntArrayUtil.swap(array, index, largestIndex);
        heapify(array, largestIndex, heapSize);
    }

    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        int[] arr = {10, 7, 9, 0, 2, 6, 5, 11, 1};
        sort.sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

}
