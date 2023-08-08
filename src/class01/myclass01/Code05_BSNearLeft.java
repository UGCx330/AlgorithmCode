package class01.myclass01;

import java.util.Arrays;

public class Code05_BSNearLeft {
    // 在一个有序数组中找>=某个数最左侧的位置
    public static int erLeftFenGetNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1) {
            if (arr[0] >= num) {
                return 0;
            }
        }

        int left = 0;
        int right = arr.length - 1;
        int middle = 0;
        int index = -1;

        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] >= num) {
                index = middle;
                right = middle - 1;
            } else{
                left = middle + 1;
            }
        }
        return index;
    }

    // 对数器
    public static int testerFen(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= num) {
                return i;
            }
        }
        return -1;
    }

    // 随机数组生成器
    public static int[] randomArr(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
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
            int num = (int) (Math.random() * maxValue + 1) - (int) (Math.random() * maxValue);
            int[] arr = randomArr(maxSize, maxValue);
            Arrays.sort(arr);
            int i1 = testerFen(arr, num);
            int i2 = erLeftFenGetNum(arr, num);
            if (i1 != i2) {
                succeed = false;
                System.out.println(i1 + "|" + i2+"|"+num);
                break;
            }
        }
        System.out.println(succeed ? "Yes" : "No");
    }
}
