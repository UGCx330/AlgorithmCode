package class11.myclass11;

import java.util.ArrayList;
import java.util.List;

public class Code032_EncodeNaryTreeToBinaryTree {
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
     * 多叉树转二叉树：将每个多叉树根的头节点传入，new一个值相同的二叉树头节点，将多叉树的节点数组转为一个子树，将子树挂载在二叉树头节点上
     */
    public Node serialize(List<ManyNode> manyNodeList) {
        Node head = null;
        Node current = null;
        for (ManyNode manyNode : manyNodeList) {
            Node node = new Node(manyNode.value);
            if (head == null) {
                head = node;
            } else {
                current.right = node;
            }
            current = node;
            current = current.right;
            // 递归序，先将第一个二叉树节点的左节点接上右子树(对应的多叉树节点转成的)
            current.left = serialize(manyNode.children);
        }
        return head;
    }

    /**
     * 反序列化：将二叉树的右子树变为原来的多叉树孩子数组
     */
    public List<ManyNode> deSerialize(Node root) {
        ArrayList<ManyNode> list = new ArrayList<>();
        // root逐渐往右下走
        while (root != null) {
            ManyNode manyNode = new ManyNode(root.value, deSerialize(root.left));
            list.add(manyNode);
            root = root.right;
        }
        return list;
    }

}
