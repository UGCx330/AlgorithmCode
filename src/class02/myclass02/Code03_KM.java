package class02.myclass02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Code03_KM {
    // 一个数组中有一种数出现K次，其他数都出现了M次，
    // 已知M > 1，K < M，找到出现了K次的数(K,M为已知值)
    // 要求额外空间复杂度O(1)，时间复杂度O(N)

    // 思路：int类型长度为32位，将数组中每个数打散成二进制。
    // 定义一个32长度的int[]arr2，然后将每个数的二进制填入arr2中。
    // arr2下标%M如果！=0，

    // 首先两种数未知情况下，第一个1出现的位置反正就32个位置。
    // 我们使用一个map来形成第一个1(0001,00010,00100.....)和32个位置的对应关系，然后get即可
    public static void weizhi(Map map) {
        int the1 = 1;//......000001
        for (int i = 0; i < 32; i++) {
            map.put(the1, i);//...00001-->t数组种的0位置
            the1 = the1 << 1;//....000010-->...
        }
        // 循环过后，map中存储的就是第一次1出现转化为值对应t数组中位置
    }

    public static HashMap<Integer, Integer> map = new HashMap<>();

    public static int findK(int[] arr, int k, int m) {
        if (map.size() == 0) {
            weizhi(map);
        }
        // 建立32长度数组，用来存储在某个位置上1的个数
        int[] t = new int[32];

        // 外层循环遍历每个数，内层循环遍历每个数1出现的位置。然后t对应的下标++.
        // 每个数中，先找第一个1出现的位置num2，num2 = num & (~num + 1);然后去map中将num2作为key，查找值，值就是第一次出现1的位置。
        // 然后让num^num2，这样由于num2除了1其余全是0，且第一次出现1位置的1异或之后也变成了0.就去除掉了第一次出现1的1，异或之后的结果就是除去第一次出现1的其余位置的1.

        // 核心点：num2=除了最右侧1保留，其全为0
        // num^num2会将最右侧1去除，保留其余1.
        // 然后再循环num2 = num & (~num + 1);
        // 此时num2就是下一次最右侧的1，其余全是0.
        // 这样num2每次都是最新的最右侧的1.
        // 直到num去除很多1之后只有0，就变成0了，此时停止循环
        for (int num : arr) {
            // num==0代表num没有1了。就不用将1次数记录到t中了
            while (num != 0) {
                // num2=num最右侧1，其余全是0
                int num2 = num & (~num + 1);
                // 利用map放入num2得到num2代表的1的位置。并累加到t中，标明该位置含有一个1
                t[map.get(num2)]++;
                num = num ^ num2;// 将当前最右侧的1变为0
            }
        }
        // 以上结束之后，t中存储的是arr中所有数的1的在各个位置上(t下标)出现的次数集合。

        // 通过在32位0上异或各个位置的1来形成出现了k次的数--ans
        int ans = 0;
        for (int i = 0; i < 32; i++) {// t的下标代表在i位置上的1，值代表在该位置上1的累计数量
            if (t[i] % m != 0) {// 在i位置上的1%m！=0说明ans在该位置上有1
                if (t[i] % m == k) {
                    ans = ans | (1 << i);// 将1左移i次，就是...1...然后ans或上它。i从0开始，初始值是1，是合理的。
                } else {
                    return -1;// k出现的次数不是用户指定的，而是随机的另一个小于m的数
                }
            }
        }
        // 循环完成后，形成的ans即为出现k次的数

        // 如果循环完之后ans还是0.说明出现了k次的数是0
        if (ans == 0) {
            int count = 0;
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;// k出现的次数不是用户指定的，而是随机的另一个小于m的数
            }
        }
        return ans;
    }

    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    public static int[] randomArr(int kinds, int range, int k, int m) {
        // 出现k次的数
        int kNum = randomNumber(range);
        // 实际上k的值(随机为用户输入的k或者另一个不大于m的值)
        int kTimes = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 数组中数的种类，最低是2种
        int numKinds = (int) (Math.random() * kinds) + 2;
        // 提供数组(减去1为减去k)
        int[] arr = new int[kTimes + (numKinds - 1) * m];
        // 往数组中填充出现k次的值
        int index = 0;// 记录当前填充的位置
        for (; index < kTimes; index++) {
            arr[index] = kNum;
        }
        // 刨除出现k次的数种类
        numKinds--;
        // 防止随机生成的数有重复，用set验证
        HashSet<Integer> set = new HashSet<>();
        set.add(kNum);

        // 根据随机生成的种类循环（如果最低是2种，刨除上面k的那一种，则剩余1种）
        while (numKinds != 0) {
            int elseNum = 0;
            // do-while--先做一次do
            do {
                // 随机生成剩余种类的每种数
                elseNum = randomNumber(range);
            } while (// 当重复生成时再随机生成
                    set.contains(elseNum)
            );
            // 添加进set防止下一次while重复
            set.add(elseNum);
            // 填充其余种类的数
            for (int i = 0; i < m; i++) {
                arr[index++] = elseNum;
            }
            // 一次填充，减少一种数
            numKinds--;
        }

        // 打乱数组
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    // 对数器
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : arr) {
            if (hashMap.containsKey(num)) {
                hashMap.put(num, hashMap.get(num) + 1);
            } else {
                hashMap.put(num, 1);
            }
        }

        for (int num : hashMap.keySet()) {
            if (hashMap.get(num) == k) {
                return num;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int testTimes = 50000;
        int range = 30;// 数组中最大数值
        int kinds = 5;//最大种类
        int max = 9;// 用于生成k， m
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            int[] arr = randomArr(kinds, range, k, m);
            int ans1 = findK(arr, k, m);
            int ans2 = test(arr, k, m);
            if (ans1!=ans2){
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错");
            }
        }
        System.out.println("测试结束");
    }

}
