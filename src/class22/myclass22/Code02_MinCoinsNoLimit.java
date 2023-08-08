package class22.myclass22;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 * 解：从左到右尝试
 * 每个面值都可以从0到<=aim
 * return 当前面值用的张数+下个面值递归用的张数
 * 如果最后一张面值用完之后，不能正好使得rest=0，说明此路径不通，return Integer.max
 * 于是这条路上都会失效
 */
public class Code02_MinCoinsNoLimit {
    public int start(int[] arr, int aim) {
        return process(arr, aim, 0);
    }

    public int process(int[] arr, int rest, int index) {
        // 走到最后，rest=0，此时正常返回0，不需要任何面值
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        // 剪枝,没走到最后，但是此时rest=0，当前以及后续所有面值都选择0张
        if (rest == 0) {
            return 0;
        }
        // rest有剩余，面值没用到边界
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i * arr[index] <= rest; i++) {
            int ans2 = process(arr, rest - i * arr[index], index + 1);
            if (ans2 != Integer.MAX_VALUE) {
                ans = Math.min(ans2 + i, ans);
            }
        }
        return ans;
    }

    /**
     * 动态规划数组
     * 可变形参：index，rest
     */
    public int process2(int[] arr, int rest) {
        int[][] ints = new int[arr.length + 1][rest + 1];
        int row = arr.length + 1;
        int col = rest + 1;
        for (int i = 1; i < col; i++) {
            ints[row - 1][i] = Integer.MAX_VALUE;
        }
        for (int i = row - 2; i >= 0; i--) {
            for (int j = 0; j < col; j++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k * arr[i] <= j; k++) {
                    int ans2 = ints[i + 1][j - k * arr[i]];
                    if (ans2 != Integer.MAX_VALUE) {
                        ans = Math.min(ans2 + i, ans);
                    }
                }
                ints[i][j] = ans;
            }
        }
        return ints[0][rest];
    }

    /**
     * 动态规划优化枚举行为：
     * 格子A=格子A下方及下方左边到rest=0截至的一些格子的最小值
     * 格子A(rest-用一张)左边为格子B，格子A=Math.min(格子B+1，格子A下方格子)
     * 格子B+1的原因：
     * 假设格子C为格子A和B共同依赖的下左方的格子，那么对于格子A来说，依赖的是格子C+用的张数，对于格子B也是格子C+用的张数
     * 而这个张数，格子A是比格子B多用一张的。所以要想使用格子B与格子A下方的取最小值，必须格子B+1
     * ps：如果左边格子是Integer.max，那么就不用比较了,如果硬要比较会造成溢出问题
     * 即原本的所有条件都要对应一下看有无遗漏
     */
    public int process3(int[] arr, int rest) {
        int[][] ints = new int[arr.length + 1][rest + 1];
        int row = arr.length + 1;
        int col = rest + 1;
        for (int i = 1; i < col; i++) {
            ints[row - 1][i] = Integer.MAX_VALUE;
        }
        for (int i = row - 2; i >= 0; i--) {
            for (int j = 0; j < col; j++) {
                ints[i][j] = ints[i + 1][j];
                if (j - arr[i] >= 0 && ints[i][j - arr[i]] != Integer.MAX_VALUE) {
                    //这里if中的判断条件就对应与暴力中的ans2！=Integer.MAX_VALUE，只有递归结果！=Integer.MAX_VALUE
                    //才将其+张数进行min比较
                    ints[i][j] = Math.min(ints[i][j], ints[i][j - arr[i]] + 1);
                }
            }
        }
        return ints[0][rest];
    }


}
