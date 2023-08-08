package class16.myclass16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的宽度优先遍历
 * 需要一个队列用于点的进出，弹出节点的时候打印节点
 * 由于图可能一个节点被多个节点指向而导致多次遍历
 * 所以还需要一个HashSet用于检测该节点是否已经被遍历
 */
public class Code01_BFS {
    public void BFS(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(head);
        set.add(head);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.println(current.value);
            for (Node node : current.nextNodeList) {
                if (!set.contains(node)) {
                    set.add(node);
                    queue.add(node);
                }
            }
        }
    }
}
