package class14.myclass14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {
    /**
     * 现在有一组数，刚开始这些数每个都是一个小集合。
     * 现在要求有两个方法
     * ①将两个小集合内中的数合并到一个集合
     * ②判断某两个数是否在一个小集合内
     * 集合的表示不限
     * <p>
     * 解决方案：
     * ①新建一个nodeMap，将value这些数包起来一层成为Node，然后value与node一一对应
     * ②新建一个parentMap，一开始存储的都是node1-node1，即每一个node所在的集合就是本身，当node2与node1合并时，node2--node1，就代表node2在node1集合中
     * 一个集合选择一个node作为代表，如果node3-node2，node2-node1，node1-node1 ，说明node1就是集合node1的代表，node3和2都在集合node1中。
     * ③新建一个sizeMap，存储的是每个集合的大小，一开始肯定每个nodex-1，即本身node一个，如②中的node1大小就是3
     * <p>
     * 两个方法：
     * ①判断两个node是否是同一个集合方法：
     * 从parentMap中判断两个node的最终father看是不是一个node即可
     * ②合并方法：即将任意两个Node的集合合并为一个集合
     * 原则：小的集合parent为大的集合
     * 将小集合的代表node的parent改变成大集合的node代表即可。
     * 然后从size表中删除小集合，并且更新大集合的size
     * <p>
     * 优化点：
     * 每次调用查询一个node的father的方法的时候，我们用一个栈，将从node开始，它的在parentMap中的依此往上的parent压入栈中，直到该集合的代表节点停止。
     * 然后将代表节点弹出，剩下的栈中所有节点依此弹出，并且弹出的时候更新其在parentMap中的parent为此集合的代表节点
     * 这样就将parentMap中的长链变O(1)级别了。
     */
    public class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public class UnionFindV<V> {
        public HashMap<V, Node<V>> nodeHashMap;
        public HashMap<Node<V>, Node<V>> parentHashMap;
        public HashMap<Node<V>, Integer> sizeHashMap;

        public UnionFindV(List<V> list) {
            nodeHashMap = new HashMap<>();
            parentHashMap = new HashMap<>();
            sizeHashMap = new HashMap<>();
            for (V v : list) {
                Node<V> vNode = new Node<>(v);
                // 保存v
                nodeHashMap.put(v, vNode);
                parentHashMap.put(vNode, vNode);
                sizeHashMap.put(vNode, 1);
            }
        }

        // 根据任意Node从parentMap中查询到其集合的最顶的代表节点,并且每查一次，都会将长链变O（1）
        public Node<V> getParent(Node<V> node) {
            Stack<Node<V>> stack = new Stack<>();
            while (node != parentHashMap.get(node)) {
                stack.push(node);
                node = parentHashMap.get(node);
            }
            //此时node已经是该集合的代表节点了
            while (!stack.isEmpty()) {
                parentHashMap.put(stack.pop(), node);
            }
            return node;
        }

        public boolean ifSameCollection(V v1, V v2) {
            return getParent(nodeHashMap.get(v1)) == getParent(nodeHashMap.get(v2));
        }

        // 将Nodev1和Nodev2所在的集合合并
        public void union(V v1, V v2) {
            // 拿到代表节点
            Node<V> v1Grand = getParent(nodeHashMap.get(v1));
            Node<V> v2Grand = getParent(nodeHashMap.get(v2));
            if (v1Grand != v2Grand) {// 不在一个集合中的时候
                // 判断代表节点的集合大小，然后小的集合挂载到大的上面
                Integer v1Size = sizeHashMap.get(v1Grand);
                Integer v2Size = sizeHashMap.get(v2Grand);
                Node<V> bigger = v1Size >= v2Size ? v1Grand : v2Grand;
                Node<V> smaller = bigger == v1Grand ? v2Grand : v1Grand;
                // 修改parentMap挂载
                parentHashMap.put(smaller, bigger);
                // 修改size
                sizeHashMap.put(bigger, v1Size + v2Size);
                sizeHashMap.remove(smaller);
            }

        }
    }
}


