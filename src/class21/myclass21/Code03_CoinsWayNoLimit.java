package class21.myclass21;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 * 从左到右的尝试：
 * 由于每张货币都是无限的，所以每张货币来看，都可以使用0到N张来完成目标钱数，这样给下一个递归的就是剩余钱数
 */
public class Code03_CoinsWayNoLimit {
    public int process(int[] arr, int index, int rest) {
        // baseCase:上一个递归达成了rest==0或者来到arr边界
        // 由于即便是上一个递归达成了rest==0，此仍然可以选择不使用货币，所以不会影响最终结果，所以省略这一baseCase
        // 所以baseCase只有达到边界
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ans = 0;
        // 枚举钱数
        for (int i = 0; i * arr[index] <= rest; i++) {
            ans += process(arr, index + 1, rest - i * arr[index]);
        }
        return ans;
    }

    /**
     * 动态规划数组
     */
    public int process2(int[] arr, int aim) {
        int N = arr.length;
        int[][] ints = new int[N + 1][aim + 1];
        ints[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                for (int k = 0; k * arr[i] <= j; k++) {
                    ints[i][j] += ints[i + 1][j - (k * arr[i])];
                }
            }
        }
        return ints[0][aim];
    }

    /**
     * 优化枚举行为：行为index，rest为列
     * 画出的格子图中可以看到：
     * 同行（同一个货币）中，格子A依赖于其下方及下方往左的一些格子。格子B同样也是，于是格子B就等于格子A+格子B下方的格子。
     * 省略一大堆枚举行为
     */
    public int process3(int[] arr, int aim) {
        int N = arr.length;
        int[][] ints = new int[N + 1][aim + 1];
        ints[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                ints[i][j] = ints[i - 1][0];
                if (j - arr[i] >= 0) {
                    ints[i][j] += ints[i][j - arr[i]];
                }
            }
        }
        return ints[0][aim];
    }
}
