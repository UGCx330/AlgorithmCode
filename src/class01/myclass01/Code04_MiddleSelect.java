package class01.myclass01;

import java.util.Arrays;

public class Code04_MiddleSelect {
    // 二分法查找单个数的前提：数组有序
    public static int erFenGetNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;
        int middle = 0;

        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] == num) {
                return arr[middle];
            } else if (arr[middle] < num) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    // 对数器
    public static int testerFen(int[] arr, int num) {
        for (int i : arr) {
            if (i == num) {
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
            int i2 = erFenGetNum(arr, num);
            if (i1 != i2) {
                succeed = false;
                System.out.println(i1 + "|" + i2);
                break;
            }
        }
        System.out.println(succeed ? "Yes" : "No");
    }


}
