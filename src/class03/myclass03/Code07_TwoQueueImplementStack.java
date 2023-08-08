package class03.myclass03;

import java.util.LinkedList;
import java.util.Queue;

public class Code07_TwoQueueImplementStack {
    // 两个队列实现一个栈
    // 思路：栈永远是弹出最上面那个，那么就需要两个队列，存储数据的时候正常进push队列。
    // 出数据的时候，除了倒数第一个数据，其他都搬运到另一个pop队列。这样就能保证出来的数据是最后进的一个。并且弹出数据后pop队列就为空
    // 然后将这两个队列的角色互换。再存储数据的时候进另一个队列。
    // 这样两个队列来回倒


    private Queue<Integer> pushQueue;
    private Queue<Integer> popQueue;

    public Code07_TwoQueueImplementStack() {
        pushQueue = new LinkedList<>();
        popQueue = new LinkedList<>();
    }

    public void push(int value) {
        pushQueue.offer(value);
    }

    public int pop() {
        // 将push队列循环搬运到pop队列，直到剩余最后一个数据
        while (pushQueue.size() > 1) {
            popQueue.offer(pushQueue.poll());
        }
        // 要弹出的数据
        Integer value = pushQueue.poll();
        // 交换两个队列的指针，push队列变成pop队列，pop队列变成push队列
        Queue<Integer> tmp = pushQueue;
        pushQueue = popQueue;
        popQueue = tmp;

        return value;
    }

    public int peek() {
        // 取最顶值实际上不需要弹出，所以搬运之后，弹出之后的值需要再加入push队列,然后交换角色
        while (pushQueue.size() > 1) {
            popQueue.offer(pushQueue.poll());
        }
        // 弹出
        int value = pushQueue.poll();
        // 塞回去
        popQueue.offer(value);
        // 交换角色
        Queue<Integer> temp;
        temp = pushQueue;
        pushQueue = popQueue;
        popQueue = temp;

        return value;
    }

    public boolean isEmpty() {
        return pushQueue.isEmpty();
    }
}
