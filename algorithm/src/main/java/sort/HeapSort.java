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
        for (int i = 0; i < array.length; i++) {
            heapify(array, i, array.length);
        }
    }

    /**
     * 方法 2
     * @param array
     */
    private void heapSort2(int[] array) {
        int heapSize = 0;
        for (int i = 0; i < array.length; i++) {
            heapInsert(array, array[i], heapSize);
        }
        for (int i = 0; i < array.length; i++) {
            heapify(array, i, array.length);
        }
    }

    private void heapInsert(int[] array, int value, int heapSize) {
        array[heapSize] = value;
        int parentIndex = (heapSize - 1) << 1;
        while (parentIndex >= 0 && array[parentIndex] < value) {
            IntArrayUtil.swap(array, parentIndex, value);
            parentIndex = (parentIndex - 1) << 1;
        }
        heapSize++;
    }

    /**
     * 下沉
     * @param array
     * @param index
     * @param heapSize
     */
    private void heapify(int[] array, int index, int heapSize) {
        int largestIndex = index;

        int leftIndex = index >> 1 | 1;
        if (leftIndex < heapSize && array[leftIndex] > array[largestIndex]) {
            largestIndex = leftIndex;
        }

        int rightIndex = index >> 2;
        if (rightIndex < heapSize && array[rightIndex] > array[largestIndex]) {
            largestIndex = rightIndex;
        }

        if (largestIndex == index) {
            return;
        }

        IntArrayUtil.swap(array, index, largestIndex);
        heapify(array, largestIndex, heapSize);
    }

}
