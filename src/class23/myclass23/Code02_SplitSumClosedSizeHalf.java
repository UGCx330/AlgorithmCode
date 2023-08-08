package class23.myclass23;

/**
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 * 解：从左往右的尝试
 * 递归函数的形参加一个变量num为最多拿的个数。
 * baseCase中，要求num必须为0，这样才能保证两个集合个数一样多。
 * 如果是集合总个数为偶数，num直接=总数/2，总数为奇数时 ，就要分两种情况讨论：
 * ①必须拿总数/2个时，<=sum/2的累加和
 * ②必须拿总数/2+1个时，<=sum/2的累加和
 * ①和②由于已经是<=sum/2了，所以要取max返回
 */
public class Code02_SplitSumClosedSizeHalf {
    public int start(int[] arr) {
        int rest = 0;
        int num = arr.length / 2;
        for (int i : arr) {
            rest += i;
        }
        rest /= 2;
        // 如果长度是偶数，那么二进制中第一位必定是0，奇数第一位必定是1
        if ((arr.length & 1) != 0) {// 奇数
            // 拿总长度/2个数
            int ans1 = process(0, arr, rest, num);
            // 拿总长度/2+1个数
            int ans2 = process(0, arr, rest, num + 1);
            return Math.max(ans1, ans2);
        } else {// 偶数
            return process(0, arr, rest, num);
        }
    }

    public int process(int index, int[] arr, int rest, int num) {
        // baseCASE:来到最后，num==0才保证两个集合数量一致或者差1，否则此路不通
        if (index == arr.length) {
            return num == 0 ? 0 : -1;
        }
        // 不拿
        int ans1 = process(index + 1, arr, rest, num);
        // 拿
        // 因为拿与不拿都可能无效返回-1，所以p2默认值也要是-1
        int ans2 = -1;
        if (arr[index] <= rest) {
            int ans = process(index + 1, arr, rest - arr[index], num - 1);
            if (ans != -1) {// 此路有效
                ans2 = arr[index] + ans;
            }
        }
        return Math.max(ans1, ans2);
    }

    /**
     * 动态规划数组:
     * 可变形参三个:index,rest,num三维数组
     * 纵index ，行rest，列num
     */
    public int process2(int[] arr) {
        int N = arr.length;
        int rest = 0;
        for (int i : arr) {
            rest += i;
        }
        rest /= 2;
        // num范围为一半+1，防止奇数个数情况
        int num = N / 2 + 1;
        int[][][] ints = new int[rest + 1][num + 1][N + 1];
        // 所有baseCase，有效解为0，无效为-1
        for (int k = 0; k <= num; k++) {
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= rest; j++) {
                    ints[i][j][k] = -1;
                }
            }
        }
        // 填充暴力中的baseCase，index=N情况
        for (int i = 0; i <= rest; i++) {
            ints[rest][0][N] = 0;
        }
        // 其他格子，由于每层依赖下一层的数据，纵轴指向是向上的，所以从上层往下层填
        for (int k = N - 1; k >= 0; k--) {
            for (int i = 0; i <= rest; i++) {
                for (int j = 0; j <= num; j++) {
                    // 不拿
                    int ans1 = ints[i][j][k + 1];
                    int ans2 = -1;
                    if (j - 1 >= 0) {
                        // 拿
                        if (arr[k] <= rest) {
                            int ans = ints[i - arr[k]][j - 1][k + 1];
                            if (ans != -1) {
                                ans2 = arr[k] + ans;
                            }
                        }
                    }
                    ints[i][j][k] = Math.max(ans1, ans2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            // 如果是偶数
            return ints[rest][num - 1][0];
        } else {
            // 如果是奇数
            return Math.max(ints[rest][num][0], ints[rest][num - 1][0]);
        }
    }

}
