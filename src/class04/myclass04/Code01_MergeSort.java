package class04.myclass04;

public class Code01_MergeSort {
    // 递归实现排序
    public static void mergeSortByRecursion(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);

    }

    // 大范围拆分成左右小范围，，循环往复，直到具体某个数返回。左子树深度优先。
    public static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int M = L + ((R - L) >> 1);
        process(arr, L, M);
        process(arr, M + 1, R);
        mergeSort(arr, L, M, R);
    }

    //----------------------------------------
    // 广度优先，迭代实现排序,迭代不需要每次变化左右边界，不是递归而是自下而上的循环,跟递归一样，上一次循环的信息下一次循环用得上
    // 核心边界为：①某次步长下能不能构成左组并且有右组②右组数量不够步长
    // 核心数学：merge的左右两个数组都是具有单调性的
    public static void iteration(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长，步长为1则两个数分两组比较，步长为2则4个数分两组比较，步长为4则8个数分两组比较，步长=步长<<1
        int mergeSize = 1;
        while (mergeSize < N) {// 先保证外层大循环的顺利进行,这个条件里面再具体判断
            int L = 0;
            while (L < N) {// 某个步长下的从头到尾的排序
                // 某次步长下最后剩余的数不能满足左组数量=步长的要求或者无右组，直接结束，进入下一个步长
                // 比如9个数比较，步长为1，剩余1个数，步长为2，剩余1个数，步长为4，剩余一个数，步长为8，可以作为右组
                if (mergeSize >= N - L) {
                    break;
                }
                // 每次比较的中点
                int M = L + mergeSize - 1;
                // 可能剩余的数可以组成左组和右组，但是右组数量不够步长，但是也可以与左组组合在一起比较排序的，所以取N-M+1作为右侧指针
                int R = M + Math.min(mergeSize, N - M -1);
                mergeSort(arr, L, M, R);

                // 下一次比较
                L = R + 1;
            }

            // 如果当前的步长已经超过总长度一半了，下一次步长绝对大于总长度，就没必要继续，同时防止总长度*2会溢出int类型
            if (mergeSize > N / 2) {
                break;
            }

            // 步长*2
            mergeSize <<= 1;
        }
    }

    //-------------------------------------------
    // merge比较
    public static void mergeSort(int[] arr, int L, int M, int R) {
        int[] tempArr = new int[R - L + 1];
        int PL = L;
        int PR = M + 1;
        int i = 0;
        // 左右指针不越界，PL不超过M，PR不超过R
        while (L <= M && PR <= R) {
            tempArr[i++] = arr[PL] > arr[PR] ? arr[PR++] : arr[PL++];
        }
        // PR越界,处理剩下的PL
        while (PL <= M) {
            tempArr[i++] = arr[PL++];
        }
        // PL越界,处理剩下的PR
        while (PR <= R) {
            tempArr[i++] = arr[PR++];
        }
        // 临时数组排序好的值覆盖掉原数组
        for (i = 0; i < tempArr.length; i++) {
            arr[L + i] = tempArr[i];
        }
    }


}
