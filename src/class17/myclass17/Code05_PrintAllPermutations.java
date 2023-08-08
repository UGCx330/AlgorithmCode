package class17.myclass17;

import java.util.ArrayList;

public class Code05_PrintAllPermutations {
    /**
     * 打印一个字符串的全排序:
     * 方法1：假设字符串4个字符
     * 0位置可以从4个字符任选一个，然后递归1位置，每一种选法都递归1位置
     * 1位置可以从除了0位置的三个字符中任选一个，每一种选法都递归2位置......
     */
    public ArrayList<String> printAllPermutations(String str) {
        // 代替chars，因为list有remove功能
        ArrayList<Character> list = new ArrayList<>();
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            list.add(aChar);
        }
        ArrayList<String> ans = new ArrayList<>();
        process1(ans, "", list);
        return ans;
    }

    // pre为前面位置选择的字符拼接成的字符串
    public void process1(ArrayList<String> ans, String pre, ArrayList<Character> list) {
        // baseCase,所有位置选择完毕，添加到答案中
        if (list.isEmpty()) {
            ans.add(pre);
            return;
        }
        int n = list.size();
        for (int i = 0; i < n; i++) {
            // 从剩下的字符中选择一个，并去除已选择的字符，给下一个递归
            char c = list.get(i);
            list.remove(i);
            process1(ans, pre + c, list);
            // 还原现场，位置一定要对
            list.add(i, c);
        }
    }

    /**
     * 打印字符串全排序方法2：
     * 不需要将已选择的字符去除，然后添加回去。而是采用变换顺序方法。
     * 注意仍然需要还原现场.
     * 假设仍有4个字符
     * 0位置可以与0123位置的字符交换，这样就得到了开头位置的4种情况，任意一种交换选择之后，递归1位置
     * 1位置可以与123位置字符交换，这样就得到了1位置的3种情况，任意一种交换选择之后，递归2位置...
     * 需要注意，x位置，只能与x之后的位置交换，如果1位置可以与0位置交换，那么与上一个递归的0与0位置交换中的1与1交换这一情况重复
     * 即：
     * 交换相当于选择一个字符作为开头，而只能交换后面的相当于前面的被上层递归使用了
     */
    // chars是原字符串的字符数组，在这上面做交换
    // index代表当前来到第几个位置
    public void process2(char[] chars, int index, ArrayList<String> ans) {
        // baseCase:所有位置交换完毕
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
        }
        // index位置可以与index及其之后的字符交换
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            // index+1位置去递归
            process2(chars, index + 1, ans);
            // 还原现场
            swap(chars, i, index);
        }
    }

    public void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /**
     * 方法2的去重
     * 字符串accd
     * a与第一个c交换的结果，与a与第二个c交换的结果中有重复的
     * 如果0位置交换后cacd
     * 那么从1位置开始，可以ccad
     * 如果0位置交换后ccad，就重复了
     * 因为本质上1位置开始会都交换一遍，所以a在哪里都会过一遍
     * 解决方法：
     * 每个递归中定义一个256长度的boolean数组（默认值false）
     * 每个递归只要选择了一个字符，将字符转为ASCII码，对应与数组下标置为true
     * 下一次循环检测此次选择的字符是否为true，是说明使用过了，直接跳过此次循环
     */
    public void process3(char[] chars, int index, ArrayList<String> ans) {
        // baseCase:所有位置交换完毕
        if (index == chars.length) {
            ans.add(String.valueOf(chars));
        }
        boolean[] ifUsed = new boolean[256];
        // index位置可以与index及其之后的字符交换
        for (int i = index; i < chars.length; i++) {
            if (!ifUsed[chars[i]]) {// 如果未被使用
                // 标记为已使用
                ifUsed[chars[i]] = true;
                swap(chars, index, i);
                // index+1位置去递归
                process2(chars, index + 1, ans);
                // 还原现场
                swap(chars, i, index);
            }
        }
    }


}
