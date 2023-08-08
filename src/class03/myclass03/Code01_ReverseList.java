package class03.myclass03;

public class Code01_ReverseList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }
    }

    // 反转单链表
    public Node reverseList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {// 循环至最后一个节点，至当前节点不为null

            // 将当前节点的next存储起来
            next = head.next;
            // 反转当前节点的下一个节点
            head.next = pre;
            // 更新反转节点
            pre = head;
            // 去下一个节点,循环至最后此处为null，所以最后应该返回pre
            head = next;
        }
        return pre;
    }

    // 反转双链表
    public DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {// 循环至最后一个节点，至当前节点不为null
            // 先记录下一个节点
            next = head.next;
            // 修改当前节点的下一个节点为pre
            head.next = pre;
            // 修改当前节点的上一个节点为next（记录的当前节点下一节点）
            head.last = next;
            // 更新pre为当前节点
            pre = head;
            // 去下一个节点,循环至最后此处为null，所以最后应该返回pre
            head = next;
        }
        return pre;
    }

    // 从单链表中删除某数据
    public Node removeDate(Node head, int num) {
        // 从头节点开始对比，找到值不为num的节点作为算法起始节点
        while (head != null) {
            if (head.value != num) {
                break;
            }

            head = head.next;
        }

        // 找到不需要删除的节点作为新的头节点后
        // pre用于将删除前的上一个节点的next指向删除后的current,然后current变更为下一个节点看是否需要删除，直到current为null。
        Node pre = head;
        Node current = head;
        while (current != null) {
            if (current.value == num) {// 将上一个节点的next赋值为当前节点(current)的下一个节点,pre不变
                pre.next = current.next;
            } else {
                // 当前节点不必删除
                // pre更新为当前节点
                pre = current;
            }
            // 去下一个节点
            current = current.next;
        }
        return head;
    }

}
