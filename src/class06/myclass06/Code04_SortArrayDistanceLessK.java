package class06.myclass06;

import java.util.PriorityQueue;

public class Code04_SortArrayDistanceLessK {
    /**
     * 给定一组数，排序。
     * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k
     * k相对于数组长度来说是比较小的。请选择一个合适的排序策略，对这个数组进行排序
     * 解：使用5个长度的小根堆，先将0-4位置上的数放入堆中，既然不超过5个距离，那么最小的数肯定在0-4中
     * 将0-4按照小根堆弹出最小值后放在新数组的0位置。
     * 同理1位置的数肯定在原数组的0-5上，小根堆再push进原数组5位置的数，然后弹出最小值放在1上。
     * 逐步将原位置的数push进小根堆，然后弹出至新数组中
     * 核心就是新数组的x位置，在原数组中可能出现的位置为x+5，所以必须将x+5囊括进来
     */

    public static void sortArrayDistanceLessK(int[] arr, int k) {
        // 系统提供的小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // push进堆的位置
        int index = 0;
        // 0到k-1下标放入小根堆
        for (; index <= Math.min(arr.length - 1, k - 1); index++) {
            heap.add(arr[index]);
        }
        int i = 0;// 弹出覆盖原数组的位置，每次弹出都会重新排小根堆
        for (; index < arr.length; index++, i++) {
            // push一个弹一个
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }
        // 数组最后k个数，弹出
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }
}
