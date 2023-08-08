package class21.myclass21;

/**
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 * 反转，目标点往左，往上走
 */
public class Code01_MinPathSum {
    public int start(int[][] matrix) {
        return process(matrix, matrix.length - 1, matrix[0].length - 1);
    }

    public int process(int[][] matrix, int x, int y) {
        if (x < 0 || y < 0) {
            return Integer.MAX_VALUE;
        }
        if (x == 0 && y == 0) {
            return matrix[0][0];
        }
        return matrix[x][y] + Math.min(process(matrix, x, y - 1), process(matrix, x - 1, y));
    }

    public int process2(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] ints = new int[row][col];
        ints[0][0] = matrix[0][0];
        for (int j = 1; j < col; j++) {
            ints[0][j] = ints[0][j - 1] + matrix[0][j];
        }
        for (int i = 1; i < row; i++) {
            ints[i][0] = ints[i - 1][0] + matrix[i][0];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                ints[i][j] = matrix[i][j] + Math.min(ints[i - 1][j], ints[i][j - 1]);
            }
        }
        return ints[row - 1][col - 1];
    }

    /**
     * 空间压缩：
     * 一个一维数组代替二维数组
     */
    public int process3(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[] ints = new int[col];
        ints[0] = matrix[0][0];
        // 第0行
        for (int i = 1; i < col; i++) {
            ints[i] = matrix[0][i] + ints[i-1];
        }
        // 其他行
        for (int i = 1; i < row; i++) {
            ints[0] = matrix[i][0] + ints[0];
            for (int j = 1; j < col; j++) {
                ints[j] = Math.min(ints[j - 1], ints[j]) + matrix[i][j];
            }
        }
        return ints[col - 1];
    }
}
