package daily.t20210627;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/27
 */
public class Topic003 {


    public static void main(String[] args) {
        int[][] arr = {
                {1, 0, 5},
                {3, 2, 1},
                {1, 2, 3},
                {1, 0, 1}
        };
        for (int[] ints : arr) {
            System.out.println(minMove(ints));
        }
    }

    /**
     * 最小移动次数
     * @param arr
     * @return
     */
    private static int minMove(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int avg = avg(arr);
        if (avg == -1) {
            return -1;
        }
        return needMove(arr, avg, 0, 0);
    }



    /**
     *
     * @param arr 正数数组
     * @return -1 表示不能平均
     */
    private static int avg(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int j : arr) {
            sum += j;
        }
        if (sum % arr.length > 0) {
            return -1;
        }
        return sum / arr.length;
    }


    private static int needMove(int[] arr, int avg, int cursor, int need) {
        if (cursor >= arr.length) {
            return 0;
        }
        int value = arr[cursor];
        int move = avg + need - value;
        return Math.max(Math.abs(move), needMove(arr, avg, cursor + 1, move));
    }
}
