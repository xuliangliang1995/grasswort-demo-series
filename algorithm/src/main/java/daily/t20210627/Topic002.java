package daily.t20210627;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/27
 */
public class Topic002 {

    public static void main(String[] args) {
        int[][] arr = {
                {1, 4, 6, 10},
                {2, 7, 8, 20},
                {4, 9, 10, 50}
        };
        System.out.println(contains(arr, 0, 0, 8));
        System.out.println(contains(arr, 0, 0, 9));
        System.out.println(contains(arr, 0, 0, 5));
    }


    /**
     *
     * @param arr
     * @param value
     * @return
     */
    private static boolean contains(int[][] arr, int x, int y, int value) {
        int cursor = arr[x][y];
        if (cursor == value) {
            return true;
        }
        if (cursor > value) {
            return false;
        }
        boolean ans = false;
        if (x + 1 < arr.length) {
            ans = contains(arr, x + 1, y, value);
        }
        if (! ans && y + 1 < arr[x].length) {
            ans = contains(arr, x, y + 1, value);
        }
        return ans;
    }
}
