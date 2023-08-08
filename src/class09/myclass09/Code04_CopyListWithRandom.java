package class09.myclass09;

import java.util.HashMap;

public class Code04_CopyListWithRandom {
    public class Node {
        public int value;
        public Node next;
        public Node random;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 一种特殊的单链表节点类描述如下
     * class Node {
     * int value;
     * Node next;
     * Node rand;
     * Node(int val) { value = val; }
     * }
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
     * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制
     * 返回复制的新链表的头节点，要求时间复杂度O(N)，额外空间复杂度O(1)
     */

    /**
     * 使用容器方式：使用map，键为遍历原链表从前往后的节点地址变量，值为复制的节点
     * 并且要先都复制一遍单节点
     * 然后再遍历map，取出复制节点，将复制节点作为操作节点，进行next指针和random指针的赋值(这两个指针指向的都应该是复制节点)。
     * 即不要改变原有节点，全部操作复制节点
     */
    public Node copyListWithRandomMap(Node head) {
        // 复制节点到map中--作为map的值
        Node start = head;
        HashMap<Node, Node> nodeMap = new HashMap<>();
        while (start != null) {
            nodeMap.put(start, new Node(start.value));
            start = start.next;
        }

        // 进行复制节点之间的关系链接
        start = head;
        while (start != null) {
            nodeMap.get(start).next = nodeMap.get(start.next);
            nodeMap.get(start).random = nodeMap.get(start.random);
            start = start.next;
        }

        return nodeMap.get(head);
    }

    /**
     * 不使用容器
     * 在原链表的相邻节点中间插入一个上节点的复制节点如：③->⑤变成③->③'->⑤，一定要循环遍历完
     * <p>
     * 再从头开始，此时两两一组(原节点和复制节点)，根据原节点的关系调整复制节点的关系。
     * 原节点random指针指向的位置的下一个就是复制节点random指针应该指向的位置
     * <p>
     * 最后将复制节点与原节点断开
     */
    public Node copyListWithRandom(Node head) {
        Node start = head;
        // 因为会改变原节点的next为复制节点，所以需要临时变量next记录一下下一个原节点，进行循环
        Node next = null;
        while (start != null) {
            next = start.next;
            start.next = new Node(start.value);
            start.next.next = next;
            start = next;
        }

        // 调整复制节点的random指针指向
        start = head;
        while (start != null) {
            // 跳过复制节点
            next = start.next.next;
            // 复制节点的random复制为原节点的random的下一个(复制节点)
            start.next.random = start.random == null ? null : start.random.next;
            start = next;
        }

        // 将复制节点与原链表分离开，即恢复原节点下一个指向为下一个原节点不再是复制节点
        // 最终要返回的复制节点的head
        Node result = head.next;
        start = head;
        while (start != null) {
            next = start.next.next;
            // 复制节点指向下一个复制节点
            start.next.next = next != null ? next.next : null;
            // 原节点指向下一个原节点
            start.next = next;
            start = next;
        }

        return result;

    }
}
