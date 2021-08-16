package codetop;

/**
 * @author 🌺xuliangliang🌺
 * @Description 岛屿问题 https://leetcode-cn.com/problems/number-of-islands/
 * @Date 2021/8/16
 */
public class NumIslands {

    public static void main(String[] args) {
        char[] row1 = {'1', '1', '1', '1', '0'};
        char[] row2 = {'1', '1', '0', '1', '0'};
        char[] row3 = {'1', '1', '0', '0', '0'};
        char[] row4 = {'0', '0', '0', '1', '0'};
        char[][] grid = {row1, row2, row3, row4};
        System.out.println(new NumIslands().numIslands(grid));
    }

    public int numIslands(char[][] grid) {
        int xLimit = grid.length - 1;
        int yLimit = grid[0].length - 1;
        int counter = 0;
        for (int x = 0; x <= xLimit; x++)  {
            for (int y = 0; y <= yLimit; y++) {
                char val = grid[x][y];
                if (val == '1') {
                    counter++;
                    ext(grid, x, y);
                }
            }
        }
        return counter;
    }

    private void ext(char[][] grid, int x, int y) {
        int xLimit = grid.length - 1;
        int yLimit = grid[0].length - 1;
        if (x >= 0 && x <= xLimit && y >= 0 && y<= yLimit) {
            char val = grid[x][y];
            boolean continueExt = val == '1';
            if (continueExt) {
                grid[x][y] = '2';
                ext(grid, x - 1, y);
                ext(grid, x + 1, y);
                ext(grid, x, y - 1);
                ext(grid, x, y + 1);
            }
        }
    }
}
