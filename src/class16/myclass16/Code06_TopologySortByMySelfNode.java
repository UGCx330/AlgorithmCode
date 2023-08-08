package class16.myclass16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 自定义的图结构实现拓扑排序
 * 因为自定义的点结构是有入度的，也就相当于被指数，所以直接省略了循环遍历每个节点的邻居节点并更新map的过程
 * 因为Node类自带int 被指数
 * 其他部分一样
 * 遍历每个节点找到入读为0的节点就是排在最前面的节点
 * 压入队列，弹出，记录到ans，将邻居节点的入度都-1，然后看所有节点是否有新的入度为0的节点，有就压入，没有继续弹出。
 */
public class Code06_TopologySortByMySelfNode {
    public ArrayList<Node> mySelfNodeByRuDu(Graph graph) {
        // 节点和入度
        HashMap<Node, Integer> map = new HashMap<>();
        ArrayList<Node> ans = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();
        for (Node node : graph.nodeHashMap.values()) {
            map.put(node, node.inEdgeNums);
            if (node.inEdgeNums == 0) {
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            //弹出一个加入到答案中
            ans.add(node);
            for (Node node1 : node.nextNodeList) {
                map.put(node1, node1.inEdgeNums - 1);
                if (map.get(node1) == 0) {
                    queue.add(node1);
                }
            }
        }
        return ans;
    }
}
