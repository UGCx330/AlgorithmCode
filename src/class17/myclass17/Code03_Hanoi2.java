package class17.myclass17;

/**
 * 汉诺塔问题更抽象化
 * 解法2：
 * 不分左中右柱子
 * 只要是当前N个圆盘所在的位置，都叫from，将N个圆盘移动到的位置，都叫to，而剩下的一个位置也就是N-1要去的位置就是other
 * 具体化：只需要用一个方法，给定from=left，to=right，other=middle即可
 */
public class Code03_Hanoi2 {
    public void hanoi2(String from, String to, String other, int N) {
        if (N == 1) {
            // baseCase
            System.out.println("Move" + N + "from" + from + "to" + to);
            return;
        }
        // 1到N-1从from移动到other，此时当前方法的other相当于递归的to,当前方法的to相当于递归的from
        hanoi2(from, other, to, N - 1);
        System.out.println("Move" + N + "from" + from + "to" + to);
        // 1到N-1从other移动到to,此时当前方法的other相当于递归的from，当前方法的from相当于递归方法的other
        hanoi2(other, to, from, N - 1);
    }
}
