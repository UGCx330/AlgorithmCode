package class04.myclass04;

public class Code03_ReversePair {
    // 逆序对----3|21 左组的数，在右组中能找到小于它的数，将这些逆序对个数累加合    3的逆序对为2和1，2的逆序对为1  合就是3个
    // 在一个数组中，任何一个前面的数a，和任何一个后面的数b，如果(a,b)是降序的，就称为降序对
    // 给定一个数组arr，求数组的降序对总数量

    // 核心：仍然是从前往后求结果，即前面的数大于后面的数
    // 可以判断左组的数是否大于右组的数，然后递归到大左组和大右组

    public static int reversePair(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int M = L + ((R - L) >> 1);
        return reversePair(arr, L, M) +
                reversePair(arr, M + 1, R) +
                merge(arr, L, M, R);
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = temp.length - 1;
        int LP = m;
        int RP = r;
        int pair = 0;
        while (LP >= l && RP > m) {
            pair += arr[LP] > arr[RP] ? (RP - m) : 0;//+1是包括RP
            temp[i--] = arr[LP] > arr[RP] ? arr[LP--] : arr[RP--];
        }
        // 越界处理
        while (LP >= l) {
            temp[i--] = arr[LP--];
        }
        while (RP >= l) {
            temp[i--] = arr[RP--];
        }
        // 覆盖原数组
        for (int j = 0; j < temp.length; j++) {
            arr[l + j] = temp[j];
        }
        return pair;
    }


}
