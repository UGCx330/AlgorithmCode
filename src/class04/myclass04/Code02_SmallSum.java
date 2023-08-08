package class04.myclass04;

public class Code02_SmallSum {
    // 最小合--1|23：左组中的1右组中两个数比自己大，所以最小合此时num=1*2.即一个数A右边有多x个数比自己大，最小合=A*x
    // merge指针动之前，同步--左数小于右数则将左数累加至一个数，一次merger返回一个当前merge的最小合数
    // 核心：每次merger行为左组数会检查右组数比自己大的个数。同时右组数已经是有序单调的。可以容易得出此刻左组某个数对于右组的最小合。
    // 即--根据题意，只需要计算每次左组对于右组的最小合即可。由于递归，会主键将小的右组和左组合并为一个大左组再与大右组求最小合（大右组也是分小左右组取最小合之后）
    // 递归之后所有数从左到右，都会涉及到自己的最小合

    public static int smallSum(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int M = L + ((R - L) >> 1);
        return smallSum(arr, L, M) +
                smallSum(arr, M + 1, R) +
                merge(arr, L, M, R);
    }

    //merge左右两组已经分别是排好序的,即左右已经是单调性，merge只是利用两边单调性将左右合并成整体并排序
    public static int merge(int[] arr, int L, int M, int R) {
        int LP = L;// 左指针
        int RP = M + 1;// 右指针
        int smallSum = 0;// 当前merge的最小合
        int[] temp = new int[R - L + 1];
        int i = 0;
        while (LP <= M && RP <= R) {
            // 求左组某数的相对于右组的最小合
            smallSum += arr[LP] < arr[RP] ? (R - RP + 1) * arr[LP] : 0;
            // 排序
            temp[i++] = arr[LP] < arr[RP] ? arr[LP++] : arr[RP++];
        }

        // 越界处理
        while (LP <= M) {
            temp[i++] = arr[LP++];
        }
        while (RP <= R) {
            temp[i++] = arr[RP++];
        }

        // temp数组覆盖原数组
        for (int j = 0; j < temp.length; j++) {
            arr[L + j] = temp[j];
        }

        return smallSum;
    }


}
