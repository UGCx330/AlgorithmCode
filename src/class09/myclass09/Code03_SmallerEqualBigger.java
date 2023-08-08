package class09.myclass09;

public class Code03_SmallerEqualBigger {
    public class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
    /**
     * 给定一个单链表的头节点head，给定一个整数n，将链表按n划分成左边<n、中间==n、右边>n
     */

    /**
     * 准备6个额外空间：
     * sH，sD：小于域的头节点尾节点
     * eH，eD：等于域的头节点尾节点
     * mH，mD：大于域的头节点尾节点
     * 从头遍历节点，将每个节点断下来：
     * 如果是第一个值<n的节点,那么将其挂在sH后面，sD同时指向这个节点。如果不是第一个值<n的节点，那么将其挂在sD后面，sD重新指向这个节点
     * 如果是第一个值=n的节点,那么将其挂在eH后面，eD同时指向这个节点。如果不是第一个值=n的节点，那么将其挂在eD后面，eD重新指向这个节点
     * 如果是第一个值>n的节点,那么将其挂在mH后面，mD同时指向这个节点。如果不是第一个值>n的节点，那么将其挂在mD后面，mD重新指向这个节点
     * <p>
     * 最后将三个区域连接起来
     */
    public Node SmallerEqualBiggerLisD(Node head, int n) {
        Node sH = null;
        Node sD = null;
        Node eH = null;
        Node eD = null;
        Node mH = null;
        Node mD = null;
        Node temp = null;
        while (head != null) {
            temp = head.next;
            // 将节点拿下来
            head.next = null;
            // 判断节点值挂载区域
            if (head.value < n) {
                if (sH == null) {
                    sH.next = head;
                    sD.next = head;
                } else {
                    sD.next = head;
                    sD = head;
                }
            } else if (head.value == n) {
                if (eH == null) {
                    eH.next = head;
                    eD.next = head;
                } else {
                    eD.next = head;
                    eD = head;
                }
            } else {
                if (mH == null) {
                    mH.next = head;
                    mD.next = head;
                } else {
                    mD.next = head;
                    mD = head;
                }
            }
            // 挂载完之后连接
            head = temp;
        }

        /**
         * 核心，步骤依然是上一个区域的尾巴连接下一个区域的头，但是会根据情况调整，但是步骤不变
         */
        // 连接小于区域和等于区域
        if (sD != null) {
            // 有小于区域情况下，小于区域的尾巴连接等于区域的头，无论等于区域是否存在
            sD.next = eH;
            // 如果等于区域存在，下一次连接的尾巴不动
            // 如果等于区域不存在，等于区域的尾巴就是小于区域的尾巴。
            // 等于区域与大于区域连接时，相当于小于区域与大于区域连接
            eD = eD == null ? sD : eD;
        }

        // 等于区域存在(等于跟大于区域连接)或者等于区域不存在而小于区域存在(小于跟大于区域连接),eD连接大于区域的头
        // 这里如果ed==null说明小于和等于区域都不存在
        if (eD != null) {
            eD.next = mH;
        }

        // 上述过程已经将所有情况的各个区域的头尾节点连接起来
        // 哪个区域的头节点不为空直接返回为head即可
        return sH != null ? sH : (eH != null ? eH : mH);


    }

}
