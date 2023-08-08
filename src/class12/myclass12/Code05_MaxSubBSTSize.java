package class12.myclass12;

public class Code05_MaxSubBSTSize {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的大小(节点个数)
     * 即一棵树的左右子树不一定非得是搜索树，可能左右子树的再左右子树是搜索树也可以
     * 我们可以使用两个size变量记录搜索树大小，allSize即为整个树的节点数，即一颗树包括其所有子树，都是搜索树
     * maxBSTSubtreeSize记录最大子树，即一棵树可能并不都是搜索树。
     * 如果这两个变量相同说明，整棵树就是搜索树。
     * <p>
     * 递归套路信息体：
     * ①allSize
     * ②maxBSTSubtreeSize
     * ③左右子树最大值--左右子树都是搜索树情况下判断头节点是否符合搜索树定义，从而判断整棵树是否是搜索树
     * ④左右子树最小值
     */
    public class Info {
        public int max;
        public int min;
        public int allSize;
        public int maxBSTSubtreeSize;

        public Info(int max, int min, int allSize, int maxBSTSubtreeSize) {
            this.max = max;
            this.min = min;
            this.allSize = allSize;
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
        }
    }

    public Info maxSubBSTSize(Node head) {
        if (head == null) {
            // 上游处理
            return null;
        }
        Info leftInfo = maxSubBSTSize(head.left);
        Info rightInfo = maxSubBSTSize(head.right);
        int max = head.value;
        int min = head.value;
        int allSize = 1;
        // 先更新，左右子树是不是搜索树再说
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            allSize += rightInfo.allSize;
        }
        // 左子树不为null，p1就是左子树中最大搜索子树的大小
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        // 右子树不为null，p2就是右子树中最大搜索子树的大小
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        // p3就是如果左右子树整个都是搜索树的情况下，如果head满足>左子树最大值，<右子树最小值，那么p3=整颗子树的大小就是搜索树的大小
        int p3 = -1;
        // 如果左子树空树，整个左子树就是搜索树，且null可以小于head值，左子树不为null，如果左子树的最大搜索子树大小就是左子树大小，说明左子树整个就是搜索树
        boolean allLeftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        // 如果右子树空树，整个右子树就是搜索树，且null可以小于head值，右子树不为null，如果右子树的最大搜索子树大小就是右子树大小，说明右子树整个就是搜索树
        boolean allRightBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        // 如果左右子树都是搜索树，那么看head为头的整棵树是不是搜索树
        if (allLeftBST && allRightBST) {
            // 看整棵树是否符合搜索树定义
            boolean leftLess = leftInfo == null ? true : (leftInfo.max < head.value);
            boolean rightLess = rightInfo == null ? true : (rightInfo.max < head.value);
            // 左右子树为null或者左子树max<head<右子树min，那么整棵树就是搜索树，更新p3为整棵树大小
            if (leftLess && rightLess) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }

        return new Info(max, min, allSize, Math.max(p3, Math.max(p1, p2)));
    }

}
