package class05.myclass05;

import java.util.Stack;

public class Code02_PartitionAndQuickSort {
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 荷兰国旗问题：arr最后一个数arr[R]，要求原数组变为左边小于arr[R]的，中间为等于arr[R](包括arr[R])的，右边大于arr[rR]的。
     * 并返回等于arr[R]的中间域的左右两个下标
     */
    // 定义小于域和大于域两个指针，遍历数组过程中，将小于arr[R]的放在小于域指针前面，大于arr[R]的放在大于域指针后面，等于的放中间
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        // 无效
        if (L > R) { // L...R L>R
            return new int[]{-1, -1};
        }
        // 只有一个数，返回本身
        if (L == R) {
            return new int[]{L, R};
        }

        int less = L - 1;// 小于域指针
        int more = R;// 大于域指针,先将arr[R]囊括进来，遍历过程中先不管arr[R]而是拿他当作参考
        // 开始从左往右遍历
        int index = L;// 扫描指针
        while (index < more) {// 扫描指针与大于域撞上之前结束
            // 如果当前数等于arr[R]，跳过此数，左右域指针不动，扫描指针++
            if (arr[index] == arr[R]) {
                index++;
            }
            // 如果当前数小于arr[R]，当前数与小于域指针前一个数做交换，小于域指针++,扫描指针++
            if (arr[index] < arr[R]) {
                swap(arr, ++less, index++);
            }
            // 如果当前数大于arr[R]，当前数与大于域指针前一个数交换，大于域指针++，扫描指针++，重新看换过来的数是否小于等于大于，因为此时大于域指针++了，所以如果还是大于的话再换就不一定是啥了
            if (arr[index] > arr[R]) {
                swap(arr, index, --more);
            }
        }
        // 将arr[R]换到大于域第一个位置，变成等于域最后一个位置
        swap(arr, R, more);

        return new int[]{less + 1, more};
    }

    /**
     * 递归快速排序1.0
     */
    // 每次排序就按照arr[R]排序，小于等于域,大于域,然后以x为中间值，再分左右两边递归排序，一直递归排序总会排好
    public static void quickSort1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int m = partition(arr, L, R);
        // 已经作为中间值的就不管了，只递归中间值左右分开的，将左右作为新数组递归
        quickSort1(arr, L, m - 1);
        quickSort1(arr, m + 1, R);
    }

    // 单次排序，小于等于域,大于域
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        int less = L - 1;
        int index = L;
        while (index < R) {
            // 如果当前值小于等于arr[R]，当前数与小于等于域指针前一个数交换
            // 如果大于，则不做处理
            // 最后大于的都被推倒最后面
            if (arr[index] <= arr[R]) {
                swap(arr, index, ++less);
            }
            index++;
        }
        // 交换到小于等于域和大于域之间
        swap(arr, R, ++less);
        return less;
    }

    /**
     * 递归快速排序2.0
     */
    // 在1的基础上，使用荷兰国旗解决方法，将等于arr[R]的放在中间，每次递归后这些数就不管了，将这些数左右分成两个组，再递归
    public static void quickSort2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }

        // 得到==arr[R]的中间位置左右下标,这些之间不管，左右再递归
        int[] m = netherlandsFlag(arr, L, R);
        quickSort2(arr, L, m[0] - 1);
        quickSort2(arr, m[1] + 1, R);
    }

    /**
     * 递归快速排序3.0
     */
    // 2.0中，如果arr[R]本身就是数组中最大数，那么根据小于arr[R]的放在小于域的操作跟没做是一样的，唯一有用的是把arr[R]提取出来下一次递归不用管了。复杂度就变成了O(N^2)
    // 最好情况当然是每次的arr[R]其实都是中点数。
    // 要打乱这种极端情况，我们可以每次不使用arr[R]，而是随机从数组中抽一个数，代替arr[R]
    public static void quickSort3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // 随机交换arr[R]
        swap(arr, arr[L + (int) (Math.random() * (R - L + 1))], R);
        // 递归
        int[] m = netherlandsFlag(arr, L, R);
        quickSort3(arr, L, m[0] - 1);
        quickSort3(arr, m[1] + 1, R);
    }

    /**
     * 快速排序3.0--非递归版本--栈的使用
     */
    // 递归行为本身使用的就是系统栈，将每次调用的递归函数未得到结果前存入栈中。
    // 我们自己 创建一个栈，我们在栈中存储一个类的对象，这个对象有两个属性，就是每次要处理的数组的左右边界。
    // 先将最大的数组边界压入栈，然后while中弹出node，调用荷兰国旗，得出m，再将L，m-1  m+1，R 分别压入栈，这样就得到了两个新node
    // 再弹出node，将L，m-1使用荷兰国旗，再得到两个新node压入栈。
    // 然后逐步弹出右组的node，然后右组node继续分裂，入栈，while直到栈空停止。

    // 用来存储数组边界的辅助类
    public static class Op {
        public int l;
        public int r;

        public Op(int left, int right) {
            l = left;
            r = right;
        }
    }

    public static void quickSortStack(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 随机交换arr[R]
        swap(arr, (int) (Math.random() * N), N - 1);

        Stack<Op> stack = new Stack<>();
        int[] m = netherlandsFlag(arr, 0, N-1);
        // 第一次入栈
        stack.push(new Op(0, m[0] - 1));
        stack.push(new Op(m[1] + 1, N-1));
        // 循环出栈，荷兰国旗，入栈，直到栈空
        while (!stack.isEmpty()) {
            // 出栈,继续荷兰国旗，分两个node再入栈
            // 正是因为先有出栈操作，所以就相当于每次将大的数组弹出去换成更小的数组，这样不会导致栈中node累积
            Op op = stack.pop();
            if (op.l >= op.r) {
                continue;
            }

            // 荷兰国旗前，随机交换
            swap(arr, op.r, op.l + (int) (Math.random() * (op.r - op.l + 1)));
            // 得到m
            int[] m1 = netherlandsFlag(arr, op.l, op.r);
            // 再入栈
            stack.push(new Op(op.l, m1[0] - 1));
            stack.push(new Op(m1[1] + 1, op.r));
        }

    }


}
