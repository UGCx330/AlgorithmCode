package class14.myclass14;

import java.util.PriorityQueue;

public class Code02_LessMoneySplitGold {
    /**
     * 一块金条切成两半，是需要花费和长度数值一样的铜板
     * 比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板?
     * 例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
     * 如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
     * 但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
     * <p>
     * 输入一个数组，返回分割的最小代价
     * <p>
     * 准备一个小根堆，将数组中所有数压入，然后每次弹出两个数，加和后再压入小根堆，再弹出两个，再加和压入，循环往复直到小根堆为空
     * 每次弹出后加和的数，记录下来，再加和就是整体最小代价，也是整体分割法
     */

    public int lessMoneySplitGold(int[] gold) {
        if (gold.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < gold.length; i++) {
            queue.add(gold[i]);
        }
        int sum = 0;
        int add = 0;
        while (queue.size() > 1) {// 为1就是金条总长度，不需要累加到sum
            add = queue.poll() + queue.poll();
            sum += add;
            queue.add(add);
        }
        return sum;
    }

    // 返回的是两两加和,方法形参为两两加和，然后除去，传给下一个递归的数组，两两相加后的和
    public int violence(int[] arr, int pre) {
        // baseCase为一条路径到头，即两两相加一条路，此时应该返回所有两两的和
        if (arr.length == 1) {
            return pre;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                min = Math.min(min, violence(merge(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        // 递归返回值应为取所有两两之和的最小的一个
        return min;
    }

    // i和j位置合并
    public int[] merge(int[] arr, int i, int j) {
        arr[i] += arr[j];
        int k = 0;
        int[] ints = new int[arr.length - 1];
        for (int i1 = 0; i1 < arr.length; i1++) {
            if (i1 != j) {
                ints[k++] = arr[i1];
            }
        }
        return ints;
    }

}
