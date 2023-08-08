package class22.myclass22;

/**
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 * 解：
 * 每个递归中应该是将某个数裂开为两个数，并且裂开的前一个数应该参照上一个递归给出的数，不能比这个数小
 * 即递归形参为参照数+要裂开的数
 * 要分割6，则先传入1参照数(认为规定)，6作为rest
 * 第一次递归可以将6分为1，5，然后1作为参照数，5作为rest传入下一个递归
 * 第二次递归可以将5分为2，3，然后2作为参照数，3作为rest传入下一个递归
 * 第三次递归3只能分为2，1或者3，0传入下一个递归
 * 第四次递归如果传入的是2，1那么剩余的1小于2，违反后一个数不能比前一个数小的规定，理应此路不通，返回0，所以baseCase中rest<pre就是返回0
 * 第四次递归如果传入的是3,0，那么说明到头了，所以只要rest=0，那就说明此路走到头了，可通，返回1
 * 对于任意一个递归来说，只要rest=0，就是能走到最后的正确的路。
 */
public class Code03_SplitNumber {
    public int start(int n) {
        // 假设第一次的参照数是1，不比1小即可
        return process(1, n);
    }

    // i为分割的参照数
    public int process(int pre, int rest) {
        // baseCase:rest==0，说明上一次分割正好是即符合上一次条件的分割，也是最后一次分割
        if (rest == 0) {
            return 1;
        }
        // rest不为0的情况下，参考数大于rest，视为不符合规定
        if (pre > rest) {
            return 0;
        }
        // 剩余不为0继续分割
        int ans = 0;
        for (int j = pre; j <= rest; j++) {
            ans += process(j, rest - j);
        }
        return ans;
    }

    /**
     * 动态规划数组:
     * 可变形参：pre和rest,且范围都在0到要分割的数以内
     */
    // n为要分割的数
    public int process2(int n) {
        // rest为列，前置为行,且0行不用
        int[][] ints = new int[n + 1][n + 1];
        // baseCase:
        for (int i = 0; i <= n; i++) {
            ints[i][0] = 1;
        }
        // 对角线以下的不填
        // 依赖的格子前缀逐渐增大，剩余逐渐减少，也就是左下位置，且前缀+1，rest-1，也就是左下的一条斜线类似于：/
        // 所以从下往上，从左往右填
        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {
                int ans = 0;
                for (int k = i; k <= j; k++) {
                    ans += ints[k][j - k];
                }
                ints[i][j] = ans;
            }
        }
        return ints[1][n];
    }

    /**
     * 动态规划优化枚举行为：
     * 每个格子A pre，rest依赖于左下的一条斜线  /，斜线开始位置为A的pre，rest-pre位置，然后往左下的斜线
     * 因为pre和rest在递归中都是每次以1作为单位变化的，所以尝试看一下A左边和下面的格子
     * A左边格子：pre一样，rest较小，那么斜线开始位置pre，rest-pre--，与A的相比，更往左,那么斜线与A的不重合
     * A下面格子：前缀增大，rest不变，那么斜线开始位置pre++,rest-pre--，与A的相比，更往左下,有可能跟A的斜线重合，即A的斜线的一部分
     * 然后举例，确实是重合。
     * 即格子A=格子A的下面+格子A的左边斜线开始的第一个位置的格子
     * <p>
     * 同时，从暴力递归中可以看出，对角线上的值跟0列的值一一对应。i=j的时候，即可看出
     */
    public int process3(int n) {
        //0行不用
        int[][] ints = new int[n + 1][n + 1];
        // 第0列和对角线一起填1
        for (int i = 0; i <= n; i++) {
            ints[i][0] = 1;
            ints[i][i] = 1;
        }
        // 仍旧是对角线左下不用填,从下往上，从左往右
        for (int i = n - 1; i >= 1; i++) {
            for (int j = i + 1; j <= n; j++) {
                // 对角线往上j-i一定>=0
                ints[i][j] = ints[i][i - j] + ints[i + 1][j];
            }
        }
        return ints[1][n];
    }
}

