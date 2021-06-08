package fibonacci;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/8
 */
public class FibonacciProblem {
    /**
     * 获取斐波拉契队列第 n 个位置的数
     */

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            int position = i;
            int num = fibonacci(position);
            System.out.printf("斐波那契第 %d 个数为 %d\n", position, num);
        }
    }

    private static int fibonacci(int position) {
        int a1 = 1;
        int a2 = 1;
        if (position == 1) {
            return a1;
        }
        if (position == 2) {
            return a2;
        }
        int[][] baseMatrix = {
                {1, 1},
                {1, 0}
        };
        int[][] result = pow(baseMatrix, position - 2);
        return result[0][0] + result[1][0];
    }


    private static int[][] pow(int[][] matrix, int power) {
        int[][] cursor = null;
        int[][] result = {
                {1, 0},
                {0, 1}
        };
        while (power != 0) {
            cursor = cursor == null ? matrix : multi(cursor, cursor);
            if ((power & 1) == 1) {
                result = multi(result, cursor);
            }
            power = power >> 1;
        }
        return result;
    }

    private static int[][] multi(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[2][2];
        result[0][0] = matrix1[0][0] * matrix2[0][0]
                + matrix1[0][1] * matrix2[1][0];
        result[0][1] = matrix1[0][0] * matrix2[0][1]
                + matrix1[0][1] * matrix2[1][1];
        result[1][0] = matrix1[1][0] * matrix2[0][0]
                + matrix1[1][1] * matrix2[1][0];
        result[1][1] = matrix1[1][0] * matrix2[0][1]
                + matrix1[1][1] * matrix2[1][1];
        return result;
    }


}
