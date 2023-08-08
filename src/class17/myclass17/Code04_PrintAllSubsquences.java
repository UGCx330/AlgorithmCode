package class17.myclass17;

import java.util.ArrayList;

/**
 * 打印一个字符串的所有字串
 * 解法：
 * 每一个字符都可以决定要还是不要，如果0位置要，那么1位置要/不要，如果1要，那么2要/不要......
 */
public class Code04_PrintAllSubsquences {
    public ArrayList<String> allSubsquences(String str) {
        ArrayList<String> ans = new ArrayList<>();
        char[] chars = str.toCharArray();
        process(ans, "", chars, 0);
        return ans;
    }

    // index为当前来到chars的哪个位置，pre为到此位置位置前面做的决定的字符串拼接
    public void process(ArrayList<String> list, String pre, char[] chars, int index) {
        // baseCase,这条路径走完，将结果添加到答案
        if (index == chars.length) {
            list.add(pre);
            return;
        }
        // 要
        process(list, pre + chars[index], chars, index + 1);
        // 不要
        process(list, pre, chars, index + 1);
    }

    /*
    如果要求去重，则使用一个set代替list存储答案即可
     */
}
