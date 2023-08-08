package class04.myclass04;

public class Code04_BiggerThanRightTwice {
    /**
     * 在一个数组中，对于任何一个数num，求有多少个(后面的数*2)依然<num，返回总个数
     * 比如：[3,1,7,0,2]
     * 3的后面有：1，0
     * 1的后面有：0
     * 7的后面有：0，2
     * 0的后面没有
     * 2的后面没有
     * 所以总共有5个
     */

    // 依旧是左组对右组比较，上升到大左组对大右组比较
    public int biggerThanRightTwice(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return biggerThanRightTwice(arr, l, m) +
                biggerThanRightTwice(arr, m + 1, r) +
                merge(arr, l, m, r);
    }

    public int merge(int[] arr, int l, int m, int r) {
        // 前面的排序，最小合，逆序对问题，都是左组中数大于/小于右组中数即可，所以可以与merge操作一并完成，但是判断>2*num并不适用。
        // 因为左右的merge指针只会在大于小于条件下移动，没有倍数要求，也不能有倍数要求，否则破坏merge排序。
        // 比如左数最后一个9大于右数最后一个8，merge行为此时将9放入临时数组，但是2倍判断的话9>2*8才可以将9放入临时数组，
        // 要么放入8，破坏merge排序行为，要么不放，移动右指针直到>2*右数为止，这时右指针过后还需要回退！不符合merge不回退行为。
        // 解决方法：与merge操作分开，单独一个for循环，左组从头开始遍历并与右组(windowR指针)中数从头开始比较，使用一个WindowR指针在右组记录左组某数>右组某数*2的最右位置。个数=m-windowR，记录之后左指针继续移动即可。
        // 由于左组有序，左组后面的数WindowR的位置肯定大于左组前面的数的WindowR位置，所以下一次的次数记录可以建立在上一次的记录之上。仍旧是m-windowR
        // 所以windowR指针可以实现一直往右走不会退，左组循环也是一直往右走不回退

        // 求值--不进行merge，不做改动
        int twiceSum = 0;
        int windowR = m + 1;
        for (int i = l; i <= m; i++) {// 左组单个数最大windowR位置
            while (windowR <= r && arr[i] > (arr[windowR] * 2)) {
                // 只有不越界，且左数>右数*2，windowR指针++，循环往复直到不满足条件，此时windowR-m-1即为左数>右数*2的最大数量
                // 并且下一个arr[i]一定大于此时windowR的位置的右数*2，因为左组是有序的
                windowR++;
            }
            // 每次左组中数都加一遍
            twiceSum += windowR - m - 1;// 多出来的++减去
        }

        // 正常merge排序
        int LP = l;
        int RP = m + 1;
        int[] temp = new int[r - l + 1];
        int i = 0;
        while (LP <= m && RP <= r) {
            temp[i++] = arr[LP] <= arr[RP] ? arr[LP++] : arr[RP++];
        }
        // 越界处理
        while (LP <= m) {
            temp[i++] = arr[LP++];
        }
        while (RP <= r) {
            temp[i++] = arr[RP++];
        }

        // 覆盖原数组
        for (int j = 0; j < temp.length; j++) {
            arr[l + j] = temp[j];
        }

        return twiceSum;
    }

}
