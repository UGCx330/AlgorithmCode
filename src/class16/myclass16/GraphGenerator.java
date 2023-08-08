package class16.myclass16;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 给定一个二维数组matrix表示的图，将其转化为自己的图结构，然后做算法题
 * 数组每行--[线的权值，线的from节点的值，线的to节点的值]
 * 转化思路：
 * new 线，同时new两头节点
 * 当from节点存在于map中时，出线++
 * 当to节点存在于map中时，入线++
 * 如果两头节点都不在map，给节点编制代号，出入线=1，from节点的线list加入此线
 */
public class GraphGenerator {
    public Graph transformer(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // 将节点值作为编号,值相同节点默认为一个节点
            int quanZhi = matrix[i][0];
            int fromValue = matrix[i][1];
            int toValue = matrix[i][2];
            // 如果图中没有，新new一个点add入图，其他属性的处理跟图中有一样处理，后续统一处理
            if (!graph.nodeHashMap.containsKey(fromValue)) {
                graph.nodeHashMap.put(fromValue, new Node(fromValue));
            }
            if (!graph.nodeHashMap.containsKey(toValue)) {
                graph.nodeHashMap.put(fromValue, new Node(toValue));
            }
            // 处理其他属性
            Node fromNode = graph.nodeHashMap.get(fromValue);
            Node toNode = graph.nodeHashMap.get(toValue);
            // new 线
            Edge edge = new Edge(quanZhi, fromNode, toNode);

            // 单纯两个点之间不可能有两条线
            // 所以换句话来说，新线的from和to如果都存在于map中，那么这两个节点之前一定没有线直接连接
            fromNode.nextNodeList.add(toNode);
            fromNode.outEdgeNums++;
            toNode.inEdgeNums++;
            fromNode.edgeList.add(edge);
            graph.edgeHashSet.add(edge);
        }
        return graph;
    }
}
