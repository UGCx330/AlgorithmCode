package class03.myclass03;

public class Code03_DoubleEndsQueueToStackAndQueue {
    public static class DoubleNode<T> {
        public T value;
        public DoubleNode<T> last;
        public DoubleNode<T> next;

        public DoubleNode(T value) {
            this.value = value;
        }
    }

    public static class DoubleDuanQueue<T> {
        public DoubleNode<T> head;
        public DoubleNode<T> tail;

        public void addFromHead(T value) {
            // 放在首部的新节点
            DoubleNode<T> current = new DoubleNode<>(value);
            if (head == null) {
                head = current;
                tail = current;
            } else {
                // 头部加入的节点只需要修改新加节点的下一个节点指向为原本头节点就行。
                current.next = head;
                // 修改原头节点的上一个节点为新加入的节点
                head.last = current;
                // 移动头节点指针为新加入的节点
                head = current;
            }
        }

        public void addFromEnd(T value) {
            DoubleNode<T> current = new DoubleNode<>(value);
            if (head == null) {
                head = current;
                tail = current;
            } else {
                // 尾部加入的节点只需要修改新加入的节点的上一个节点指向为原本尾节点就行
                current.last = tail;
                // 原本尾节点的下一个节点为新加入的节点
                tail.next = current;
                // 移动尾节点指针为新加入的节点
                tail = current;
            }
        }

        public T popFromHead() {
            if (head == null) {
                return null;
            }
            // 暂时将头节点的地址保留一份用于修改要弹出的节点以及返回弹出节点的数据
            DoubleNode<T> popNode = head;
            if (head == tail) {// 最后一个数据被弹出
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.last = null;
                popNode.next = null;
            }
            return popNode.value;
        }

        public T popFromTail() {
            if (head == null) {
                return null;
            }
            DoubleNode<T> popNode = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                popNode.last = null;
            }
            return popNode.value;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    // 双端队列实现栈--从头进从头出
    public static class MyStack<T> {
        private class03.Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new class03.Code03_DoubleEndsQueueToStackAndQueue.DoubleEndsQueue<T>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T pop() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // 双端队列实现队列--从头进从尾出
    public static class MyQueue<T> {
        private DoubleDuanQueue<T> queue;

        public MyQueue() {
            queue = new DoubleDuanQueue<T>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T poll() {
            return queue.popFromTail();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

}
