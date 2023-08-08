package class16.myclass16;

import java.util.*;

/**
 * 最小生成树之K算法--无向图
 * 从给定的图(自定义的)中，有点有线，线有权值。
 * 找出所有能联通点的线，且要求权值最小
 * 解法：线有from和to节点。
 * 并查集：每个点都是一个独立集合
 * 贪心策略，每次拿最小权值的线，并且将两头的节点归为一个集合
 * 如果线的两个节点已经在一个集合中了，说明已经有路联通了，跳过此线，否则就会形成环
 */
public class Code07_Kruskal {
    public class UnionFind {
        private HashMap<Node, Node> parentMap;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(ArrayList<Node> list) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : list) {
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findParent(Node node) {
            Stack<Node> stack = new Stack<>();
            while (node != parentMap.get(node)) {
                stack.add(node);
                node = parentMap.get(node);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), node);
            }
            return node;
        }

        public void union(Node node1, Node node2) {
            if (node1 == null || node2 == null) {
                return;
            }
            Node parent1 = findParent(node1);
            Node parent2 = findParent(node2);
            if (parent1 != parent2) {
                Integer size1 = sizeMap.get(parent1);
                Integer size2 = sizeMap.get(parent2);
                Node bigger = size1 >= size2 ? parent1 : parent2;
                Node smaller = bigger == parent1 ? parent2 : parent1;
                parentMap.put(smaller, bigger);
                sizeMap.put(bigger, size1 + size2);
                sizeMap.remove(smaller);
            }
        }

        public boolean isSameCollection(Node node1, Node node2) {
            return findParent(node1) == findParent(node2);
        }

        // 按线的权值排序
        public class leastComparator implements Comparator<Edge> {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        }

        public Set<Edge> kruskalLeastTree(Graph graph) {
            PriorityQueue<Edge> heap = new PriorityQueue<>(new leastComparator());
            ArrayList<Node> list = new ArrayList<>();
            HashSet<Edge> ans = new HashSet<>();
            for (Node node : graph.nodeHashMap.values()) {
                list.add(node);
            }
            UnionFind unionFind = new UnionFind(list);
            for (Edge edge : graph.edgeHashSet) {
                heap.add(edge);
            }
            while (!heap.isEmpty()) {
                Edge edge = heap.poll();
                Node from = edge.fromNode;
                Node to = edge.toNode;
                if (!unionFind.isSameCollection(from, to)) {
                    ans.add(edge);
                    unionFind.union(from, to);
                }
            }
            return ans;
        }
    }
}
