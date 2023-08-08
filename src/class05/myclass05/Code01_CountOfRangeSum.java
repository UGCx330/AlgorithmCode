package class05.myclass05;

public class Code01_CountOfRangeSum {
    /**
     * 给定一个数组arr，两个整数lower和upper，
     * 返回arr中有多少个子数组的累加和在[lower,upper]范围上--子数组是连续的
     */

    // 凡是子数组，一定是以数组中某个数为结尾。
    // 子数组累加和大致分为两种：①从0加到x ②从y加到x
    // ①的表示：将所有数从0累加到x写成一个数组即可。叫做前缀数组
    // ②的表示：从0到x的累加和减去从0到y的累加和
    // 递归行为：每个数x的从y到x的子数组，可以作为右组，其左组就是y。我们直接使用前缀数组代替原数组。
    // 以下的arr实际上是原数组的前缀数组
    public int countOfRangeSum(int[] arr, int l, int r, int lower, int upper) {
        if (l == r) {
            return arr[l] >= lower && arr[l] <= upper ? 1 : 0;
        }
        int m = l + ((r - l) >> 1);
        return countOfRangeSum(arr, l, m, lower, upper) +
                countOfRangeSum(arr, m + 1, r, lower, upper) +
                merge(arr, l, m, r, lower, upper);

    }

    // 在左右已经有序情况下，我们在merge中，脱离merge排序，然后实现一个O（N）复杂度的取累加和
    // O（N）级别就不能使得指针回退，一直向右
    // 对于右组的数来说，其减去同在右组的左边的数的子数组的累加和已经在右组更小的递归中实现了。
    // 所以在此右组的数只针对于左组的数求子数组累加和即可。
    public int merge(int[] arr, int l, int m, int r, int lower, int upper) {
        // 对于右组的数x，从左到右已经单调递增，其相对于左组数的子数组累加和，要在[lower,upper)范围中，则左组数应该在[x-upper,x-lower)范围中
        // 并且随着右组数的逐渐增大，x-upper和x-lower也是逐渐增大的，代表左组数就是一直往右走的。这就使得左组数的指针不会回退。
        //即问题转为右组中某个数要求左组中数在什么范围之内，取左组这些数的个数，并且这个范围是随右组中数往右，逐渐变大的，这就使得左组中范围也是王右不回退。

        // 定义两个窗口指针，代表每个右组数对应的左组数的范围
        int windowL = l;// x-upper位置
        int windowR = l;// x-lower位置
        int sum = 0;
        for (int i = m + 1; i < r; i++) {
            // 左组数的范围,min-max
            // x-upper
            long min = arr[i] - upper;
            // x-lower
            long max = arr[i] - lower;
            // 因为左组数是单调的，窗口中的个数即为满足要求的子数组的个数
            // 移动窗口右指针
            while (windowR <= m && arr[windowR] <= max) {
                windowR++;
            }
            // 移动窗口左指针
            while (windowL <= m && arr[windowL] <= min) {
                windowL++;
            }

            sum += windowR - windowL;
        }

        // merge排序，排序并不影响上面的下一次的子数组的取个数过程，而是上面过程依赖于上一次的排序
        // 排序之后右组的数只针对于左组数，而左组数被排序之后并不影响取子数组过程，实际上左组所有数都要对应每个右组中的数。所以排序防止了指针不回退导致循环对应。
        int LP = l;
        int RP = m + 1;
        int i = 0;
        int[] temp = new int[r - l + 1];
        while (LP <= m && RP <= r) {
            temp[i++] = arr[LP] <= arr[RP] ? arr[LP++] : arr[RP++];
        }
        // 越界处理
        while (LP <= m) {
            temp[i++] = arr[LP++];
        }
        while (RP <= m) {
            temp[i++] = arr[RP++];
        }
        // 覆盖原数组
        for (int j = 0; j < temp.length; j++) {
            arr[l + j] = arr[j];
        }

        return sum;
    }

}
