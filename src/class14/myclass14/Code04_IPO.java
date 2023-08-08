package class14.myclass14;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_IPO {
    /**
     * 输入正数数组costs、正数数组profits、正数K和正数M
     * <p>
     * costs[i]表示i号项目的花费
     * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
     * K表示你只能串行的最多做k个项目
     * M表示你初始的资金
     * 说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目。
     * 输出：最后获得的最大钱数
     * <p>
     * 升级打怪问题：
     * 先将所有项目放入小根堆(按照项目最小启动资金排序)中，开始弹出，只要自己的资金可以启动的项目，压入大根堆(按照项目利润排序)。
     * 此时大根堆中最顶上的就是自己可以启动的且利润最大的项目
     * 弹出大根堆中第一个项目，变更启动资金。就相当于做了一次项目。
     * 此时再从小根堆看看能否弹出项目到大根堆，直到当前启动资金不能启动。
     * 循环往复
     */
    public class Program {
        public int startMoney;
        public int back;

        public Program(int startMoney, int back) {
            this.startMoney = startMoney;
            this.back = back;
        }
    }

    public class startComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.startMoney - o2.startMoney;
        }
    }

    public class backComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.back - o1.back;
        }
    }

    public int IPO(int[] costs, int[] profits, int K, int M) {
        PriorityQueue<Program> startQueue = new PriorityQueue<>(new startComparator());
        PriorityQueue<Program> backQueue = new PriorityQueue<>(new backComparator());
        for (int i = 0; i < costs.length; i++) {
            startQueue.add(new Program(costs[i], profits[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!startQueue.isEmpty() && startQueue.peek().startMoney <= M) {
                backQueue.add(startQueue.poll());
            }
            // 如果大根堆没有项目，直接结束整个方法。
            // 如资金不够任意项目启动，或者资金即便启动了力所能及的项目，但是还是不足以启动下一个项目等情况
            if (backQueue.isEmpty()) {
                return M;
            }
            // 此时大根堆第一个就是可以做的项目,然后将本金+利润累加回去
            M += backQueue.poll().back;
            // 然后按照新的M启动资金进行下一个循环
        }
        return M;
    }
}
