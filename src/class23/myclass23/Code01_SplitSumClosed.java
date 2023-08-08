package class23.myclass23;

/**
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 * 解：从左往右的尝试，返回最接近集合一半的累加和
 * 递归中每个数可以选择拿/不拿。但是要保证当前值<=rest，rest即为集合一半-前面已经拿的值。
 * 返回拿/不拿取max即可。
 * 这样返回的累加和一定<=集合的一半，也就是最接近的前提下，较小的累加和
 */
public class Code01_SplitSumClosed {
    public int start(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        sum = sum / 2;
        return process(0, arr, sum);
    }

    public int process(int index, int[] arr, int rest) {
        // baseCase:来到最后
        if (index == arr.length) {
            return 0;
        }
        // 不拿
        int ans1 = process(index + 1, arr, rest);
        // 拿
        int ans2 = 0;
        if (arr[index] <= rest) {
            ans2 = arr[index] + process(index + 1, arr, rest - arr[index]);
        }
        return Math.max(ans1, ans2);
    }

    /**
     * 动态规划数组
     * 可变形参index和rest
     * index行，rest列
     */
    public int process1(int[] arr) {
        int rest = 0;
        for (int i : arr) {
            rest += i;
        }
        rest = rest / 2;
        int N = arr.length;
        int[][] ints = new int[N + 1][rest + 1];
        // baseCase默认填充好
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= rest; j++) {
                // 不拿
                int ans1 = ints[i + 1][j];
                // 拿
                int ans2 = 0;
                if (arr[i] <= rest) {
                    ans2 = arr[i] + ints[i + 1][j - arr[i]];
                }
                ints[i][j] = Math.max(ans1, ans2);
            }
        }
        return ints[0][rest];
    }

}
