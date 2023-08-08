package class06.myclass062;

public class Code02_Heap {
    public static class myHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public myHeap(int limit) {
            this.arr = new int[limit];
            this.limit = limit;
            this.size = 0;
        }

        public void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("full");
            }
            arr[size] = value;
            heapInsert(arr, size++);
        }

        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public int poll() {
            int value = arr[0];
            swap(arr, 0, --size);
            heapify(arr, 0, size);
            return value;
        }

        private void heapify(int[] arr, int index, int size) {
            int left = index * 2 +1;
            while (left < size) {
                int maxChild = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;

                if (arr[index] > arr[maxChild]) {
                    break;
                }
                swap(arr, index, maxChild);
                index = maxChild;
                left = index * 2 + 1;
            }
        }


    }
}
