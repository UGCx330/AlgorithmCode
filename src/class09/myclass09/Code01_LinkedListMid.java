package class09.myclass09;

public class Code01_LinkedListMid {
    public class Node {
        private Node next;
        private Integer value;

        public Node(Integer value) {
            this.value = value;
        }
    }

    /**
     * 输入链表头节点----奇数长度返回中点----偶数长度返回上中点
     * <p>
     * 准备两个Node类型的快慢指针，慢指针指在head节点的下一个，也就是2号节点，快指针指在3号节点
     * 循环，每次慢指针走一部，快指针走两步，这样快指针由于每一次都会跨一个节点，所以快指针的next或者next.next为null的时候。
     * 快指针越过的节点就是一半的数量，而这些数量就是慢指针走过的数量，也就是最后慢指针的位置
     * <p>
     * 对于奇数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next(或者head.next)，则最后fast.next.next！=null(或者head.fast.next.next==null)，快指针停在最后一个节点(或者倒数第二个节点)，此时慢指针都会指向中点
     * 对于偶数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next，则最后fast.next.next==null，快指针停在倒数第二个节点，此时慢指针指向上中点
     */
    public Node middleNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            // 2个或者以内返回头节点
            return head;
        }
        // 3个节点及其以上
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点---奇数长度返回中点---偶数长度返回下中点
     * <p>
     * 对于奇数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next(或者head.next)，则最后fast.next.next！=null(或者head.fast.next.next==null)，快指针停在最后一个节点(或者倒数第二个节点)，此时慢指针都会指向中点
     * 对于偶数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next，则最后fast.next.next==null，快指针停在倒数第二个节点，此时慢指针指向上中点
     * <p>
     * 要想指向下中点，而奇数情况下的不动，就需不改变慢指针一开始指向情况下，使得偶数情况下的慢指针多走一步，那么fast指针一开始应该在fast.next位置，这样最后fast.next.next!=null，偶数情况下快指针多走一步，慢指针也会多走一步，指向下中点
     */
    public Node middleNode2(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点---奇数长度返回中点前一个---偶数长度返回上中点前一个
     * <p>
     * 对于奇数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next(或者head.next)，则最后fast.next.next！=null(或者head.fast.next.next==null)，快指针停在最后一个节点(或者倒数第二个节点)，此时慢指针都会指向中点
     * 对于偶数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next，则最后fast.next.next==null，快指针停在倒数第二个节点，此时慢指针指向上中点
     * <p>
     * 如果奇数和偶数都需要返回前一个，那么就是一开始慢指针的指向往前一位了
     */
    public Node middleNode3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     * <p>
     * 对于奇数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next(或者head.next)，则最后fast.next.next！=null(或者head.fast.next.next==null)，快指针停在最后一个节点(或者倒数第二个节点)，此时慢指针都会指向中点
     * 对于偶数长度来说，如果一开始慢指针在heat.next,快指针在head.next.next，则最后fast.next.next==null，快指针停在倒数第二个节点，此时慢指针指向上中点
     * <p>
     * ①都返回中点前一个，slow一开始的指向就需要往前一位。
     * ②偶数长度返回下中点前一个，也就是上中点，就需要偶数情况下的快指针多走一步，fast=head.next
     */
    public Node middleNode4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
