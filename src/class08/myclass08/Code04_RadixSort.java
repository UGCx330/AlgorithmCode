package class08.myclass08;

import java.util.Map;

public class Code04_RadixSort {
    /**
     * 基于桶模型的原始排序方法：
     * 一个数组，找到最大值，看最大值有几位数(比如三位，就是个十百)，在其他两位数的百位上补0，个位数的十位百位补0，补成三位数
     * 准备0号到9号十个桶(队列)，代表0到9十个数字，分三次循环
     * 第一次循环，将所有数按照个位数字对应哪个桶放在哪个桶中，然后从0号到9号每个桶按照先进先出的形式弹出各个数，此时各个数按照个位上从小到大排好序了。因为桶的号就是从小到大的
     * 第二次循环按照十位数字，弹出后在原先个位排好序的前提下，按照百位排序。因为十位数字相同的话会被放在同一个桶中，先进先出原则，原先个位排好序的个位小的先进也先出。
     * 第三次按照千位
     * 三次循环过后就是排序好的样子
     * <p>
     * 基于桶模型的基数排序方法：
     * 准备一个count数组，下标为0到数组中出现的最大数字--是数字不是数。
     * count数组的每个下标代表数组中所有数中某一位下出现该下标的次数，假设个位
     * 如110，133，121 count：1，1，0，1  意为count[0]存储着0出现了1次，count[1]存储着1出现了1次.。。。
     * <p>
     * count数组接着进化为count2数组
     * count2数组0下标位置代表数组中的数字某一位下小于等于0的共有多少，数组1下标位置代表数组中的数字小于等于1的共有多少.。。。假设个位
     * arr：110，133，121
     * count：1，1，0，1
     * count2：1，1+1，1+0+1,1+0+1+1
     * count2：1，2，2，3
     * <p>
     * 然后准备一个temp临时数组。与原数组arr完全等长
     * arr取出最大值，最大值有几位如3位数，就开启3次的循环
     * 单次循环中：
     * 从arr的右往左，每次拿出一个数，先看个位数，个位数作为count2的下标，取出count2的值
     * 如121个位数为1，count2[1]=2说明个位小于等于1的一共有两个数，排序后应该在数组的0到1位置， 因为排序是从小到大的，所以应该将121放在temp[1]位置，同时将count2[1]--,变为1，下一个个位数为1的数就应该放在temp[0位置]
     * 如133，个位数为3，count2[3]=3，说明个位小于等于3的一共有3个数，排序后应该在temp的0到2位置，放在temp【2】位置，同时count2[3]--变为2，下一个个位数为3的数就应该放在temp[1]位置
     * count2[3]--后count2[1]不用--，因为count2中存储的实际上是某位上，小于等于某个数的arr中的数有多少个。
     * 如果个位上小于等于4的数有3个，那么第一次遍历arr时，第一个个位数为3的数x，就应该放在数组temp的3下标处，然后count[4]--为2，下一个个位数为3的数y就应该放在数组temp的下标2处。
     * 同时，小于等于3的数一定比小于等于4的数少，所以精髓就在这，count2其实记录的就是个位数为x的第一个arr应该放的位置。count2后面的--，不能更改count2前面的--。count2中相邻下标值的差就是以某个个位数的arr中的数摆放的个数
     *
     * 如果两个十位数相等，个位数大的一定在右边，因为个位数的时候是从右往左放的
     **/

    // 获取数组中最大值的位数
    public int getWeiTimes(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            // 找到数组中的最大值
            max = Math.max(max, arr[i]);
        }
        // 按照最大值取位数--只有个位=1，有十位=2，有百位=3
        int times = 0;
        while (max != 0) {
            times++;
            max = max / 10;
        }
        return times;
    }

    // 根据第几位获取该位上的数字
    public int getWeiNum(int num, int wei) {
        // pow：10的wei-1次方
        // x：如果取个位上的数，将num保留。如果取十位上的数，将num的个位去除。如果取百位上的数，将num的十位和个位去除。
        int x = num / ((int) Math.pow(10, wei - 1));
        // 取个位/十位/百位的数
        int result = x % 10;
        return result;
    }

    // L和R指定要排序的范围，times就是arr最大值的位数
    public void radixSort(int[] arr, int L, int R, int wei) {
        // 辅助数组
        int[] temp = new int[R - L + 1];
        // 复用的循环中的临时变量
        int i = 0;
        int j = 0;

        // 位数决定大的循环次数---个位进出桶，十位进出桶，百位进出桶
        for (int time = 1; time < wei; time++) {
            // 记录个位数字在数组中每个数上出现的次数
            // 注意只看个位
            int[] count = new int[10];
            for (i = L; i < arr.length; i++) {
                // 数组中数个位上是什么数字
                j = getWeiNum(arr[i], time);
                // 次数++
                count[j]++;
            }
            // 记录个位数小于等于某个数(count下标)的次数和
            for (i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }
            // 从右往左遍历arr，根据新的count，放数到临时数组temp中
            for (i = R; i >= L; i--) {
                // 小于等于位上数的在数组中数个数,放置后将个数--,下一次小于等于相同的数的个数位置--
                j = count[getWeiNum(arr[i], wei)]--;
                // 个数-1就是放置arr中数在temp的下标位置
                temp[j - 1] = arr[i];
            }
            // 将临时数组中数据覆盖回原数组,然后进行下一次十位上的循环
            int d = L;
            for (int i1 : temp) {
                arr[d++] = i1;
            }
        }

    }
}
