package class03.myclass03;

public class Code08_GetMax {
    // 递归：将大范围切割成一个个嵌套的小范围，解决所有小范围循环合并为解决大范围最终解决最大的范围得出结果。
    // 二分法可以看作有条件的递归，每次砍去一半。
    // 核心：一个递归方法中，应该有两个要求，①满足某个条件时返回的确切值——②不满足某个条件时调用本方法。
    // 并且确保最小的递归调用一定是会满足条件的，

    // 求arr中最大值
    // 时间复杂度小于排序(最坏情况)，因为排序是双重for。
    // 而使用递归相当于：所有数相邻两两相比，得出一堆Max1数，然后Max1数再相邻两两相比得出一堆Max2数。一直到最后只有两个MaxN数比较，得出最大数。
    // 所以递归一定可以写成有尽的形式

    public int getMax(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        } else {
            int middle = left + ((right - left) >> 1);
            int leftMax = getMax(arr, left, middle);
            int rightMax = getMax(arr, middle + 1, right);
            return Math.max(leftMax, rightMax);
        }
    }
}
