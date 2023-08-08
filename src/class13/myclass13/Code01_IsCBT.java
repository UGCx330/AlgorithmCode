package class13.myclass13;

public class Code01_IsCBT {
    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 判断一棵树是不是完全二叉树
     * 是完全二叉树的可行性分析：以头节点为中心点画垂线，左边为左子树部分，右边为右子树部分
     * ①左子树是满二叉树，右子树是满二叉树，且左子树高度=右子树高度
     * ②左子树是完全二叉树，右子树是满二叉树，且左子树的高度=右子树高度+1
     * ③左子树是满二叉树，右子树是满二叉树，且左子树高度=右子树高度+1
     * ④左子树是满二叉树，右子树是完全二叉树，且左子树高度=右子树高度
     * <p>
     * 递归套路信息体：
     * ①左右子树的高度
     * ②左右子树是否为完全二叉树
     * ③左右子树是否为满二叉树
     */
    public class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public Info isCBT(Node head) {
        if (head == null) {
            // 空树是满，完全二叉树高度为0
            return new Info(true, true, 0);
        }
        Info leftInfo = isCBT(head.left);
        Info rightInfo = isCBT(head.right);
        int height = leftInfo.height + rightInfo.height;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        // 只要符合可行性任意一种，置isCBT为true
        // 如果左右子树是满二叉树且高度相同，则整棵树是完全二叉树
        boolean isCBT = isFull;
        if (rightInfo.isFull && leftInfo.isCBT && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if (rightInfo.isFull && leftInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if (rightInfo.isCBT && leftInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }

}
