package class23.myclass23;

/**
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。
 * n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 * 解：皇后问题没有重复解，也就是单纯递归。因为每一步是由上面所有行的皇后位置和自己行当前位置逻辑判断，这个条件是一直在变且不重复的。
 */
public class Code03_NQueens {
    public int start(int n) {
        // 记录每一行皇后位置，下标即为行
        int[] ints = new int[n];
        return process1(0, ints, n);
    }

    public int process1(int index, int[] ints, int n) {
        // 如果放完所有行，此路通
        if (index == n) {
            return 1;
        }
        int ans = 0;
        // 当前行尝试每个位置
        for (int i = 1; i <= n; i++) {
            // 与ints[]中每个行的皇后位置都不同列，不同斜线
            if (buChongTu(i, index, ints)) {
                // 当前行选择的位置与前面所有皇后不冲突，记录自己位置，到下一行
                ints[index] = i;
                ans += process1(index + 1, ints, n);
            }
        }
        return ans;
    }

    public boolean buChongTu(int i, int index, int[] ints) {
        for (int j = 0; j < index; j++) {
            if (ints[j] == i || Math.abs(j - index) == Math.abs(ints[j] - i)) {
                // 如果与前面任意一个皇后有冲突，停止比较，选择本行下一个位置再比较(外层for)
                return false;
            }
        }
        return true;
    }

    /**
     * N皇后问题位优化：
     * 将记录位置比较是否冲突的数组改为3个int的32位，减少常数级别的比较时间
     */
    // n皇后
    // lie为前面所有皇后影响下列上可以放皇后的位置(可以放的位为0)
    // leftDown为前面所有皇后的影响下左下可以放的位置(可以放的位为0)
    // rightDown为前面所有皇后的影响下右下可以放的位置(可以放的位为0)
    // 即leftDown和rightDown和lie都转变为下一行不可放皇后的列位置
    public static int process2(int n, int lie, int leftDown, int rightDown) {
        // 如果所有列都放了皇后，说明此路通
        if (lie == (1 << n) - 1) {
            return 1;
        }
        int ans = 0;
        // 考虑本行皇后的位置
        for (int i = 0; i < n; i++) {
            // 尝试皇后位置
            int h = 1 << i;
            // 冲突检查,如果&完lie为0，说明此位置在列上不冲突,如果&完leftDown为0，说明此位置在左下不冲突,如果&完rightDown为0，说明此位置在右下不冲突
            // lie，leftDown，rightDown中为1的位置说明此位置对于h来说都不能放。所以h的1&这三个如果为0，说明此位置没有那三个限制的1，可以放皇后
            if ((h & ~(lie | leftDown | rightDown)) != 0) {
                // 更新lie，leftDown，rightDown的限制位置，然后进入下一个递归
                // lie的放皇后的位置置为1
                int lie1 = lie | h;
                // leftDown左边增加一个1，并且对于下一行的皇后来说，还需要整体左移一位
                int leftDown1 = (h | leftDown) << 1;
                // rightDown右边增加一个1，并且对于下一行的皇后来说，还需要整体右移一位
                int rightDown1 = (h | rightDown) >>> 1;
                ans += process2(n, lie1, leftDown1, rightDown1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(process2(13, 0, 0, 0));
    }


}
