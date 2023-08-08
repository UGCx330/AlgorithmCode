package class07.myclass07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_CoverMax {
    /**
     * 给定很多线段，每个线段都有两个数[start, end]，
     * 表示线段开始位置和结束位置，左右都是闭区间
     * 规定：
     * 1）线段的开始和结束位置一定都是整数值
     * 2）线段重合区域的长度必须>=1
     * 返回线段最多重合区域中，包含了几条线段
     * <p>
     * 题解：
     * 所谓线段重合，重合部分的头，一定是线段的头部，重合部分的尾巴，一定是线段的尾巴。
     * 所以原问题转化为--对于每个线段的头来说，如果小于先前的线段的尾巴，就是有重合。
     * 如(1，5) 和(3，8)----3小于5，所以这两条线段有重合，如果此时下一条线段的头部小于5和8，如(4，9)，那么(4，9)与前面的两条线段都有重合部分
     * 所以得出结论，只要新的线段头部小于先前的的线段的尾部，那么就与先前所有线段都有重合。
     * <p>
     * 思路：
     * ①将所有线段先按照头的大小排好序
     * ②准备一个小根堆
     * ③依次将线段放入小根堆 ：
     * 将第一条线段的尾部放入小根堆。记为尾1。记录此时重合线段数为1，他自己。
     * 第二条线段的头部判断是否小于小根堆中的尾1，小于说明有重合部分，将尾2放入小根堆。记录此时重合线段数为2。
     * 第三条线段的头部判断是否小于小根堆中的尾1和尾2，都小于说明这三条线段有重合部分，将尾3放入小根堆。记录此时重合线段数为2。
     * 第三条线段的头部判断是否小于小根堆中的尾1和尾2和尾3，如果大于尾1小于尾2尾3，(说明第四条线段的头部超出了第一条线段的尾部，与第一条线段不重合)。
     * 由于后面进来的线段头部是递增的，所以后面的也不会与线段1重合，从堆中弹出尾1，放入尾4。记录此时重合线段数为2。
     * 弹出堆时，会重新排序小根堆。所以只要线段头部大于堆顶尾部，弹出后会自动将第二小的尾部排到顶部，再比较，直到头部<某个堆中最小的尾部，说明头部<堆中所有尾部。就不会再弹出，说明此头部的线段与堆中所有尾部的线段有重合部分。
     * 以此类推......直到最后一个线段入堆
     * <p>
     * 核心：
     * 因为线段头单调递增，所以对于堆中的线段尾部，如果后续某个线段头y来说超过了某个尾部x，那么以后的线段头也会超过尾部x，图像意义来说就是尾部x与线段y拉开了距离不再交错。所以就需要将尾部x弹出。
     * <p>
     * 一句话总结：
     * 每比较一个线段头，记录一次堆中尾部数量，堆中能剩余的尾部就都是与头交错的，也即有一个共同重合部分的。也相当于新进来的线段与以前的以堆中尾部为尾部的线段重合了这么多次。
     * 即堆中记录线段的尾部，新进来的线段头部<堆中哪几个尾部，就与几个以前的线段有重合,如果新进来的线段头部>某几个尾部，说明与这几个线段没有重合，以后新进来的线段也不会与他们有重合(线段头单调递增进来的)，记录完数量，就从堆中弹出这几个尾部即可。
     * <p>
     * 最差代价：每次堆不弹出，N个数全部入小根堆上浮，NlogN   最好代价：下一个线段头>上一个线段尾，这样每次弹出一个，入堆一个，代价就是O(N)
     */

    public class Line {
        private Integer head;
        private Integer end;

        public Line(Integer head, Integer end) {
            this.head = head;
            this.end = end;
        }
    }

    public class LineCompare implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.head - o2.head;
        }
    }

    public int coverMax(int[][] initLines) {
        // 初始化线段数组
        Line[] lines = new Line[initLines.length];
        for (int i = 0; i < initLines.length; i++) {
            lines[i] = new Line(initLines[i][0], initLines[i][1]);
        }
        Arrays.sort(lines, new LineCompare());
        // 重合数量
        int size = 0;
        // 小根堆存储线段尾巴
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 入堆
        for (int i = 1; i < lines.length; i++) {
            while (!heap.isEmpty() && lines[i].head >= heap.peek()) {
                heap.poll();
            }
            heap.add(lines[i].end);
            size = Math.max(size, heap.size());
        }
        return size;
    }

}
