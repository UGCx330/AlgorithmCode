package class06.myclass062;

public class Code03_HeapSort {
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void heapify(int[] arr, int index, int size) {
        int left = index * 2 - 1;
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

    public void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int size = arr.length;
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, size);
        }
        swap(arr, 0, --size);
        while (size > 0) {
            heapify(arr, 0, size);
            swap(arr, 0, --size);
        }
    }
}
