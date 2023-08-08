package class06.myclass06;

public class Code03_HeapSort {
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 堆排序--先变成大根堆，然后0位置数与最后一个数交换，size-1，0位置数下沉重新生成大根堆，循环至只剩下一个数的大根堆。此时数组就排好序了。
     * 一次性给所有数，组成一个数组，变成大根堆
     * 未给定所有数一个数一个数往上加--NlogN--多数数上爬logN高度，给定所有数，方法时间复杂度为O(N)--少数数下沉logN高度
     * 一个个数追加的上浮法是保证从上往下分支逐渐减小。   给定所有数的下沉法保证从下往上分支逐渐增大
     */
    public void heapSort(int[] arr) {
        // 生成大根堆
        for (int i = arr.length - 1; i >= 0; i--) {
            // 从底部每个节点开始下沉
            heapify(arr, i, arr.length);
        }
        // 交换，size-1
        int size = arr.length;
        swap(arr, 0, --size);
        // 0位置下沉重新生成大根堆
        while (size > 0) {
            heapify(arr, 0, size);
            swap(arr, 0, --size);
        }
    }

    public void heapify(int[] arr, int index, int size) {
        int left = index * 2 + 1;
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
