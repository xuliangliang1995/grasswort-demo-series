package codetop;

/**
 * @author 🌺xuliangliang🌺
 * @Description 查询数组中第 K 大的值(快排改动版)
 * @Date 2021/8/12
 */
public class FindKthLargest {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println(findKthLargest(arr, 2));
    }

    public static int findKthLargest(int[] nums, int k) {
        int index = nums.length - k;
        quickSortPlus(nums, 0, nums.length - 1, index);
        return nums[index];
    }

    private static void quickSortPlus(int[] arr, int L, int R, int k) {
        if (R <= L) {
            return;
        }

        int key = arr[R];
        int lessPointer = L - 1;
        int greaterPointer = R + 1;

        for (int i = L; i < greaterPointer; i++) {
            int val = arr[i];
            if (val < key) {
                swap(arr, i, ++lessPointer);
            } else if (val > key) {
                swap(arr, i--, --greaterPointer);
            }
        }

        if (k <= lessPointer) {
            quickSortPlus(arr, L, lessPointer, k);
        }
        if (k >= greaterPointer) {
            quickSortPlus(arr, greaterPointer, R, k);
        }
    }

    public static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
