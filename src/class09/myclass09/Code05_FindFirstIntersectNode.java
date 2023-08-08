package class09.myclass09;

import class10.Code01_FindFirstIntersectNode;

public class Code05_FindFirstIntersectNode {
    public class Node {
        private int value;
        private Node next;

        public Node(Integer value) {
            this.value = value;
        }
    }
    /**
     * 给定两个可能有环也可能无环的单链表，头节点head1和head2
     * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交返回null
     * 要求如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     *
     * 两个链表都无环---可以相交，Y字型
     * 两个链表都无环---可以不相交，Y字型
     * 一个有环一个无环---不可能相交
     * 两个都有环---可以相交，并且交点可以在环内
     * 两个都有环---可以相交，并且交点可以在环外或者是入环节点
     * 两个都有环---可以不相交
     *
     * 即先判断两个链表的有无环情况
     * 如果都无环：如果相交那么交点一定在  最后一个节点及其往上到比较短的那个链表的头节点集合中
     *          遍历两个链表，记下各自长度，长的减去短的就是长度差值，先让长链表遍历完这些差值，然后两个链表同时往下走判断节点是否相等，相等就意味着相交
     *          如果不相交，遍历长度时，都走到最后一个节点后，两个最后节点一定不一样。
     * 如果一个有环一个无环：直接return null。
     * 如果两个都有环，返回各自入环节点。
     * 如果入环节点相等：说明如果相交那么相交位置在入环节点之前或者就是入环节点--环外。
     *               那么就去掉环跟无环一样处理，分别遍历两个链表直到入环节点，记下长度，长的减短的得到差值，长的先走完差值。
     *               然后两个链表一起走到入环节点位置，有相等的节点说明相交，无相等的节点说明不相交。
     * 如果入环节点不相等：说明如果相交那么相交位置在--环内，且两个链表的入环节点都是相交节点，返回任意一个就行。
     *                 即选任意一个链表的环的入环节点开始1步往前走，如果绕环一圈，没有碰到第二个链表的入环节点，说明两个有环链表不相交
     *                 如果碰到了，说明两个有环链表相交，返回任意一个入环节点作为相交界点。
     */

    /**
     * 对于某个单链表来说，如果有环，返回入环的第一个节点
     * 如果无环，返回null
     * <p>
     * 判断方法：快慢指针，快指针一次走两步，慢指针一次走一步。
     * 当快指针追上慢指针时，快指针返回head，继续以每次1步的速度追慢指针，下一次相遇的节点就是入环的节点
     */
    public Node ifLoop(Node head) {
        // 先让快慢指针都走一次，再走之前判断是否相遇(循环条件)
        // 就需要判断前三个节点是否为null
        if (head == null && head.next == null && head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head.next;
        while (fast != slow) {// 走之前判断是否相遇
            if (fast.next == null && fast.next.next == null) {
                // 此时说明无环
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        // 正常结束循，说明有环，快慢指针第一次相遇，然后快指针回到head，速度变成1步后继续追
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        // 结束循环，此时快慢指针相遇停留的地方就是入环节点
        return slow;
    }

    /**
     * 如果两个都无环
     */
    public Node bothNoLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        // 暂时这样赋值，后面复用
        Node longStart = head1;
        Node shortStart = head2;
        // 长度,后面复用
        int n = 0;
        while (longStart.next != null) {
            n++;
            longStart = longStart.next;
        }
        while (shortStart.next != null) {
            n--;
            shortStart = shortStart.next;
        }
        // 如果两个链表的end节点不一样说明不相交
        if (longStart != shortStart) {
            return null;
        }
        // longStart重定向长链表头节点，shortStart重定向短链表头节点
        longStart = n > 0 ? head1 : head2;
        shortStart = longStart == head1 ? head2 : head1;
        // 长链表走差值
        n = Math.abs(n);// 绝对值
        while (n != 0) {
            n--;
            longStart = longStart.next;
        }
        // 两个链表同时走,肯定有相同节点，终止循环返回
        while (shortStart != longStart) {
            shortStart = shortStart.next;
            longStart = longStart.next;
        }

        return longStart;
    }

    /**
     * 两个都有环
     */
    public Node bothLoop(Node head1, Node head2, Node inLoop1, Node inLoop2) {
        Node longStart = null;
        Node shortStart = null;
        // 入环节点相等,如果相交交点比在环外，入环点相当于end
        if (inLoop1 == inLoop2) {
            longStart = head1;
            shortStart = head2;
            int n = 0;
            // 走到入环节点位置上为止
            while (longStart != inLoop1) {
                n++;
                longStart = longStart.next;
            }
            while (shortStart != inLoop2) {
                n--;
                shortStart = shortStart.next;
            }
            longStart = n > 0 ? head1 : head2;
            shortStart = longStart == head1 ? head2 : head1;
            n = Math.abs(n);
            // 长链表先走差值步
            while (n != 0) {
                n--;
                longStart = longStart.next;
            }
            // 两个链表同时走,肯定有相同节点，终止循环返回
            while (shortStart != longStart) {
                longStart = longStart.next;
                shortStart = shortStart.next;
            }
            return longStart;
        }
        // 入环节点不等，如果相交交点必在环内，两个入环节点追赶上说明相交，入环节点追赶不上说明不相交
        else {
            // 复用变量为入环节点下一个节点
            longStart = inLoop1.next;
            // 转一圈过程中
            while (longStart != inLoop1) {
                // 如果追上了第二个链表的入环节点，说明相交，返回任意一个入环节点即可
                if (longStart == inLoop2) {
                    return inLoop1;
                }
                longStart = longStart.next;
            }
            // 如果转完一圈了，说明链表1的圈内没有链表2的入环节点，不相交
            return null;
        }
    }

    public Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = ifLoop(head1);
        Node loop2 = ifLoop(head2);
        if (loop1 == null && loop2 == null) {
            return bothNoLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

}
