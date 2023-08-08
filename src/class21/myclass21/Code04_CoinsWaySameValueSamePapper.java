package class21.myclass21;

import java.util.HashMap;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 * 解：从左往右的尝试
 * 值相同，货币相同，则可以去重，一个数组用来代表货币的面值，一个数组用来代表该面值的货币数量
 */
public class Code04_CoinsWaySameValueSamePapper {
    public int start(int[] arr, int aim) {
        // 转化
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            if (!map.containsKey(i)) {
                map.put(i, 1);
            } else {
                map.put(i, map.get(i) + 1);
            }
        }

        int[] arr1 = new int[map.size()];
        int[] nums = new int[map.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 切忌重复i++
            arr1[i] = entry.getKey();
            nums[i++] = entry.getValue();
        }
        return process(arr1, nums, 0, aim);
    }

    public int process(int[] arr, int[] nums, int index, int aim) {
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        int ans = 0;
        // 不能超过总张数，并且不能超过目标金额
        for (int i = 0; i <= nums[index] && i * arr[index] <= aim; i++) {
            ans += process(arr, nums, index + 1, aim - i * arr[index]);
        }
        return ans;
    }

    /**
     * 动态规划
     */
    public int process2(int[] arr, int[] nums, int aim) {
        int N = arr.length;
        int[][] ints = new int[N + 1][aim + 1];
        ints[N][0] = 1;
        for (int i = N - 1; i >= 0; i++) {
            for (int j = 0; j <= aim; j++) {
                for (int k = 0; k <= nums[i] && k * arr[i] <= j; k++) {
                    ints[i][j] += ints[i + 1][j - k * arr[i]];
                }
            }
        }
        return ints[0][aim];
    }

    /**
     * 动态规划去除枚举行为优化：
     * 从图中可以得知，格子A依赖于其下方和下方左边的一些格子。格子A的左边是格子B，格子B依赖于其下方和下方左边的一些格子。
     * 那么格子A是否可以=格子B+格子A下方格子？
     * 需要注意：如果在张数足够多的情况下，成立。
     * 但是如果对于格子B的rest来说，张数不够，也就是全部张数用完了，格子B的rest仍有剩余：
     * 格子A=下方+下方左边两个格子一个3个格子。
     * 格子B=下方+下方左边两个格子一个3个格子。(因为张数不够，rest仍有剩余，所以不会越界)
     * 那么格子A=格子B+格子A下方格子，就会让格子A多出一个格子B的下方的最左边那个格子的值。
     * 所以需要判断：
     * 如果对于格子B，张数用完了，仍有rest，那么格子A=格子B+格子A下方格子-格子B下方最左的格子
     * 如果对于格子B，张数用不完，rest就<=0了，那么格子A=格子B+格子A下方格子
     */
    public int process3(int[] arr, int[] nums, int aim) {
        int N = arr.length;
        int[][] ints = new int[N + 1][aim + 1];
        ints[N][0] = 1;
        for (int i = N - 1; i >= 0; i++) {
            for (int j = 0; j <= aim; j++) {
                // 总是依赖于下方格子，先赋值下方格子
                ints[i][j] = ints[i + 1][j];
                // 左边格子不越界,先直接加上左边格子，再判断该不该减
                if (j - arr[i] >= 0) {
                    ints[i][j] = ints[i][j - arr[i]];
                }
                // 该不该减
                if (j - nums[i] * (arr[i] + 1) >= 0) {// 如果左边格子张数用完了rest仍有剩余，该减
                    ints[i][j] -= ints[i + 1][j - nums[i] * (arr[i] + 1)];
                }
            }
        }
        return ints[0][aim];
    }
}
