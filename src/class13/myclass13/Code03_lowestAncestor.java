package class13.myclass13;

public class Code03_lowestAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 给定两个树中的节点A,B，求出这两个节点往上过程中第一次交汇的节点
     * 某树中可行性分析：
     * ①左/右某个子树中已经找到了节点AB，此时交汇节点在子树中，返回子树的交汇节点
     * ②左右子树各找到了其中一个节点，此树为交汇点
     * ③左/右子某个树只找到了其中一个节点，此树head为另一个节点，此树为交汇点
     * <p>
     * 递归信息体：
     * ①左右子树是否找到了AB
     * ②交汇节点
     */
    public class Info {
        public boolean ifFindA;
        public boolean ifFindB;
        public Node meetNode;

        public Info(boolean ifFindA, boolean ifFindB, Node meetNode) {
            this.ifFindA = ifFindA;
            this.ifFindB = ifFindB;
            this.meetNode = meetNode;
        }
    }

    public Info lowestAncestor(Node head, Node A, Node B) {
        if (head == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = lowestAncestor(head.left, A, B);
        Info rightInfo = lowestAncestor(head.right, A, B);
        boolean ifFindA = head == A || leftInfo.ifFindA || rightInfo.ifFindA;
        boolean ifFindB = head == B || leftInfo.ifFindB || rightInfo.ifFindB;
        Node meetNode = null;
        if (leftInfo.meetNode != null) {
            meetNode = leftInfo.meetNode;
        } else if (rightInfo.meetNode != null) {
            meetNode = rightInfo.meetNode;
        } else if (ifFindA && ifFindB) {
            meetNode = head;
        }
        return new Info(ifFindA, ifFindB, meetNode);
    }
}
