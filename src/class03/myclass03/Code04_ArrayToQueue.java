package class03.myclass03;

public class Code04_ArrayToQueue {
    public static class MyQueue {
        private int[] arr;
        private int push;
        private int pop;
        private int size;
        private final int limit;

        public MyQueue(int limit) {
            arr = new int[limit];
            push = 0;
            pop = 0;
            size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("队列已满");
            }
            // 已用容量+1
            size++;
            // 由于容量限制，pop永远不会超越push
            arr[push] = value;
            push = nextIndex(push);
        }

        public int pop() {
            int value = 0;
            if (size == 0) {
                throw new RuntimeException("队列已空");
            }
            size--;
            value = arr[pop];
            pop = nextIndex(pop);
            return value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int nextIndex(int i) {
            return i < limit - 1 ? i++ : 0;
        }
    }

}
