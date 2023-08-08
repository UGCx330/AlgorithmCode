package class19.myclass19;

/**
 * 背包问题
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
 * 给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
 * 返回能装下的最大价值
 * 从左到右的递归：
 * 每个递归两种分支情况，即装此物品，不装此物品,向上层返回max
 */
public class Code01_Knapsack {
    public int start1(int[] weights, int[] values, int bag) {
        return process1(weights, values, bag, 0);
    }

    public int process1(int[] weights, int[] values, int bag, int index) {
        // baseCase：背包满或者到最后
        if (bag < 0) {
            // 说明上一层递归装完物品其重量超出背包，所以要使得上层递归失效，返回-1
            return -1;
        }
        // 背包没满，到了最后，此次递归返回0，没有物品可拿
        if (index == weights.length) {
            return 0;
        }
        // 背包容量不为负数，且没到最后，则直接分为拿和不拿两种情况
        int ans1 = process1(weights, values, bag, index + 1);
        int ans2 = 0;
        int next = process1(weights, values, bag - weights[index], index + 1);
        if (next != -1) {
            // 拿完背包不为负数才拿不然当前拿了也不算
            ans2 = values[index] + next;
        }
        return Math.max(ans1, ans2);
    }

    public int start2(int[] weights, int[] values, int bag) {
        return process2(weights, values, bag);
    }

    // 动态规划数组,每个递归的返回值只与index和bag有关
    public int process2(int[] weights, int[] values, int bag) {
        int N = weights.length;
        // bag作为列，index作为行
        int[][] ints = new int[N + 1][bag + 1];
        // 填充baseCase,index==length全为0即最后一行全为0,由于默认值就是0不需要填充
        // 填充其他位置,每个格子与下方某2个格子有关，直接从倒数第二行往上从左到右填即可
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= bag; j++) {
                int ans1 = ints[i + 1][j];
                int ans2 = 0;
                int next = j - weights[i] < 0 ? -1 : values[i] + ints[i + 1][j - weights[i]];
                if (next != -1) {
                    ans2 = values[i] + next;
                }
                ints[i][j] = Math.max(ans1, ans2);
            }
        }
        return ints[0][bag];
    }


}
