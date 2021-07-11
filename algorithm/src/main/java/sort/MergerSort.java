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
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] arr, int L, int R) {
        if (R == L) {
            return;
        }
        int mid = L + ((R - L) / 2);
        mergeSort(arr, L, mid);
        mergeSort(arr, mid + 1, R);
        int[] arrBak = new int[R - L + 1];
        int l = L;
        int r = mid + 1;
        for (int i = 0; i < arrBak.length; i++) {
            int leftMin = l > mid ? Integer.MAX_VALUE : arr[l];
            int rightMin = r > R ? Integer.MAX_VALUE : arr[r];
            if (leftMin < rightMin) {
                arrBak[i] = leftMin;
                l++;
            } else {
                arrBak[i] = rightMin;
                r++;
            }
        }
        System.arraycopy(arrBak, 0, arr, L, arrBak.length);
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
