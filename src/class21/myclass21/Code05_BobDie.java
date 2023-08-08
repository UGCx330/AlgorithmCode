package class21.myclass21;

/**
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 * 解：
 * 对于走k步，每一步四个方向，那么一共有4^k种走法
 * 对于k步之后，只要不越界，就视为路径+1，递归结果/4^k就是概率
 */
public class Code05_BobDie {
    public double start(int N, int M, int x, int y, int k) {
        return (double) process(N - 1, M - 1, x, y, k) / Math.pow(4, k);
    }

    public int process(int n, int m, int x, int y, int k) {
        // 如果此时越界，直接剪枝返回0
        if (x < 0 && x > n && y < 0 && y > m) {
            return 0;
        }
        // baseCase：步数用完，仍在区域内，路径有效
        if (k == 0) {
            return 1;
        }
        int ans1 = process(n, m, x, y - 1, k - 1);
        int ans2 = process(n, m, x, y + 1, k - 1);
        int ans3 = process(n, m, x + 1, y, k - 1);
        int ans4 = process(n, m, x - 1, y, k - 1);
        return ans1 + ans2 + ans3 + ans4;
    }

    public int process2(int n, int m, int x, int y, int k) {
        int[][][] ints = new int[n + 1][m + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                ints[i][j][0] = 1;
            }
        }
        for (int k1 = 1; k1 <= k; k1++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= m; j++) {
                    // 越界的baseCase既然不需要体现在格子种，就要体现在逻辑中用到这些越界的baseCase时，要返回0
                    ints[i][j][k1] = ifExists(i, j - 1, k1, n, m, ints);
                    ints[i][j][k1] += ifExists(i, j + 1, k1, n, m, ints);
                    ints[i][j][k1] += ifExists(i - 1, j, k1, n, m, ints);
                    ints[i][j][k1] += ifExists(i + 1, j, k1, n, m, ints);
                }
            }
        }
        return ints[x][y][k];
    }

    public int ifExists(int x, int y, int k, int n, int m, int[][][] ints) {
        if (x < 0 && x > n && y < 0 && y > m) {
            return 0;
        }
        return ints[x][y][k];
    }
}
