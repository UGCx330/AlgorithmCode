package class12.myclass12;

public class Code04_IsFull {
    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 判断二叉树是不是满二叉树
     * 满二叉树就是满节点的二叉树，即i层的树上的所有节点数量一定是2^i-1个
     * 递归信息体：
     * ①左右子树的高度
     * ②左右子树的节点数量
     * 即函数只负责返回树的节点数和高度，最终在main方法中看是否高度与节点数符合2^i-1即可
     */
    public class Info {
        public int nodes;
        public int height;

        public Info(int nodes, int height) {
            this.nodes = nodes;
            this.height = height;
        }
    }

    public Info isFull(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = isFull(head.left);
        Info rightInfo = isFull(head.right);
        int height = Math.max(rightInfo.height, leftInfo.height)+1;
        int nodes = leftInfo.nodes + rightInfo.nodes+1;
        return new Info(nodes, height);
    }

}
