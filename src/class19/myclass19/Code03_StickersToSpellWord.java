package class19.myclass19;

import java.util.HashMap;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * ba + ba + c  3  abcd + abcd 2  abcd+ba 2
 * 所以返回2
 * 解：
 * 贴纸数组每个贴纸都可以无限次使用
 * 每个递归，每次选择，可以选择使用任意贴纸
 * 从str中去除使用的贴纸能去除的部分，然后给下个递归
 * 即每次都可以选择任意贴纸的递归
 */
public class Code03_StickersToSpellWord {
    public int start1(String str, String[] arr) {
        // 传给递归函数返回结果
        int ans = process1(str, arr);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int process1(String str, String[] arr) {
        // baseCase:字符串被拼完了，长度为0了，返回0不需要再使用贴纸了
        if (str.length() == 0) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        // 否则还能继续拼,任选一张贴纸拼
        for (String s : arr) {
            // str使用某个贴纸拼完之后剩余的字符串为rest,字符串类型为复制行为，所以不会改变原本的str
            String rest = remove(str, s);
            // 如果当前贴纸无用，则直接使用下一个贴纸，结束当前次循环,当前贴纸有用则进入下一个递归
            if (rest.length() != str.length()) {
                // 继续递归
                ans = Math.min(process1(rest, arr), ans);
            }
        }
        // 只要有一个路是通的，返回的就不是Integer.MAX_VALUE,说明当前递归至少有一个贴纸是使用了的，ans+1
        return ans + (ans == Integer.MAX_VALUE ? 0 : 1);
    }

    public String remove(String str, String s) {
        char[] arrChars = str.toCharArray();
        char[] sChars = s.toCharArray();
        // 0到25的数组
        int[] ints = new int[26];
        // 先将目标字符串转一下
        for (int i = 0; i < sChars.length; i++) {
            // 小写字母转成数字，都减去a，肯定是0到25,对应ints下标0到25的值++
            ints[sChars[i] - 'a']++;
        }
        // 贴纸减一下
        for (int i = 0; i < arrChars.length; i++) {
            ints[arrChars[i] - 'a']--;
        }
        // 重新将ints数组转为剩余的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] > 0) {
                for (int j = 1; j <= ints[i]; j++) {
                    // 下标+'a'=ascii码，然后强转为字符
                    stringBuilder.append((char) i + 'a');
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 简单优化：
     * 不再每次都将贴纸和剩余字符串转成字符数组然后操作
     * 而是直接将贴纸转为二维字符数组
     * 目标字符串也转成字符数组
     * 然后在递归中传递,直接在递归函数中操作
     */
    public int start2(String str, String[] arr) {
        // 所有贴纸的词频统计
        int N = arr.length;
        // 行=多少个字符串
        // 列=26个英文字母
        // 值=26个字母在当前字符串中的词频统计
        int[][] arrChars = new int[N][26];
        // 词频统计
        for (int i = 0; i < N; i++) {
            char[] chars1 = arr[i].toCharArray();
            for (char c : chars1) {
                arrChars[i][c - 'a']++;
            }
        }
        // 传给递归函数返回结果
        int ans = process2(arrChars, str);
        return ans == Integer.MAX_VALUE ? -1 : ans;

    }

    public int process2(int[][] arrChars, String s) {
        // baseCase:目标字符串被拼完,不再需要贴纸，返回0
        // 这里就是为什么不传递s的词频统计的原因，判断baseCase的时候方便,如果s变成了s的词频统计还得遍历看每个位置的值是否全部为0
        if (s.length() == 0) {
            return 0;
        }
        // 目标字符串词频统计
        int[] sChars = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            sChars[c - 'a']++;
        }
        // 开始拼接
        int ans = Integer.MAX_VALUE;
        int N = arrChars.length;
        for (int i = 0; i < N; i++) {
            // 剪枝行为：只选择能拼出目标字符串的第一个字符的贴纸，开始递归。
            // 因为如果此路是通的话，早晚会使用某个贴纸将第一个字符拼出来，所以将这个贴纸提前选择不影响结果
            if (arrChars[i][chars[0] - 'a'] > 0) {
                // 说明此贴纸可选，开始得到rest字符串(不影响词频的情况下),即将两者的词频相减，如果>0，将此位置重新转为字符拼接到rest，>0几个就拼接几个
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (sChars[j] > 0) {// 目标词频数组在此位置有字母,开始拼
                        int num = sChars[j] - arrChars[i][j];
                        for (int k = 1; k <= num; k++) {
                            stringBuilder.append((char) j + 'a');
                        }
                    }
                }
                String rest = stringBuilder.toString();
                ans = Math.min(process2(arrChars, rest), ans);
            }
        }
        return ans + (ans == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 动态规划缓存：
     * 如果要改数组，形参string的变化不是固定归规律变化的，而且变化排列组合巨多，所以不需要使用数组，使用缓存发即可
     * 缓存法：
     * 将每次递归完后，rest和用到的贴纸数都存入HashMap中
     * 如目标为aaabbbccc，贴纸aaa，aaaa......
     * 首选aaa贴纸，其子过程递归完后，rest即为bbbccc，缓存中就有了bbbccc的最短贴指数。
     * 那么首选aaaa为第一张贴纸时，进行他的子过程时，因为传递进去的rest也是bbbccc，所以直接从缓存中获取即可。
     */
    public int start3(String str, String[] arr) {
        // 缓存
        HashMap<String, Integer> map = new HashMap<>();
        // 所有贴纸的词频统计
        int N = arr.length;
        // 行=多少个字符串
        // 列=26个英文字母
        // 值=26个字母在当前字符串中的词频统计
        int[][] arrChars = new int[N][26];
        // 词频统计
        for (int i = 0; i < N; i++) {
            char[] chars1 = arr[i].toCharArray();
            for (char c : chars1) {
                arrChars[i][c - 'a']++;
            }
        }
        // 传给递归函数返回结果
        int ans = process3(arrChars, str, map);
        return ans == Integer.MAX_VALUE ? -1 : ans;

    }

    public int process3(int[][] arrChars, String s, HashMap<String, Integer> map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        // baseCase:目标字符串被拼完,不再需要贴纸，返回0
        // 这里就是为什么不传递s的词频统计的原因，判断baseCase的时候方便,如果s变成了s的词频统计还得遍历看每个位置的值是否全部为0
        if (s.length() == 0) {
            return 0;
        }
        // 目标字符串词频统计
        int[] sChars = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            sChars[c - 'a']++;
        }
        // 开始拼接
        int ans = Integer.MAX_VALUE;
        int N = arrChars.length;
        for (int i = 0; i < N; i++) {
            // 剪枝行为：只选择能拼出目标字符串的第一个字符的贴纸，开始递归。
            // 因为如果此路是通的话，早晚会使用某个贴纸将第一个字符拼出来，所以将这个贴纸提前选择不影响结果
            if (arrChars[i][chars[0] - 'a'] > 0) {
                // 说明此贴纸可选，开始得到rest字符串(不影响词频的情况下),即将两者的词频相减，如果>0，将此位置重新转为字符拼接到rest，>0几个就拼接几个
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (sChars[j] > 0) {// 目标词频数组在此位置有字母,开始拼
                        int num = sChars[j] - arrChars[i][j];
                        for (int k = 1; k <= num; k++) {
                            stringBuilder.append((char) j + 'a');
                        }
                    }
                }
                String rest = stringBuilder.toString();
                ans = Math.min(process2(arrChars, rest), ans);
            }
        }
        ans += (ans == Integer.MAX_VALUE ? 0 : 1);
        map.put(s, ans);
        return ans;
    }


}
