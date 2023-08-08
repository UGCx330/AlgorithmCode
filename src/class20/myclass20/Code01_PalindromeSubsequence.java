package class20.myclass20;

/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 * 解：
 * 一个字符串的最长回文子序列即为该字符串与该字符串的逆序的最长公共子序列
 * 因为只要不是回文子序列，逆序之后，其公共长度最大也就是1即字符本身。只有回文字符序列逆序之后公共长度与原本一致。
 * 解法①：样本对应模型：将字符串逆序，两个字符串字符数组作为形参
 * 解法②：范围模型：
 * 回文特点从前往后和从后往前相同，假定回文子序列以L和R为开头和结尾，可分为下面四种情况：
 * ①以L开头，不以R结尾
 * ②不以L开头，以R结尾
 * ③不以L开头，不以R结尾
 * ④以L开头，不以R结尾
 * 即每个递归寻找回文子序列时以L...R为范围有四种可能性
 */
public class Code01_PalindromeSubsequence {
    public int start(String str) {
        return process(str.toCharArray(), 0, str.length() - 1);
    }

    public int process(char[] chars, int L, int R) {
        // baseCase:L==R，回文子序列最长长度也就是本身，返回1
        if (L == R) {
            return 1;
        }
        // L和R相邻，如果相等回文长度为2，否则为1
        if (L == R - 1) {
            if (chars[L] == chars[R]) {
                return 2;
            }
            return 1;
        }
        // 以L开头，不以R结尾
        int ans1 = process(chars, L, R - 1);
        // 不以L开头，以R结尾
        int ans2 = process(chars, L + 1, R);
        // 不以L开头，不以R结尾
        int ans3 = process(chars, L + 1, R - 1);
        // 以L开头，以R结尾
        int ans4 = chars[L] == chars[R] ? (2 + process(chars, L + 1, R - 1)) : 0;
        return Math.max(Math.max(ans1, ans2), Math.max(ans3, ans4));
    }

    /**
     * 动态规划数组：
     * 行数：L
     * 列数：R
     * L最大为0到length-1
     * R最大为0到length-1
     * 且L<=R，下三角区域不用填充
     */
    public int process2(char[] chars) {
        int N = chars.length;
        int[][] ints = new int[N][N];
        // 填充baseCase
        // 先把最后一个格子填了
        ints[N - 1][N - 1] = 1;
        // 然后两个baseCase一起
        for (int i = 0; i < N - 1; i++) {
            ints[i][i] = 1;
            ints[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1;
        }
        // 其他格子：
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                // 左边格子
                int ans1 = ints[L][R - 1];
                // 下面格子
                int ans2 = ints[L + 1][R];
                // 左下格子
                int ans3 = ints[L + 1][R - 1];
                int ans4 = chars[L] == chars[R] ? (2 + process(chars, L + 1, R - 1)) : 0;
                ints[L][R] = Math.max(Math.max(ans1, ans2), Math.max(ans3, ans4));
            }
        }
        return ints[0][N - 1];
    }

    /**
     * 动态规划数组进一步优化：
     * 对于某个格子A来说，左边格子为B，左下为C，下面为D
     * 根据每个格子的Math.max
     * A一定不小于C，B一定不小于C，D一定不小于C
     * 所以填值时不用考虑左下格子，因为其左边和下面格子都不小于左下格子的值。
     */
    public int process3(char[] chars) {
        int N = chars.length;
        int[][] ints = new int[N][N];
        // 填充baseCase
        // 先把最后一个格子填了
        ints[N - 1][N - 1] = 1;
        // 然后两个baseCase一起
        for (int i = 0; i < N - 1; i++) {
            ints[i][i] = 1;
            ints[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1;
        }
        // 其他格子：
        for (int L = 0; L < N - 3; L++) {
            for (int R = L + 2; R < N; R++) {
                ints[L][R] = Math.max(ints[L][R - 1], ints[L + 1][R]);
                if (chars[L] == chars[R]) {
                    ints[L][R] = Math.max(ints[L][R], 2 + ints[L + 1][R - 1]);
                }
            }
        }
        return ints[0][N - 1];
    }
}
