package class16.myclass16;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图
 * 一张图由点和线组成
 */
public class Graph {
    // 点带编号
    public HashMap<Integer, Node> nodeHashMap;
    public HashSet<Edge> edgeHashSet;

    public Graph() {
        nodeHashMap = new HashMap<>();
        edgeHashSet = new HashSet<>();
    }
}
