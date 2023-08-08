package class11.myclass11;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_LevelTraversalBT {
    public class Node {
        private Node left;
        private Node right;
        private Integer value;

        public Node(Integer value) {
            this.value = value;
        }
    }

    /**
     * 二叉树的按层遍历
     * 二叉树的深度遍历采用栈结构，因为一个节点弹出后，压入的是子节点，然后子节点又弹出，再压入子节点的子节点
     * 二叉树的按层遍历使用队列，节点出去以后，子节点进入但是排在兄弟节点的后面
     */
    public void queuePoll(Node head) {
        if (head == null) {
            return;
        }
        // 存放树节点,弹出树节点
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.value + "|");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }
}
