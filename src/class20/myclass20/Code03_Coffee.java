package class20.myclass20;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 题目
 * 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
 * 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
 * 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
 * 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
 * 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
 * 四个参数：arr, n, a, b
 * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
 * 解：题意为一瞬间大量人涌入，要求分配以达到最短喝上咖啡和洗完被子的时间。
 * 总时间为两部分，第一部分为所有人喝到咖啡的最短时间，第二部分为根据喝完咖啡的时间安排洗完最后一个杯子的最短时间
 */
public class Code03_Coffee {
    // 咖啡机
    public class coffeeMachine {
        // 当前咖啡机多长时间后才可以被使用
        public int nextUseTime;
        // 当前咖啡机冲一杯咖啡所需要的时间
        public int workTime;

        public coffeeMachine(int nextUseTime, int workTime) {
            this.nextUseTime = nextUseTime;
            this.workTime = workTime;
        }
    }

    /**
     * 分析：
     * 每个咖啡机可供人选择时，应该是当前有多少人正在使用+其被使用一次的时间
     * 如1号机器工作时间为1，2号为2，3号为3
     * 现有a，b，c三个人
     * a应该选择1号咖啡机，此时1号咖啡机变成了当前有1个人要使用，且工作时间为1.
     * b此时可以选择1号或者2号，因为1号咖啡机能被使用的时间为1，冲完b的咖啡时间为1，总共为2，而2号咖啡机冲虽然没有人要使用但是完b的咖啡需要的时间也是2
     * 如果b选择了1号，那么1号咖啡机变成了被2个人使用，且工作时间为1.
     * c此时应该选择2号咖啡机，因为没有人使用且工作时间为2，1号被2个人使用且冲完自己的需要1，总共3 。 3号没有被人使用但是冲完自己的也是3.
     * 所以综合123号应该选择2号。
     * 结论：
     * 使用小根堆，按照咖啡机的使用人数*工作时间（记为下一次可用时间nextUseTime）+工作时间（记为workTime）排序
     * 每个咖啡机被使用时，应该更新nextUseTime，重新放入小根堆排序
     * 有多少个人就弹出多少次压回，且弹出的nextUseTime+workTime就是此热喝到咖啡的最短时间
     */
    public class machineComparator implements Comparator<coffeeMachine> {
        @Override
        public int compare(coffeeMachine o1, coffeeMachine o2) {

            return (o1.nextUseTime + o1.workTime) - (o2.workTime + o2.nextUseTime);
        }
    }

    public int start(int[] arr, int n, int a, int b) {
        /**
         * 求出所有人喝到咖啡的最短时间
         */
        // 小根堆,且将所有机器以下一次可用时间为0的姿态入堆
        PriorityQueue<coffeeMachine> heap = new PriorityQueue<>(new machineComparator());
        // 所有人喝到咖啡的最短时间
        int[] allPersionCanDirnkTime = new int[n];
        for (int i : arr) {
            heap.add(new coffeeMachine(0, arr[i]));
        }
        // 按照人数循环弹出压入
        for (int i = 0; i < n; i++) {
            if (!heap.isEmpty()) {
                coffeeMachine coffeeMachine = heap.poll();
                coffeeMachine.nextUseTime += coffeeMachine.workTime;
                allPersionCanDirnkTime[i] = coffeeMachine.nextUseTime;
                heap.add(coffeeMachine);
            }
        }
        // 安排洗杯子
        return process(allPersionCanDirnkTime, a, b, 0, 0);

    }

    // a为机器清洗时间，b为自然挥发时间，index为来到第几个人,washTime为咖啡机可用的时间，返回总体最大喝完时间的最小版本
    // 当前人的选择会对下一个人造成影响，以至于下一个人干净时间甚至比当前人干净时间还早，所以递归中返回的应该是总体最后洗杯子干净的那个人的时间，而不是整体时间加和。
    // 所以此递归中应该是两两相比，递归返回的应该是此人的何种选择对后面人影响后取总体最后洗完时间的最小总体时间
    public int process(int[] allPersionCanDirnkTime, int a, int b, int index, int nextWashTime) {
        // baseCase:来到边界，没有人喝完咖啡也没人需要洗杯子
        if (index == allPersionCanDirnkTime.length) {
            return 0;
        }
        // 假设此人喝完咖啡选择使用机器清洗
        // 那么此人能用上咖啡机的时间可能是自己喝到咖啡的时间(咖啡机可以用，自己还没喝到)或者咖啡机可用的时间(自己喝到了，咖啡机被占用)+a
        // 此人让杯子干净的时间
        int newNextWashTime = Math.max(allPersionCanDirnkTime[index], nextWashTime) + a;
        // 此人选择使用咖啡机后，下一个人的选择(返回的也就是其余所有人的清洗完的最小时间)
        // 由于下一个人可能比此人更快让被子干净，所以必须是总体最大的干净时间
        int time1 = Math.max(process(allPersionCanDirnkTime, a, b, index + 1, newNextWashTime), newNextWashTime);

        // 假设此人喝完咖啡选择自然挥发,对咖啡机的下一次可用时间无影响,下一个递归返回的也就是其余所有人的清洗完的最小时间
        // 由于下一个人可能比此人更快让被子干净，所以必须是总体最大的干净时间
        int time2 = Math.max(allPersionCanDirnkTime[index] + b, process(allPersionCanDirnkTime, a, b, index + 1, nextWashTime));
        // 返回两种情况下的最后一个人洗完杯子最短时间
        return Math.min(time1, time2);
    }

    /**
     * 动态规划数组：
     * 可变形参：index，nextWashTime
     */
    public int process2(int[] allPersionCanDirnkTime, int a, int b) {
        int N = allPersionCanDirnkTime.length;
        // index为行，nextWashTime为列
        // 确定nextWashTime范围--每个人喝到咖啡后都清洗杯子的最坏情况
        int worstNextWashTime = 0;
        for (int i : allPersionCanDirnkTime) {
            // 可能会出现喝完了，机器不闲，此时最坏情况为机器下一次可用时间+a
            // 或者机器闲，没喝完，此时最坏情况为喝完了+a
            worstNextWashTime = Math.max(allPersionCanDirnkTime[i], worstNextWashTime) + a;
        }
        int[][] ints = new int[N + 1][worstNextWashTime + 1];
        // baseCase-最后一行无需填充默认值为0
        // 其余格子
        // 当下一次的清洗时间逼近worstNextWashTime时，再加上a可能越界，即这种可能性也不会存在
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < ints[0].length; j++) {
                int newNextWashTime = Math.max(j, allPersionCanDirnkTime[i]) + a;
                if (newNextWashTime > worstNextWashTime) {
                    break;
                }
                int time1 = Math.max(newNextWashTime, ints[i + 1][newNextWashTime]);
                int time2 = Math.max(allPersionCanDirnkTime[i] + b, ints[i + 1][j]);
                ints[i][j] = Math.min(time1, time2);
            }
        }
        return ints[0][0];
    }


}
