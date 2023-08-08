package class11.myclass11;

public class Code06_SuccessorNode {
    public class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 求二叉树某个节点的后继节点，（中序遍历）
     * 二叉树结构如下定义：
     * Class Node {
     * 	V value;
     * 	Node left;
     * 	Node right;
     * 	Node parent;
     * }
     * 给你二叉树中的某个节点，返回该节点的后继节点
     */

    /**
     * 对于中序遍历，一个节点的后继节点有两种情况
     * ①该节点有右子树，那么后继节点就是右子树的最左下角那个节点
     * ②该节点没有右子树，那么后继节点就是，该节点在其左子树中的  祖先节点
     * 有没有左子树没影响，因为中序是先左再中
     */
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        Node parent = null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            // 循环到根说明节点是整棵树的最右下角一个节点,此时node回到根，parent==null
            return parent;
        }
    }
}
