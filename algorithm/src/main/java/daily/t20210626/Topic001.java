package daily.t20210626;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/26
 */
public class Topic001 {

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 1, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 0, 1, 1}
        };
        System.out.println(maxSquareEdgeLength(matrix));
    }


    /**
     * 求一个正方形矩阵中 以 1 为边围成的最大正方形的边长
     * @param squareMatrix
     * @return
     */
    private static int maxSquareEdgeLength(int[][] squareMatrix) {
        if (squareMatrix == null || squareMatrix.length == 0) {
            return 0;
        }
        int edge = squareMatrix.length;
        // expandMap[a][b] = [上扩极限, 右阔极限]
        int[][][] expandMap = new int[edge][edge][2];

        int ans = 0;

        for (int i = 0; i < squareMatrix.length; i++) {
            int[] level = squareMatrix[i];
            for (int j = level.length - 1; j >= 0; j--) {
                if (level[j] == 1) {
                    int upExpand = 1 + (i - 1 >= 0 ? expandMap[i - 1][j][0] : 0);
                    int rightExpand = 1 + (j + 1 < edge ? expandMap[i][j + 1][1] : 0);
                    expandMap[i][j][0] = upExpand;
                    expandMap[i][j][1] = rightExpand;

                    int edgeLen = Math.min(upExpand, rightExpand);
                    while (edgeLen > 1) {
                        if (expandMap[i - edgeLen + 1][j][1] >= edgeLen
                                && expandMap[i][j + edgeLen - 1][0] >= edgeLen) {
                            break;
                        }
                        edgeLen--;
                    }
                    ans = Math.max(ans, edgeLen);
                }
            }
        }

        return ans;
    }
}
