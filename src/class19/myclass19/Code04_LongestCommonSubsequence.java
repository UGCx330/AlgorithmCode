package class19.myclass19;

/**
 * 给定两个字符串str1和str2，
 * 返回这两个字符串的最长公共子序列长度
 * 比如 ： str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
 * 最长公共子序列是“123456”，所以返回长度6
 * 解：从右往左的递归
 * 每个递归都假设为一个公共子序列，则递归中有三个假设：
 * ①以k作为公共子序列结尾,跳过d的判断--做下一个递归
 * ②以d作为公共子序列结尾，跳过k的判断--做下一个递归
 * ③以k和d作为公共子序列结尾--k和d是相等假定成立，才做下一个递归，1+下一个递归。如果不相等，假定不成立直接为0
 * 且在③中，只要相等，那么就判断两者下一个位置，直接从当前分支剔除掉这两个字符，121和112，就不用担心一个字符匹配到另一个数组中多个字符问题等其他问题
 * 所以①②意义上是为了下一个递归的③
 */
public class Code04_LongestCommonSubsequence {
    public int start1(String str1, String str2) {
        return process1(str1.toCharArray(), str2.toCharArray(), str1.length() - 1, str2.length() - 1);
    }

    public int process1(char[] array1, char[] array2, int index1, int index2) {
        // baseCase:如果都走到0位置了，判断两个字符是否相等，相等返回1，不相等返回0
        if (index1 == 0 && index2 == 0) {
            return array1[0] == array2[0] ? 1 : 0;
        }
        // 如果index1走到0位置，index2没有，判断index2当前位置字符与index1，0位置字符是否相等
        if (index1 == 0) {
            if (array1[0] == array2[index2]) {
                // 因为再往前就没字符了，直接返回1即可
                return 1;
            }
            return process1(array1, array2, 0, index2 - 1);
        }
        // 如果index2走到0位置，index1没有，判断index1当前位置字符与index2，0位置字符是否相等
        if (index2 == 0) {
            if (array2[0] == array1[index1]) {
                return 1;
            }
            return process1(array1, array2, index1 - 1, 0);
        }
        // 两者都没有走到头位置,假定以index1位置为公共序列尾,直接不看index2，而是看index2-1位置
        int ans1 = process1(array1, array2, index1, index2 - 1);
        // 两者都没有走到头位置,假定以index2位置为公共序列尾,直接不看index1，而是看index1-1位置
        int ans2 = process1(array1, array2, index1 - 1, index2);
        // 两者都没有走到头位置，且当前位置的两个字符相等，那么就假定以这两个位置尾公共序列尾，看index1-1和index2-1位置
        int ans3 = array1[index1] == array2[index2] ? 1 + process1(array1, array2, index1 - 1, index2 - 1) : 0;
        return Math.max(Math.max(ans1, ans2), ans3);
    }

    /**
     * 动态规划数组：
     * 形参为两个字符串的字符，因为某个子序列是以某个字符为结尾的，所以纵轴也就是行数设为str1的各个字符,横轴也就是列数设为str2的各个字符.
     */
    public int start2(String str1, String str2) {
        return process2(str1.toCharArray(), str2.toCharArray(), str1.length() - 1, str2.length() - 1);
    }

    public int process2(char[] arr1, char[] arr2, int k1, int k2) {
        int row = arr1.length;
        int col = arr2.length;
        int[][] ints = new int[row][col];
        // 填充baseCase：
        if (arr1[0] == arr2[0]) {
            ints[0][0] = 1;
        }
        // 第0列
        for (int i = 1; i < row; i++) {
            if (arr1[i] == arr2[0]) {
                ints[i][0] = 1;
            }
            // 否则依赖于上一行
            ints[i][0] = ints[i - 1][0];
        }
        // 第0行
        for (int i = 1; i < col; i++) {
            if (arr2[i] == arr1[0]) {
                ints[0][i] = 1;
            }
            // 否则依赖于上一列
            ints[0][i] = ints[0][i - 1];
        }
        // 其他格子
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 假设以arr1的当前字符为结尾，则arr2的上一个字符，即行不变，列-1，左边格子
                int ans1 = ints[i][j - 1];
                // 假设以arr2的当前字符为结尾，则arr1的上一个字符，即列不变，行-1，上边格子
                int ans2 = ints[i - 1][j];
                int ans3 = 0;
                // 如果俩个字符相等，就以这俩为结尾，设为1+上一个字符的情况(左上方格子)，不相等就是0
                if (arr1[i] == arr2[j]) {
                    ints[i][j] = 1 + ints[i - 1][j - 1];
                }
                // 取最大填入当前格子
                ints[i][j] = Math.max(Math.max(ans1, ans2), ans3);
            }
        }
        return ints[k1][k2];
    }

}
