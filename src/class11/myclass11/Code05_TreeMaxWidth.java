package class11.myclass11;

import java.util.LinkedList;
import java.util.Queue;

public class Code05_TreeMaxWidth {
    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 求二叉树的最大宽度
     * 在原本使用队列进行宽度遍历基础上，增加记录每层宽度的变量，最大宽度变量，当前层结束的节点Node1，下一层结束的节点Node2
     * 每一层结束之后(弹出节点Node==Node1),将Node1==Node2，Node2=null，开始新的一层。
     * 新的一层中每压入队列，都要更新Node2为压入的，直到弹出节点Node再次==Node1，就又结束一层
     */
    public Integer maxWidth(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        Node thisEndNode = head;
        Node nextEndNode = null;
        int maxWidth = 0;
        int thisWidth = 0;
        queue.add(head);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            thisWidth++;
            if (node.left != null) {
                // 入队
                queue.add(node.left);
                nextEndNode = node.left;
            }
            if (node.right != null) {
                // 入队
                queue.add(node.right);
                nextEndNode = node.right;
            }
            if (node == thisEndNode) {
                maxWidth = Math.max(maxWidth, thisWidth);
                thisEndNode = nextEndNode;
                thisWidth = 0;
            }
        }
        return maxWidth;
    }

}
