package class09.myclass09;

import class09.Code01_LinkedListMid;

import java.util.Stack;

public class Code02_IsPalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }
    /**
     * 给定一个单链表的头节点head，请判断该链表是否为回文结构
     * 回文结构：单链表正着反着遍历的value都一样，①-②-③-②-①-null  ①-②-③-③-②-①-null
     */

    /**
     * 借助于栈：让每个单链表的节点从头开始进栈，然后从栈(非基础类型存储地址值)中弹出的时候就是单链表的倒序。
     * 然后分别head=head.next指向的节点的value跟栈弹出的节点的value比较，全部一样为回文结构
     */
    public boolean isPalindromeStack(Node head) {
        Stack<Node> stack = new Stack<>();
        Node n3 = head;
        while (n3 != null) {
            stack.push(n3);
            n3 = n3.next;
        }
        while (head != null) {
            if (stack.pop().value != head.value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 不借助于任何容器：头指针，尾指针。
     * 找到中间节点，中间节点后本部分反转。
     * 头指针尾指针一起往中间靠，靠一次，比较一次节点值是否相等，不相等立马false
     */
    public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // n1和n2复用
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }
        // 此时n1就是中点/上中点,然后从中点开始单链表反转
        n2 = n1.next;
        n1.next = null;
        Node n3 = null;
        // n2反转，指向n1
        while (n2 != null) {
            n3 = n2.next;
            n2.next = n1;
            // 下一次循环，n1，n2都往后移动一个
            n1 = n2;
            n2 = n3;
        }

        // 反转完成后，n1就应该是最后一个节点(中间节点/上中节点),n2就是null
        // 最后一个节点开始往中间走，因为左右都指向中间节点，所以最多走到中间节点
        // 这里使用n1作为尾指针，n3只是用来将单链表右半部分反转回原本样子的
        n3 = n1;
        // 头节点往后走,因为左右都指向中间节点，所以最多走到中间节点
        n2 = head;
        // 如果循环中检测到不是回文结构，不能直接结束方法，还需要将链表还原回原本样子
        boolean result = true;
        // n2头和n1尾分别开始走。
        // 如果链表是偶数个节点，中间节点是上中点，左半部分提前到null，右半部分只剩下一个与上中节点值相同的节点，不用比较。
        // 如果链表是奇数个节点，都走到中间节点，正常比较到null结束
        while (n1 != null && n2 != null) {
            if (n1.value != n2.value) {
                result = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        // 是回文结构，还原单链表,n3记录的尾节点，n3的下一个开始反转,复用n2,n1
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) {
            // 两两调整
            // n2作为temp
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }

        return result;
    }

}
