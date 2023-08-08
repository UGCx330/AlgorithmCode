package class17.myclass17;

import class16.myclass16.Edge;
import class16.myclass16.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * D算法之加强堆改进：
 * 使用set去除已经使用过的节点每次都是O（n）
 * 使用小根堆：logn
 * 将每个解锁的节点加入小根堆，要使用节点，弹出后，加入答案中，然后并不需要从反向索引表中移除，而是索引置为-1。
 * 这样新加入的其他节点每次检查该节点是否已经被使用过或者从未有过直接从索引表看值即可。
 * 如果新加入的节点要更新没被使用过的节点，就需要更新堆中的节点，从反向索引表找到该位置，更新，然后与堆中最后一个交换位置，上浮
 */
public class Code01_Dijkstra {
    // 小根堆
    public class SuperHeap {
        private Node[] nodes;
        private HashMap<Node, Integer> indexMap;
        // 额外使用map存储答案
        private HashMap<Node, Integer> distanceMap;
        private int size;

        public SuperHeap(int heapSize) {
            nodes = new Node[heapSize];
            indexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        public void swap(int i, int j) {
            indexMap.put(nodes[i], j);
            indexMap.put(nodes[j], i);
            Node temp = nodes[i];
            nodes[i] = nodes[j];
            nodes[j] = temp;
        }

        // 上浮
        public void heapiInsert(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 下沉
        public void heapify(int index) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallerChildren = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1 : left;
                int least = distanceMap.get(nodes[index]) < distanceMap.get(nodes[smallerChildren]) ? index : smallerChildren;
                if (index != least) {
                    // 下沉
                    swap(index, least);
                    index = least;
                    left = index * 2 + 1;
                }
            }
        }

        // 判断一个节点是否进入过堆中
        public boolean ifIn(Node node) {
            return indexMap.containsKey(node);
        }

        // 判断和一个节点是否还在堆中未被使用
        public boolean ifExists(Node node) {
            return ifIn(node) && indexMap.get(node) != -1;
        }

        // 包装Node
        public class RecordNode {
            public Node node;
            public int distance;

            public RecordNode(Node node, int distance) {
                this.node = node;
                this.distance = distance;
            }
        }

        // 弹出堆顶,并且包装一下node跟他的距离,说明此节点以后再也不会被更新了
        public RecordNode pop() {
            // 临时保存node
            Node popNode = nodes[0];
            RecordNode recordNode = new RecordNode(popNode, distanceMap.get(popNode));
            // distanceMap中移除掉
            distanceMap.remove(popNode);
            // 置下标为-1，说明该节点已经被使用弹出
            indexMap.put(popNode, -1);
            swap(0, size - 1);
            // 置空丢掉node
            nodes[size - 1] = null;
            // 尺寸--
            size--;
            // 0位置下沉
            heapify(0);
            return recordNode;
        }

        // 给一个节点和他的距离，判断是否需要更新
        public void insertOrUpdate(Node node, int distance) {
            // 如果该节点在堆中,尝试更新,如果更新成功，则值一定是变小或者不变，都去上浮一下
            if (ifExists(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                heapiInsert(indexMap.get(node));
            }
            // 如果没有进入过堆，那么就要新增此节点于堆
            if (!ifIn(node)) {
                distanceMap.put(node, distance);
                nodes[size] = node;
                indexMap.put(node, size);
                heapiInsert(size);
                size++;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public HashMap<Node, Integer> start(Node head, int size) {
        SuperHeap superHeap = new SuperHeap(size);
        HashMap<Node, Integer> ans = new HashMap<>();
        superHeap.insertOrUpdate(head, 0);
        while (!superHeap.isEmpty()) {
            Node node = superHeap.pop().node;
            int distance = superHeap.pop().distance;
            ans.put(node, distance);
            for (Edge edge : node.edgeList) {
                superHeap.insertOrUpdate(edge.toNode, distance + edge.weight);
            }
        }
        return ans;
    }

}
