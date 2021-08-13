package daily.t20210809;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/8/12
 */
public class Test002 {
    // 11 20 4000 52100 9

    public static void main(String[] args) {
        int[] arr = {11, 20, 4000, 52100, 9};
        insertionSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            for (int j = i - 1; j >= 0 ; j--) {
                int lastKey = arr[j];
                boolean lastKeyLessThanKey = getHighPosition(lastKey) < getHighPosition(key);
                if (lastKeyLessThanKey) {
                    swap(arr, j, j + 1);
                } else {
                    break;
                }
            }
        }
    }

    public static int getHighPosition(int a) {
        return Integer.valueOf(String.valueOf(a).toCharArray()[0] + "");
    }

    public static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
