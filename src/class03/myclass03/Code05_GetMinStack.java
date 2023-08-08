package class03.myclass03;

import java.util.Stack;

public class Code05_GetMinStack {
    public static class MyStack {
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MyStack() {
            this.dataStack = new Stack<>();
            this.minStack = new Stack<>();
        }

        public void push(int value) {
            // 最小栈为空则直接压入数据
            if (minStack.isEmpty()) {
                minStack.push(value);
            } else if (value <= getMinStackPeek()) {
                // 最小栈不为空，如果最小栈栈顶小于等于数据，则压入数据到最小栈
                minStack.push(value);
            }
            // 压入数据到数据栈
            dataStack.push(value);
        }

        public int pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("数据栈为空");
            }
            // 从数据栈弹出数据
            int data = dataStack.pop();
            // 如果与最小栈栈顶数据一致，则说明push的时候同样push进了最小栈，需要从最小栈中一并弹出该栈顶数据
            if (data == getMinStackPeek()) {
                minStack.pop();
            }
            return data;
        }

        public int getMinStackPeek() {
            if (minStack.isEmpty()) {
                throw new RuntimeException("最小栈已空");
            }
            return minStack.peek();
        }
    }
}
