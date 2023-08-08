package class01.myclass01;

import java.util.Arrays;

public class Code01_SelectSort {
//   阐释： 每次从i到最后，选择最小的数（常数级别），放在i位置。  复杂度：等差数列，N为数组的长度，即O（N^2）

    // 代码：v
    public static int[] selectSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        // 外层次数：根据数据长度N决定循环次数
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            // 内层循环：N确定之后，确定的常数次数的比较，交换
            for (int j = i + 1; j < arr.length; j++) {
                // 如果后面的数大于一开始的arr【i】，则记录此次循环最小值下标为j
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            // 使i位置为此次循环最小值。
            swap(arr, i, minIndex);
        }
        return arr;
    }

    public static int[] selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        // 外层次数：根据数据长度N决定循环次数
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层循环：N确定之后，确定的常数次数的比较，交换
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    swap(arr, i, j);
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
            selectSort(array);
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
            System.out.print(i+"|");
        }
        selectSort(array);
        System.out.println("--------");
        for (int i : array) {
            System.out.print(i+"|");
        }
    }

}
