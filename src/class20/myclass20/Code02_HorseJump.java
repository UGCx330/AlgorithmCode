package class20.myclass20;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 * 解：
 * 马走日，所以每个点一共有八种走向，即递归中8中情况分析
 */
public class Code02_HorseJump {
    public int start1(int x, int y, int k) {
        return process(x, y, 0, 0, k);
    }

    public int process(int x, int y, int index1, int index2, int k) {
        // baseCase:
        if (k == 0) {
            if (index1 == x && index2 == y) {
                return 1;
            }
            return 0;
        }
        int way = process(x, y, index1 + 1, index2 + 2, k - 1);
        way += process(x, y, index1 + 2, index2 + 1, k - 1);
        way += process(x, y, index1 + 2, index2 - 1, k - 1);
        way += process(x, y, index1 + 1, index2 - 2, k - 1);
        way += process(x, y, index1 - 1, index2 - 2, k - 1);
        way += process(x, y, index1 - 2, index2 - 1, k - 1);
        way += process(x, y, index1 - 2, index2 + 1, k - 1);
        way += process(x, y, index1 - 1, index2 + 2, k - 1);
        return way;
    }

    /**
     * 动态规划数组：
     * 可变形参三个，三维数组，长宽高
     * 每一层的某个点只依赖于下一层的某8个点
     */
    public int process2(int x, int y, int k) {
        int[][][] ints = new int[9][10][k + 1];
        int N = ints.length;
        // baseCase第一层
        ints[x][y][0] = 1;
        // 其他层
        for (int k1 = 1; k1 < k + 1; k1++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    // 可能出现越界问题，使用额外方法处理
                    int way = border(ints, i + 1, j + 2, k1 - 1);
                    way += border(ints, i + 2, j + 1, k1 - 1);
                    way += border(ints, i + 2, j - 1, k1 - 1);
                    way += border(ints, i + 1, j - 2, k1 - 1);
                    way += border(ints, i - 1, j - 2, k1 - 1);
                    way += border(ints, i - 2, j - 1, k1 - 1);
                    way += border(ints, i - 2, j + 1, k1 - 1);
                    way += border(ints, i - 1, j + 2, k1 - 1);
                    ints[i][j][k] = way;
                }
            }
        }
        return ints[0][0][k];

    }

    public int border(int[][][] ints, int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return ints[x][y][k];
    }
}
