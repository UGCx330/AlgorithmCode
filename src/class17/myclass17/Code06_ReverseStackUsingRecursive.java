package class17.myclass17;

import java.util.Stack;

/**
 * 给定一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数
 * 解法：
 * 将最后一个元素弹出，记为N
 * 将1到N-1落到栈底
 * 递归此过程，最后一次弹出时候，也就是原本最上面的值1，然后此时压回
 * 然后返回上级递归，压回2...
 * 最后压回N
 */
public class Code06_ReverseStackUsingRecursive {
    /**
     * 前置：取出最下面的节点并将其他节点原封不动地落下去
     * <p>
     * 对于每一个递归来说，如果当前弹出的节点是栈的最后一个节点，那么就是baseCase也就是答案
     * 如果不是最后一个节点，说明此节点不是答案，先记下当前弹出的节点，因为已经弹出了也就相当于去掉了非答案，
     * 然后假定下一个节点是否是最后一个节点，baseCase弹出此节点并返回这一级，再使用变量接收，最终返回，返回之前把当前弹出的节点压回栈中
     * <p>
     * 即过程为：
     * 不是答案--去除--下一级要答案--压回还原--返回答案
     */
    public int getLowerNodeAndPushOthers(Stack<Integer> stack) {
        Integer pop = stack.pop();
        if (stack.isEmpty()) {
            // 最后一个节点就是答案，返回
            return pop;
        }
        // 否则，在当前节点不是答案暂时弹出地基础上，向下一级要答案，要到了就返回答案，并且将弹出地这个点压回去
        int ans = getLowerNodeAndPushOthers(stack);
        stack.push(pop);
        return ans;
    }

    /**
     * 开始反转
     * 即每次递归使用前置方法得到最底下节点并记录，直到栈为空，
     * 此时最底的递归记录的就是最上面的节点，然后函数中直接压入，返回上一层递归再去压入
     * 实现逆序压入
     */
    public void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            // baseCase
            return;
        }
        // 得到并弹出最底下节点，其他节点原封不动落下去
        int lowerNode = getLowerNodeAndPushOthers(stack);
        reverse(stack);
        // 如果栈空了baseCase，返回到此处，此时lowerNode就是最上面的节点,压入栈，返回上一层递归，递归压入
        stack.push(lowerNode);
    }

    /**
     * 如果允许使用额外一个栈实现逆序
     * 栈1是原栈节点为123，栈2为额外栈
     * 解法：
     * 栈1第一个1弹出，变量接收，然后其他节点逐个弹出压入栈2
     * 然后变量回栈1，栈2节点回栈1，此时栈1从上往下为231
     *
     * 然后栈1第一个2弹出，变量接收，然后只有3弹出压入栈2
     * 然后变量回栈1，栈2节点回栈1，此时栈1就是321了
     *
     * 即i次从栈1弹出一个节点变量接收，并将栈1的2到N-i+1个节点全部弹出入栈2，变量回栈1，栈2节点回栈1
     * 可以使用额外变量控制弹出几次
     */
}
