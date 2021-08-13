package daily.t20210809;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/8/12
 */
public class FindKthLargest {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println(findKthLargest(arr, 2));
    }

    public static int findKthLargest(int[] nums, int k) {
        int index = nums.length - k;
        partitionSort(nums, 0, nums.length - 1, index);
        return nums[index];
    }

    public static void partitionSort(int[] nums, int l, int r, int k) {
        int key = nums[r];
        int lessPointer = l - 1;
        int greatPointer = r + 1;
        for (int i = l; i < greatPointer; i++) {
            int cursorVal = nums[i];
            if (cursorVal < key) {
                lessPointer++;
                swap(nums, i, lessPointer);
            } else if (cursorVal > key) {
                greatPointer--;
                swap(nums, i, greatPointer);
                i--;
            }
        }
        if (k >= greatPointer) {
            partitionSort(nums, greatPointer, r, k);
        } else if (k > lessPointer) {
            return;
        } else if (k <= lessPointer) {
            partitionSort(nums, l, lessPointer, k);
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
