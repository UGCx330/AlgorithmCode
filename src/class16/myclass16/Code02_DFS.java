package class16.myclass16;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 图的深度优先遍历
 * 需要一个栈，用于将弹出的父节点重新压回栈，然后入其他子节点
 * 需要一个set，避免重复压入节点
 * 入栈打印
 */
public class Code02_DFS {
    public void DFS(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.add(head);
        set.add(head);
        System.out.println(head.value);
        while (!stack.isEmpty()) {
            Node father = stack.pop();
            for (Node node : father.nextNodeList) {
                if (!set.contains(node)) {
                    stack.push(father);
                    stack.push(node);
                    set.add(node);
                    System.out.println(node.value);
                    break;
                }
            }
        }
    }
}
