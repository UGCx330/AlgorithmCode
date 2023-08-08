package class02.myclass02;

import java.util.Arrays;

public class Code02_EvenTimesOddTimes {
    // 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数

    // 异或性质：满足交换律结合律，即异或结果不受异或的顺序影响，异或为不进位的加法运算，所以0异或任意数为任意数，任意数异或自身为0。
    // 数组中所有数互相异或，偶数次数全部归零。0异或剩余的单个奇数次的最后一个数即为奇数次的数。
    public static int jishu(int[] arr) {
        int num = 0;
        for (int i : arr) {
            num = num ^ i;
        }
        return num;
    }

    // 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
    public static int[] jioushu(int[] arr) {
        // 先得出只含有这两种数的两个数异或的结果num
        int num = 0;
        for (int i : arr) {
            num = num ^ i;
        }
        // 结果为两个不同的数异或，对于这个结果而言，最右边的第一个1，就表示在这个1的位置，一个奇数为1，一个奇数为0，因为0和1异或才是1.
        // 所以只要让异或结果变成除了第一个1其余为0的形式，再&数组中的这两个数，结果为0或者1，就可以找到这两个奇数次的数。
        //    找出一个数中第一个1出现的位置的01表示--num:1010  ~num:0101  ~num+1:0110  num&(~num+1):0010

        // 形成第一次1的形式
        int num2 = num & (~num + 1);

        // 数组异或，但是分成两派，即num的第一次1出现位置为0或者1的两派。这样异或出来必定是在num第一次1出现位置为0/1的所有数的异或结果，最终即某个奇数。
        int otherNum = 0;
        for (int i : arr) {
            // 分成两派，两种奇数也分成了两派
            if ((i & num2) != 0) {
                otherNum = otherNum ^ i;
            }
        }
        int theOtherNum = num ^ otherNum;
        return new int[]{otherNum, theOtherNum};
    }

    // 总结
    //①异或结果的第一次1出现的位置，对于这两个数而言一定一个为0一个为1，所以生成异或结果的第一次1出现位置的01形式：num2=num&(~num+1)
    //      num2为某个位置为1，其余位置全为0。
    //②int num3=0. ifnum2&数==0/！=0，num3=num3^某个数。第一个数就会被挑出来
    //③第二个数num4=num^num3.第二个数就会被挑出来。

    public static void main(String[] args) {
        int[] arr1 = {3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1};
        System.out.println(jishu(arr1));
        System.out.println("----");
        int[] arr2 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        for (int i : jioushu(arr2)) {
            System.out.print(i + "|");
        }
    }
}
