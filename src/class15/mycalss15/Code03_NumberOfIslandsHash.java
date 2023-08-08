package class15.mycalss15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code03_NumberOfIslandsHash {
    /**
     * 岛问题（递归解法 + 并查集解法 + 并行解法）
     * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量
     * <p>
     * 并查集解法：HashMap
     * 老三样:
     * NodeMap保存每个1的位置信息
     * ParentMap保存每个1的集合的代表节点
     * SizeMap保存每个集合的大小
     * 我们可以为每个1new一个空类Point，根据原本的matrix二维数组，组装一个一模一样形状的Point数组，由于每个Point都是不用的内存地址，就区分开了每个1
     * 遍历matrix数组，只要是1的位置，将Point数组中相同位置的Point加入到List里面。遍历之后，List的POint就是matrix中为1的位置
     * 将List初始化FindUnion，老三样初始化，每个Point为一个集合，parent为自己，大小为1
     * <p>
     * union调用时机：
     * 重新遍历matrix数组，除去数组的（0，0）位置，从（0，1）开始，如果该点的左和上是1，那么就将该点归于左和上集合中
     * 也就是对应Point归并
     */

    // 老三样
    public class Node<V> {
        public V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public class FindUnion<V> {
        private HashMap<V, Node<V>> nodeHashMap;
        private HashMap<Node<V>, Node<V>> parentHasMap;
        private HashMap<Node<V>, Integer> sizeHashMap;

        public FindUnion(List<V> list) {
            nodeHashMap = new HashMap<>();
            parentHasMap = new HashMap<>();
            sizeHashMap = new HashMap<>();
            for (V v : list) {
                Node<V> node = new Node<>(v);
                nodeHashMap.put(v, node);
                parentHasMap.put(node, node);
                sizeHashMap.put(node, 1);
            }
        }

        public Node<V> findParent(Node<V> node) {
            Stack<Node<V>> stack = new Stack<>();
            while (node != parentHasMap.get(node)) {
                stack.push(node);
                node = parentHasMap.get(node);
            }
            while (!stack.isEmpty()) {
                parentHasMap.put(stack.pop(), node);
            }
            return node;
        }

        public void union(V v1, V v2) {
            Node<V> v1Parent = findParent(nodeHashMap.get(v1));
            Node<V> v2Parent = findParent(nodeHashMap.get(v2));
            if (v1Parent != v2Parent) {
                Integer v1Size = sizeHashMap.get(v1Parent);
                Integer v2Size = sizeHashMap.get(v2Parent);
                Node<V> bigger = v1Size >= v2Size ? v1Parent : v2Parent;
                Node<V> smaller = bigger == v1Parent ? v2Parent : v1Parent;
                parentHasMap.put(smaller, bigger);
                sizeHashMap.put(bigger, v1Size + v2Size);
                sizeHashMap.remove(smaller);
            }
        }

        public int howManySets() {
            return sizeHashMap.size();
        }
    }

    // 代替1的空类，只使用他的地址
    public class Point {
        public Point() {
        }
    }

    // 开始逐行遍历二维数组
    public int start(char[][] matrix) {
        int hangShu = matrix.length;
        int lieShu = matrix[0].length;
        // 空数组,形状对应matrix
        Point[][] points = new Point[hangShu][lieShu];
        // 用来将1对应的Point初始化到FindUnion
        ArrayList<Point> list = new ArrayList<>();
        for (int i = 0; i < hangShu; i++) {
            for (int j = 0; j < lieShu; j++) {
                if (matrix[i][j] == '1') {
                    // points数组此时才真正newPoint
                    points[i][j] = new Point();
                    list.add(points[i][j]);
                }
            }
        }
        // 遍历完之后，points数组中有Point的地方对应matrix中有1的地方,且这些Point已经初始化在FindUnion的HashMap中
        FindUnion<Point> findUnion = new FindUnion<>(list);
        // 开始遍历points数组
        // 分三次遍历，因为：第一行的树只有左没有上，第一列只有上没有左，其他的行和列既有左也有上
        // 第一行,跳过0，0
        for (int j = 1; j < lieShu; j++) {
            if (matrix[0][j] == '1') {
                // 看他的左边是否为1，是就将points对应位置合并
                if (matrix[0][j - 1] == '1') {
                    findUnion.union(points[0][j - 1], points[0][j]);
                }
            }
        }
        // 第一列，跳过0，0
        for (int i = 1; i < hangShu; i++) {
            if (matrix[i][0] == '1') {
                // 看他的上边是否为1，是就将points对应位置合并
                if (matrix[i - 1][0] == '1') {
                    findUnion.union(points[i - 1][0], points[i][0]);
                }
            }
        }

        // 其他位置,第二行第二列开始
        for (int i = 1; i < hangShu; i++) {
            for (int j = 1; j < lieShu; j++) {
                if (matrix[i][j] == '1') {
                    // 如果上面也是1，合并
                    if (matrix[i - 1][j] == '1') {
                        findUnion.union(points[i - 1][j], points[i][j]);
                    }
                    // 如果左面也是1，合并
                    if (matrix[i][j - 1] == '1') {
                        findUnion.union(points[i][j - 1], points[i][j]);
                    }
                }
            }
        }
        // 合并完成后，unionFind中sizeHashMap的大小就是岛的数量
        return findUnion.howManySets();
    }


}
