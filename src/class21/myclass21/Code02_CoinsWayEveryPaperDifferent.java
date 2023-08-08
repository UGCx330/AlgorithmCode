package class21.myclass21;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 * 从左往右的尝试
 * 每个货币有两种选择，拿或者不拿
 */
public class Code02_CoinsWayEveryPaperDifferent {
    public int process(int[] arr, int index, int aim) {
        // baseCase：中途就算使得aim正好为0，下面的递归也是可以选择不使用货币从而走到arr边界
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        if (aim < 0) {
            return 0;
        }
        int ans1 = process(arr, index + 1, aim);
        int ans2 = process(arr, index + 1, aim - arr[index]);
        return ans1 + ans2;
    }

    public int process2(int[] arr, int aim) {
        int N = arr.length;
        int[][] ints = new int[N + 1][aim + 1];
        ints[N][0] = 1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                ints[i][j] = ints[i + 1][j];
                if (j - arr[i] >= 0) {
                    ints[i][j] += ints[i + 1][j - arr[i]];
                }
            }
        }
        return ints[0][aim];
    }
}
