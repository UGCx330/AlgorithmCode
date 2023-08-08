package class10.myclass10;

public class Code02_RecursiveTraversalBT {
    /**
     * 二叉树
     */
    public class Node {
        private Node left;
        private Node right;
        private int value;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 递归函数可以将方法形参分成三次处理。
     * 第一次是进入递归函数的时候
     * 第二次是调用了自己递归函数2，等这个递归函数2处理完再回到当前递归函数时可以再处理方法形参
     * 第三次是调用了自己递归函数3，等这个递归函数3处理完再回到当前递归函数时可以再处理方法形参
     */

    /**
     * 递归实现：二叉树先序遍历
     */
    public void headPre(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value + "|");
        headPre(node.left);
        headPre(node.right);
    }

    /**
     * 递归实现：二叉树中序遍历
     */
    public void headMiddle(Node node) {
        if (node == null) {
            return;
        }
        headPre(node.left);
        System.out.println(node.value + "|");
        headPre(node.right);
    }

    /**
     * 递归实现：二叉树后序遍历
     */
    public void headLast(Node node) {
        if (node == null) {
            return;
        }
        headPre(node.left);
        headPre(node.right);
        System.out.println(node.value + "|");
    }
}
