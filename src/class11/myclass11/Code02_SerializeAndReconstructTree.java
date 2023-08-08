package class11.myclass11;

import java.util.LinkedList;
import java.util.Queue;

public class Code02_SerializeAndReconstructTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 二叉树先序遍历，序列化为队列中的一个个元素
     * 序列化的时候只序列化值
     */
    public void headPreSerialize(Node head, Queue<String> queue) {
        if (head == null) {
            queue.add(null);
        } else {
            queue.add(String.valueOf(head.value));
            headPreSerialize(head.left, queue);
            headPreSerialize(head.right, queue);
        }
    }

    /**
     * 二叉树的反序列化，将值从队列中按照先后顺序提取出来，重新new一个个节点。
     * 将节点一个个按照先序遍历的方式形成树
     */
    public Node headPreDeSerialize(Queue<String> queue) {
        String value = queue.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.left = headPreDeSerialize(queue);
        head.right = headPreDeSerialize(queue);
        return head;
    }

    /**
     * 二叉树的按层序列化
     * 准备一个队列，就按照按层遍历二叉树的方式把节点值放入队列中
     */
    public Queue<String> serializeByStorey(Node head) {
        Queue<String> valueQueue = new LinkedList<>();
        if (head == null) {
            valueQueue.add(null);
        } else {
            // 存放节点值
            valueQueue.add(String.valueOf(head.value));
            // 存放树中节点
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                // 弹出一个node
                Node node = nodeQueue.poll();
                // 压入node左右子节点，压入同时记录下值到队列中.
                // 这样，如果不是最后一行的时候某个左节点为null，那么记录值为null，但是不压入node队列，下一次直接弹出右节点
                // 弹出最后一行节点的时候，左右为null，不压入，但是存储值null
                // 记录null值是必要的，因为可以通过null值反序列化的时候判断该节点是否存在
                if (node.left != null) {
                    nodeQueue.add(node.left);
                    valueQueue.add(String.valueOf(node.left.value));
                } else {
                    valueQueue.add(null);
                }
                if (node.right != null) {
                    nodeQueue.add(node.right);
                    valueQueue.add(String.valueOf(node.right.value));
                } else {
                    valueQueue.add(null);
                }
            }
        }
        return valueQueue;
    }

    /**
     * 二叉树的按层反序列化
     */
    public Node deSerializeByStorey(Queue<String> valueQueue) {
        if (valueQueue == null || valueQueue.size() == 0) {
            return null;
        }
        // 存放值转为node的node
        Queue<Node> nodeQueue = new LinkedList<>();
        Node head = new Node(Integer.parseInt(valueQueue.poll()));
        nodeQueue.add(head);
        while (!nodeQueue.isEmpty()) {
            // 弹出一个节点从node队列中，按照value队列中的值创造出该弹出节点左右两个节点压入node队列。循环直到node队列为空
            Node node = nodeQueue.poll();
            // node队列弹出的节点，其左右子树是否为null取决于value队列中的值，值为null左右子节点就是null
            node.left = ifNullNode(valueQueue.poll());
            if (node.left != null) {
                // 节点的左节点被赋值后发现不是null，压入node队列，value队列中肯定有与之对应的value
                nodeQueue.add(node.left);
            }
            // 节点的左节点被赋值后发现是null，不压入node队列，直接看右节点
            node.right = ifNullNode(valueQueue.poll());
            if (node.right != null) {
                nodeQueue.add(node.right);
            }
        }
        return head;
    }

    public Node ifNullNode(String value) {
        if (value == null) {
            return null;
        }
        return new Node(Integer.parseInt(value));
    }

}
