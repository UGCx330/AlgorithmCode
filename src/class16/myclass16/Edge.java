package class16.myclass16;

/**
 * 线-组成图的基本要素之一
 */
public class Edge {
    // 权重-长度
    public int weight;
    // 线的从节点
    public Node fromNode;
    // 线的去节点
    public Node toNode;

    public Edge(int weight, Node fromNode, Node toNode) {
        this.weight = weight;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

}
