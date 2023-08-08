package class16.myclass16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 拓扑排序：有向无环图的排序
 * 最先指向别的节点的那些节点们就在前面，换句话说，没有入线的那些点在前面
 * 条件：只给出点类，类中有属性为指向的点List
 * 邻居节点：每个节点指向的那些节点
 * 解法1：
 * 对于每个点，除了最开头的点之外。肯定是有别的点指向它的。
 * 遍历每个点，只要他们指向别的点，别的点的被指数就+1.（HashMap中完成）
 * 然后找出被指数为0的，就是最开头的点
 * 然后最开头这些点加入到ans(返回答案中)，并准备一个队列，加入到队列中
 * 随后从队列中循环弹出一个点，将他们指向的邻居节点被指数-1，立即检查map中这些邻居节点的被指数是否为0，为0说明不被指向了，加入到队列中
 * 如果染出第一个节点后邻居节点被指数不为0，说明可能还有别的一开始的点指向，把那些点弹出的时候邻居节点的被指数-1，就可能为0了，就可以加入到队列中了
 */
public class Code03_TopologicalOrderBFS_BeiZhiShu {
    public class Node {
        public int value;
        public ArrayList<Node> nextNodeList;

        public Node(int value) {
            this.value = value;
            nextNodeList = new ArrayList<>();
        }
    }

    public ArrayList<Node> beiZhiShuJianYi(ArrayList<Node> nodeList) {
        // 排好序返回的答案
        ArrayList<Node> ans = new ArrayList<>();
        // 节点和被指数
        HashMap<Node, Integer> map = new HashMap<>();
        // 先将每个节点作为key保存一下，当然此时被指数为0
        for (Node node : nodeList) {
            map.put(node, 0);
        }
        // 将每个节点的被指数都过一遍,得出每个节点最全的被指数
        for (Node node : nodeList) {
            for (Node node1 : node.nextNodeList) {
                map.put(node1, map.get(node1) + 1);
            }
        }
        // 找出被指数为0的就是开头节点,也就是需要排在最前面的节点,并且将这些节点放在队列中
        LinkedList<Node> queue = new LinkedList<>();
        for (Node node : map.keySet()) {
            if (map.get(node) == 0) {
                queue.add(node);
            }
        }
        // 开始弹出，加入到ans，并且所有邻居节点的被指数-1，随后判断邻居节点的被指数为0进队列,说明是第二级节点，不为0不做任何处理
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            ans.add(node);
            for (Node node1 : node.nextNodeList) {
                map.put(node1, map.get(node1) - 1);
                if (map.get(node1) == 0) {
                    queue.offer(node1);
                }
            }
        }

        return ans;
    }
}
