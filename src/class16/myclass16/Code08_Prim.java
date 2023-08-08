
package class16.myclass16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * 最小生成树之P算法--无向图
 * 从给定的图(自定义的)中，有点有线，线有权值。
 * 找出所有能联通点的线，且要求权值最小
 * 解法：
 * 从虚无的线开始解锁任意一个节点(加入set)。
 * 然后从任意节点解锁它指向的所有线（加入小根堆）
 * 从这些线中取权值最小的(弹出),并且解锁其指向的节点（加入set）
 * 如果set中已经有这个节点了，则此线不需要
 * 继续弹出最小的线，解锁点，如果这个点没有在set中，则将点所指向的线加入小根堆。
 * 继续弹出最小的线...
 */
public class Code08_Prim {
    public class leastComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public ArrayList<Edge> primLeastTree(Graph graph) {
        ArrayList<Edge> ans = new ArrayList<>();
        ///放线
        PriorityQueue<Edge> heap = new PriorityQueue<>(new leastComparator());
        // 放点
        HashSet<Node> set = new HashSet<>();
        // 从任意节点开始，第一次循环过后可以直接break，如果是森林结构（多个图），则不能break
        for (Node node : graph.nodeHashMap.values()) {
            if (!set.contains(node)) {
                set.add(node);
                for (Edge edge : node.edgeList) {
                    heap.add(edge);
                }
                while (!heap.isEmpty()) {
                    // 弹出线
                    Edge edge = heap.poll();
                    // 解锁to点
                    Node toNode = edge.toNode;
                    if (!set.contains(toNode)) {
                        // 加入答案
                        ans.add(edge);
                        // 放入set，解锁线，放入heap
                        set.add(toNode);
                        for (Edge edge1 : toNode.edgeList) {
                            heap.add(edge1);
                        }
                    }
                }
            }
            // 如果有森林结构，必须遍历所有节点不能break
            break;
        }
        return ans;
    }
}
