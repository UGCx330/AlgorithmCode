package class13.myclass13;

import java.util.List;
import java.util.TreeSet;

public class Code05_LowestLexicography {
    /**
     * 给定一个字符串数组，返回将他们拼接在一起的所有拼接可能性
     * 可能性分析：以第1个字符串为开头...,以第2个字符串为开头...,以第3个字符串为开头...
     * 以第几个字符串开头，就从数组中去掉该字符串，剩下的字符串递归拼接
     */
    public TreeSet<String> lowestLexicography(String[] strings) {
        TreeSet<String> treeSet = new TreeSet<>();
        if (strings.length == 0) {
            treeSet.add("");
            return treeSet;
        }
        for (int i = 0; i < strings.length; i++) {
            //first为以每个字符串数组中的字符串为开头
            String first = strings[i];
            String[] afterRemoveStrings = remove(strings, i);
            // 除去first以外的所有字符串拼接组合
            TreeSet<String> allSplitSituations = lowestLexicography(afterRemoveStrings);
            for (String s : allSplitSituations) {
                // first拼接上这些组合就是以first为头的所有拼接组合
                treeSet.add(first + s);
            }
        }
        return treeSet;
    }

    // 移除i位置字符串
    public String[] remove(String[] strings, int index) {
        String[] strings1 = new String[strings.length - 1];
        int start = 0;
        for (int i = 0; i < strings.length; i++) {
            if (i != index) {
                strings1[start++] = strings[i];
            }
        }
        return strings1;
    }
}
