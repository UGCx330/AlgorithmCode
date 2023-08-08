package class17.myclass17;

/**
 * 汉诺塔问题：
 * 左中右三个柱子
 * 规则：小在上
 * 场景：左柱子有N个圆盘，要求移动到右柱子
 * 解法1：
 * 先把1到N-1个圆盘移动到中间，第N个圆盘到右柱子，1到N-1移动到右柱子
 * 递归：
 * 1到N-1到中间柱子，那么1到N-2就需要移动到右柱子，然后N-1移动到中间柱子，1到N-2移动到中间柱子。
 * 1到N-2移动到右柱子，就需要1到N-3移动到中间......
 */
public class Code02_Hanoi1 {
    public void leftToMiddle(int N) {
        // baseCase：N为1时，说明只需要将一个圆盘从左移动到中，直接移动即可
        if (N == 1) {
            System.out.println("Move" + N + "leftToMiddle");
            return;
        }
        // 否则：
        // 将1到N-1从左移动到右
        leftToRight(N - 1);
        // 然后将N移动到中
        System.out.println("Move" + N + "leftToMiddle");
        // 然后将1到N-1从右移到中
        rightToMiddle(N - 1);
    }

    public void leftToRight(int N) {
        if (N == 1) {
            System.out.println("Move" + N + "leftToRight");
            return;
        }
        leftToMiddle(N - 1);
        System.out.println("Move" + N + "leftToRight");
        middleToRight(N - 1);
    }

    public void middleToLeft(int N) {
        if (N == 1) {
            System.out.println("Move" + N + "middleToLeft");
            return;
        }
        middleToRight(N - 1);
        System.out.println("Move" + N + "middleToLeft");
        rightToLeft(N - 1);
    }

    public void middleToRight(int N) {
        if (N == 1) {
            System.out.println("Move" + N + "middleToRight");
            return;
        }
        middleToLeft(N - 1);
        System.out.println("Move" + N + "middleToRight");
        leftToRight(N - 1);
    }

    public void rightToMiddle(int N) {
        if (N == 1) {
            System.out.println("Move" + N + "rightToMiddle");
            return;
        }
        rightToLeft(N - 1);
        System.out.println("Move" + N + "rightToMiddle");
        leftToMiddle(N - 1);
    }

    public void rightToLeft(int N) {
        if (N == 1) {
            System.out.println("Move" + N + "rightToLeft");
            return;
        }
        rightToMiddle(N - 1);
        System.out.println("Move" + N + "rightToLeft");
        middleToLeft(N - 1);
    }


}
