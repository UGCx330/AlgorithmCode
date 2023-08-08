package class12.myclass12;

public class Code06_MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
     * 距离分析：对于一个树，其最大距离分穿过头节点或者不穿过头节点两种情况：
     * 一：最大距离仅存在于左右子树中
     * 二：最大距离为左子树的高度+头节点+右子树的高度
     * <p>
     * 递归套路信息体：
     * ①左右子树的最大距离
     * ②左右子树的高度
     * 然后在本树中max(左+右子树最大距离，左+右子树高度+1)，得出的就是本树的最大距再往上反。
     */
    public class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public Info maxDistance(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = maxDistance(head.left);
        Info rightInfo = maxDistance(head.right);
        int height = 1 + Math.max(leftInfo.height, rightInfo.height);
        int maxDistance = Math.max(1 + leftInfo.height + rightInfo.height, Math.max(leftInfo.maxDistance, rightInfo.maxDistance));
        return new Info(maxDistance, height);
    }


}
