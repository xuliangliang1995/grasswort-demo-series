package sort;

/**
 * @author xuliangliang
 * @Description 归并排序 O(N * logN)
 * @Date 2020/7/15
 */
public class MergerSort implements IMathArraySort {


    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        int mergeSize = 1;
        while (true) {
            int i = 0;
            while (i < array.length) {
                merge(array, i, Math.min(i - 1 + (mergeSize * 2), array.length - 1));
                i += mergeSize << 1;
            }
            if (mergeSize >= (array.length + 1) / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }

    private void merge(int[] array, int i, int j) {
        // 0 , 1, 2, 3, 4
        int mid = (j - i) / 2 + i;
        int L = i;
        int R = mid + 1;
        int k = 0;
        int[] sortedArray = new int[j - i + 1];
        while (L <= mid && R <= j) {
            if (array[L] < array[R]) {
                sortedArray[k++] = array[L++];
            } else {
                sortedArray[k++] = array[R++];
            }
        }
        while (L <= mid) {
            sortedArray[k++] = array[L++];
        }
        while (R <= j) {
            sortedArray[k++] = array[R++];
        }
        for (int l = 0; l < sortedArray.length; l++) {
            array[i + l] = sortedArray[l];
        }
    }

}
