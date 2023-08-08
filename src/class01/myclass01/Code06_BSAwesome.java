package class01.myclass01;

public class Code06_BSAwesome {
    //    局部最小值问题
// 定义何为局部最小值：
// arr[0] < arr[1]，0位置是局部最小；
// arr[N-1] < arr[N-2]，N-1位置是局部最小；
// arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
// 给定一个无序数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
    public static int less(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        // 上述不满足，则前降后升，中间一定有极小值，也就是局部最小值
        // 局部最小值一定是中间值小于左边，大于右边。
        // 如果左边<中间，则在开头到中间先降后升，右指针=中间-1；去左半范围查找。
        // 如果左边>中间，则在中间到最后先降后升，左指针=中间+1，去右半范围查找。
        // 引出去左/右的条件+目标：局部最小值  所以二分法
        int left = 1;
        int right = arr.length - 2;
        int middle = 0;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] > arr[middle - 1]) {
                right = middle - 1;
            } else if (arr[middle] > arr[middle + 1]) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    // 对数器
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1; // no exist
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }
        int left = 1;
        int right = arr.length - 2;
        int mid = 0;
        while (left < right) {
            mid = (left + right) / 2;
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    // 随机数组生成器
    public static int[] randomArr(int maxSize, int maxValue) {
        int[] arr = new int[maxSize];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 50000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArr(maxSize, maxValue);
            int i1 = less(arr);
            int i2 = getLessIndex(arr);
            if (i1 != i2) {
                succeed = false;
                System.out.println(i1 + "|" + i2);
                break;
            }
        }
        System.out.println(succeed ? "Yes" : "No");
    }
}
