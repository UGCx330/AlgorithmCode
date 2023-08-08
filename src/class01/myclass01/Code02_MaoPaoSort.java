package class01.myclass01;

import java.util.Arrays;

public class Code02_MaoPaoSort {
//   阐释： 每次从第一个数开始往后每两个进行比较，大的排在后面，循环N次，逐渐将大数排在后面。

    // 代码：
    public static int[] MaoPaoSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        // 外层次数：根据数据长度N决定循环次数
        for (int i = arr.length - 1; i > 0; i--) {
            // 内层循环：每次比较相邻两个数，大的交换到后面
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }


    // 交换
    public static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    // 对数器
    public static int[] sort(int[] arr) {
        Arrays.sort(arr);
        return arr;
    }

    // 随机数组生成器
    public static int[] randomIntArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue) * Math.random());
        }
        return arr;
    }

    // 数组复制
    public static int[] coryIntArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] arr2 = new int[arr.length];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] array = randomIntArray(maxSize, maxValue);
            int[] array1 = coryIntArray(array);
            MaoPaoSort(array);
            Arrays.sort(array1);
            if (!Arrays.equals(array1, array)) {
                succeed = false;
                System.out.println(array1);
                System.out.println(array);
                break;
            }
        }

        System.out.println(succeed ? "Yes" : "No");

        int[] array = randomIntArray(maxSize, maxValue);
        for (int i : array) {
            System.out.print(i + "|");
        }
        MaoPaoSort(array);
        System.out.println("--------");
        for (int i : array) {
            System.out.print(i + "|");
        }
    }

}
