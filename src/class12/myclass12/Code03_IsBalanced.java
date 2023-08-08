package class12.myclass12;

public class Code03_IsBalanced {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    /**
     * 判断二叉树是不是平衡二叉树
     * 定义：对于某树而言，其左子树和右子树必须是平衡二叉树，且左子树和右子树的高度相差不能超过1，那么整个树才是平衡二叉树
     */

    /**
     * 递归套路信息体分析：
     * ①左右子树是否是平衡二叉树
     * ②左右子树的高度
     */
    public class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public Info isBalanced(Node head) {
        if (head == null) {
            // 如果是空树，是平衡树,高度为0,就不在上游处理了
            return new Info(true, 0);
        }
        // 此时左右子树信息一定不是null，开始利用左右子树信息处理本树,然后将本数作为左/右子树信息返回给祖先节点
        Info leftInfo = isBalanced(head.left);
        Info rightInfo = isBalanced(head.right);
        // 本树信息
        boolean isBalanced = true;
        // 高度为本树头节点，后面再加上左右取最大高度即为本树高度
        int height = 1 + Math.max(leftInfo.height, rightInfo.height);
        // 左/右子树不是，整个树不是
        if (!leftInfo.isBalanced || !rightInfo.isBalanced) {
            isBalanced = false;
        }
        // 左右都是平衡二叉树,高度差超过1，则整棵树不是
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }


}
