package class06.myclass06;

public class Code02_Heap {
    /**
     * 大根堆演示(每个子树的最大值是其根节点).
     * 堆，可以用数组来表示，每个节点i的父节点位置为(i-1)/2,左子节点位置为2i+1,右子节点位置为2i+2。
     * 所以虽然是数组，但是可以根据位置，想象成树的结构。
     */
    public static class myBigRootHeap {
        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        private int[] arr;
        // 控制数组的长度
        private final int limit;
        // 控制堆的长度<=数组长度
        private int size;

        public myBigRootHeap(int limit) {
            this.arr = new int[limit];
            this.limit = limit;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size <= 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        /**
         * * 堆的插入:在数组最后插入时，需要重新对该数进行循环上浮，直到不大于父节点为止。
         */
        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("堆已满");
            }
            // 先添加到最后位置
            arr[size] = value;
            // 然后上浮
            heapInsert(arr, size++);
        }

        // 上浮
        public void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {// 比父节点小或者来到顶点(父节点==本身)停
                swap(arr, index, (index - 1) / 2);// 否则比父节点大，交换
                index = (index - 1) / 2;// 父节点赋值给index
            }
        }

        /**
         * 堆最大值的弹出：0位置即为大根堆的最大值，弹出之前记录值，然后不要破坏堆结构，让0位置与最后一个数做交换，新的0位置的数再循环下沉，直到不小于左右子节点为止。
         */
        public int pop(int[] arr) {
            if (size == 0) {
                throw new RuntimeException("堆已空");
            }
            int value = arr[0];
            // 交换，并将size-1，弃用原根节点
            swap(arr, 0, --size);
            //下沉
            heapify(arr, 0, size);
            return value;
        }


        // 下沉
        public void heapify(int[] arr, int index, int size) {
            int left = 2 * index + 1;
            while (left < size) {
                // 右孩子存在?且左右孩子取较大位置
                int maxChild = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                // 孩子与index父节点比较
                maxChild = arr[index] < arr[maxChild] ? maxChild : index;
                // 父节点仍然是最大，结束
                if (maxChild == index) {
                    break;
                }
                // 下沉
                swap(arr, index, maxChild);
                index = maxChild;
                // 更新下一次循环的左孩子位置
                left = index * 2 + 1;
            }
        }


    }


}
