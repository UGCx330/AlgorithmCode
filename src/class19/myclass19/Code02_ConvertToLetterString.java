package class19.myclass19;

/**
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 * 解：
 * 从左到右的递归。每个递归有两种选择：
 * ①当前位置的数字直接转为字母，由于字母ASCII码是1到26，所以单个数字肯定能转为字母
 * ②当前位置的数字和下一个位置的数字一起转为字母
 */
public class Code02_ConvertToLetterString {
    public int start1(String str) {
        return process1(str.toCharArray(), 0);
    }

    public int process1(char[] chars, int index) {
        // baseCase,如果能走到最后，说明中间没有出错，返回1
        if (index == chars.length) {
            return 1;
        }
        // 如果此位置的数字是0，说明上个递归出错了,直接此路不通返回0
        if (chars[index] == '0') {
            return 0;
        }
        // index位置选择单独转还是跟下一个一起
        int ans = process1(chars, index + 1);
        // 只有结合下一个数字得出来的字母的ASCII在1到26&&下一个递归不能单独留个0也就是下一个递归不能返回-1，才能跟下一个数字一起转为字母
        // 注意由于要跳一个，所以必须判断index+1<总长度，才能保证index+2能进入递归返回1
        if (index + 1 < chars.length && (chars[index] - '0') * 10 + (chars[index + 1] - '0') <= 26) {
            // 直接跳一个
            ans += process1(chars, index + 2);
        }
        return ans;
    }

    public int start2(String str) {
        return process2(str.toCharArray(), 0);
    }

    /**
     * 动态规划数组:
     * 由于只与index有关，所以是一维数组
     */
    public int process2(char[] chars, int index) {
        int N = chars.length;
        int[] ints = new int[N + 1];
        // 填充baseCase：最后一个格子为1
        ints[N] = 1;
        // 从右往左填充
        for (int i = N - 1; i >= 0; i--) {
            if (chars[i] != '0') {
                int ans = ints[i + 1];
                if (i + 1 < N && ((chars[i] - '0') * 10 + (chars[i + 1] - '0') < 27)) {
                    ans += ints[i + 2];
                }
                ints[i] = ans;
            }
        }
        return ints[0];
    }
}
