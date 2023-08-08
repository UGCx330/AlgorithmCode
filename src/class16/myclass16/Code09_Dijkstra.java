package class16.myclass16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 最小生成树之D算法--无向图
 * 从给定的图(自定义的)中，有点有线，线有权值。
 * 找出所有能联通点的线，且要求权值最小
 * 解法：
 * 贪心策略：
 * 使得一个点，到其他点的距离从最开始的正无穷
 * 不断直接或间接通过指向节点更新为更小的值
 * 最终得到的某点去其他点的长度就是最小的
 * 返回值为map,为开始节点到其他各个节点的距离
 * PS：
 * 对于任意一个点A来说，他的距离要被更小的距离更新，那么在更新这个距离之前，这个点 绝 对 不 会 被再次使用去添加他的邻居节点距离，导致更新A距离之后邻居节点的距离全部错误。
 * 这种错误是不会发生的，因为每次都是从map中拿距离最小的开始,如果map中已经存储了A节点的距离，那么后续要更新它的距离的那些节点的距离+线的权值<A节点的距离
 * 也就是说那些节点的距离在map中肯定是小于A点在map中的距离的
 * 所以肯定是先使用那些点，更新A点，之后再使用A点。
 * 换句话说，每次从map中取最小距离的点使用，就是因为不会再有其他之后再能更新它的距离了，因为它此时就是最小距离了在map的所有点中！
 * 任何一个点要被使用的时候，只可能存在三种情况：
 * ①引出其他新点，直接添加到map中等待被使用
 * ②更新map中未被使用的点的距离（距离比他更小的点肯定在他之后被使用啊）
 * ③引出的点已经被使用过了(距离比他更小的点肯定在他之前被使用啊)，忽略该点。
 */
public class Code09_Dijkstra {
    public HashMap<Node, Integer> dijkstra(Node head) {
        // 到各个节点的距离
        HashMap<Node, Integer> map = new HashMap<>();
        // 从头节点到某节点的距离已更新，将某节点及距离加入set,
        HashSet<Node> set = new HashSet<>();
        // 规定自到自己距离为0,此时还未使用head节点,所以head节点就相当于此时未使用过的距离最小的节点
        map.put(head, 0);
        Node minNode = head;
        while (minNode != null) {
            // 邻居节点距离=点自带的map中存储的距离+线的权值
            // 如果此邻居节点已经在set中了，距离更小就更新map。
            // 如果邻居节点没有在set中，直接将此节点加入set，将邻居节点和距离新加到map。
            for (Edge edge : minNode.edgeList) {
                // 在set中就一定在map中
                if (set.contains(edge.toNode)) {
                    int newDistance = map.get(minNode) + edge.weight;
                    int oldDistance = map.get(edge.toNode);
                    if (newDistance < oldDistance) {
                        map.put(edge.toNode, newDistance);
                    }
                } else {
                    // 邻居节点不在set中，加入新距离和邻居节点到map，并且此节点标记为已经被使用加入到set
                    map.put(edge.toNode, map.get(minNode) + edge.weight);
                }
                // 标记此节点已被使用
                set.add(minNode);
            }
            minNode = notInSetAndLeastDistanceNode(map, set);
        }
        return map;
    }

    // 从已经更新了距离，但是没有被使用过的点中取出一个距离最小的点
    public Node notInSetAndLeastDistanceNode(HashMap<Node, Integer> map, HashSet<Node> set) {
        int minDistance = Integer.MAX_VALUE;
        Node minUnUsedNode = null;
        for (Map.Entry<Node, Integer> entry : map.entrySet()) {
            Node node = entry.getKey();
            Integer distance = entry.getValue();
            if (!set.contains(node)) {
                if (distance < minDistance) {
                    minDistance = distance;
                    minUnUsedNode = node;
                }
            }
        }
        return minUnUsedNode;
    }
}
