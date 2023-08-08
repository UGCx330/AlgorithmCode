package class16.myclass16;

import java.util.ArrayList;
import java.util.List;

/**
 * 点-组成图的基本要素之一
 */
public class Node {
    // 值
    public int value;
    // 入线的数量
    public int inEdgeNums;
    // 出线的数量
    public int outEdgeNums;
    // 出线的集合
    public List<Edge> edgeList;
    // 指向的节点的集合
    public List<Node> nextNodeList;

    public Node(int value) {
        this.value = value;
        edgeList = new ArrayList<>();
        nextNodeList = new ArrayList<>();
    }
}
