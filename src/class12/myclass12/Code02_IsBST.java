package class12.myclass12;

public class Code02_IsBST {
    public class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    /**
     * 判断二叉树是不是搜索二叉树
     * 搜索二叉树：每个子树中的头节点值大于其左子树的所有节点值，且小于其右子树的所有节点值
     * 且每个节点的值唯一
     */

    /**
     * 递归套路：
     * 定义信息体：递归中，所有树都必须一视同仁
     * 需要向左右子树要的信息为：
     * ①左右子树是否是搜索二叉树---任意子树不是整个都不是
     * ②左右子树的最大值最小值---左子树最大值<头节点<右子树最小值
     * 空树处理：认为是搜索二叉树，且null可以小于任意值，可以大于任意值,直接返回null即可。
     */
    public class Info {
        private boolean isBST;
        private int max;
        private int min;

        public Info(Boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public  Info process(Node head) {
        if (head == null) {
            // 上游处理null
            return null;
        }
        // 左子树要信息
        Info leftInfo = process(head.left);
        // 右子树要信息
        Info rightInfo = process(head.right);
        // 自己作为左/右子树要传给祖先节点的信息--根据要来的左右子树信息修改
        int max = head.value;
        int min = head.value;
        boolean isBST = true;
        // 更新信息,如果左右子树有一个不是搜索树，就算更新了信息，返回Info中也不是搜索二叉树
        if (leftInfo != null) {
            // 说明左子树不为空树,先更新信息
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            // 右子树不为空树，先更新信息
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        // 根据左右信息判断该树是否应该是搜索二叉树
        // 如果左右子树是空树，则默认是搜索二叉树
        if (leftInfo != null && !leftInfo.isBST) {
            // 如果左子树明确不是搜索二叉树,那么整个树就不是搜索二叉树
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            // 如果右子树明确不是搜索二叉树,那么整个树就不是搜索二叉树
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= head.value) {
            // 如果左子树不是空树,且最大值大于等于头节点值，那么整个树就不是搜索二叉树
            isBST = false;
        }
        if (rightInfo != null && rightInfo.max <= head.value) {
            // 如果右子树不是空树,且最大值小于等于头节点值，那么整个树就不是搜索二叉树
            isBST = false;
        }
        // 如果以上判断都不成立，说明是搜索二叉树，先前已经更新好的信息返回
        return new Info(isBST, max, min);
    }


}
