package class15.mycalss15;

public class Code02_NumberOfIslandsReflect {
    /**
     * 岛问题（递归解法 + 并查集解法 + 并行解法）
     * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量
     * <p>
     * 递归解法：感染
     * 即遍历二维数组，如果碰到1，岛数量+1，然后上下左右递归把每个点的四个方向直接碰到的1变成2
     */
    // 递归
    public void reflect(char[][] matrix, int i, int j) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length || matrix[i][j] != '1') {
            return;
        }
        // 赋值为ascii为0的一个字符
        matrix[i][j] = 0;
        // 上
        reflect(matrix, i - 1, j);
        // 下
        reflect(matrix, i + 1, j);
        // 左
        reflect(matrix, i, j - 1);
        // 右
        reflect(matrix, i, j + 1);
    }

    public int start(char[][] matrix) {
        int island = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    island++;
                    reflect(matrix, i, j);
                }
            }
        }
        return island;
    }


}
