package class03.myclass03;

import java.util.Stack;

public class Code06_TwoStacksImplementQueue {
    // 实现：一个栈入数据--push栈，一个栈出数据--pop栈
    // 添加至push时，检查pop是否为空，    不为空则不进行搬运，继续添加至push。为空则将push中所有数据搬运至pop中
    // 从pop弹出时，每次弹出后，检查pop是否为空，  不为空则不进行搬运，继续添加至push。为空则将push中所有数据搬运至pop中

    // 只要pop栈不为空，就不要往从push栈弹出数据到pop栈中。并且每次从push搬运数据到pop时，必须把数据搬运干净，即push栈中搬运直到空
    // 如果pop栈有数据，那么往push栈添加的时候就一直加。不会往pop栈搬运。
    // 这样防止情况为：
    //    添加123到push，搬运123到pop，弹出3，pop：21   push：null   ，再添加45，搬运45到pop，pop：5421，下一个弹出的时5就不是2了
    // 修正情况：
    //    添加123到push，搬运123到pop，弹出3，pop：21   push：null   ，再添加45，pop不为空，不搬运，pop：21，下一个弹出还是2。pop：1  push：45  ，
    //    再添加67 push：6745   pop不为空  pop：1  ， 弹出一个数据 pop不为空不搬运  弹出后 pop ：空   ，再弹出一个数据，pop为空  搬运6745到pop  pop  5476

    // 总结：弹出/入栈，都要检查pop是否为空，pop为空就搬运push到pop直到push为空，pop不为空就正常弹出/入栈
    // 只要保证一次性搬运所有数，就保证了每波数据的队列的形式。先进先出。
    private Stack<Integer> pushStack;
    private Stack<Integer> popStack;

    public Code06_TwoStacksImplementQueue() {
        pushStack = new Stack<>();
        popStack = new Stack<>();
    }

    // 搬运
    public void move() {
        if (popStack.empty()) {// 前提必须pop为空
            while (!pushStack.empty()) {// 搬运至push为空
                popStack.push(pushStack.pop());// push弹出pop入栈
            }
        }
    }

    public void push(int value) {
        // 先入push栈，然后看是否能搬运
        pushStack.push(value);
        move();
    }

    public int pop() {
        if (popStack.empty() && pushStack.empty()) {
            throw new RuntimeException("栈空，请先加入数据");
        }
        // 先看是否能搬运
        move();
        // 弹出
        return popStack.pop();
    }

    public int peek() {
        if (popStack.empty() && pushStack.empty()) {
            throw new RuntimeException("栈空，请先加入数据");
        }
        move();
        return popStack.peek();
    }

}
