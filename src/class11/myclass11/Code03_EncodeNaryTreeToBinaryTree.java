package class11.myclass11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code03_EncodeNaryTreeToBinaryTree {
    // 多叉树
    public class ManyNode {
        private Integer value;
        private List<ManyNode> children;

        public ManyNode() {
        }

        public ManyNode(Integer value) {
            this.value = value;
        }

        public ManyNode(Integer value, List<ManyNode> children) {
            this.value = value;
            this.children = children;
        }
    }

    // 二叉树
    public class Node {
        private Integer value;
        private Node left;
        private Node right;

        public Node(Integer value) {
            this.value = value;
        }
    }

    /**
     * N叉树的序列化
     * N叉树：一个节点中有一个Node[]属性
     * 转为二叉树：将每个节点head的孩子们-Node[]，变成head-左节点Node[0],左节点.right斜着一排Node[...]
     * head     变成       head
     * 1  2  3  4         1
     * ---------------------2
     * -----------------------3
     * -------------------------4
     * 然后多叉树的1，2，3，4下面还有孩子们，都对应二叉树的1，2，3，4再有左节点的右子树一整排
     */

    public Node nTreeSerialize(List<ManyNode> list) {
        // 记录多叉树的第一个孩子。
        Node head = null;
        // 记录循环到哪个孩子了，转为二叉树结点，记录下来，以便挂载下一个多叉树节点转为二叉树节点的孩子
        Node current = null;
        for (ManyNode manyNode : list) {
            // 孩子变为二叉树节点
            Node node = new Node(manyNode.value);
            if (head == null) {
                head = node;
            } else {
                // 挂载
                current.right = node;
            }
            // 指向当前挂载的节点
            current = node;
            // 继续生成右子树
            current.left = nTreeSerialize(manyNode.children);
        }
        // 返回右子树头节点也就是原来的第一个孩子变成的二叉树节点
        return head;
    }

    /**
     * 反序列化
     * 将某个右子树，转为一组多叉树孩子节点
     */
    public List<ManyNode> deNTreeSerialize(Node root) {
        ArrayList<ManyNode> children = new ArrayList<>();
        while (root != null) {
            ManyNode manyNode = new ManyNode(root.value, deNTreeSerialize(root.left));
            children.add(manyNode);
            root = root.right;
        }
        return children;
    }
}
