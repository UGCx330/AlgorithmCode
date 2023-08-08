package class16.myclass16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 拓扑排序：有向无环图的排序
 * 最先指向别的节点的那些节点们就在前面，换句话说，没有入线的那些点在前面
 * 条件：只给出点类，类中有属性为指向的点List
 * 邻居节点：每个节点指向的那些节点
 * 解法3：深度（指向链的长度最深的排前面）
 * 每个节点的深度=Max(子节点的深度)+1
 * 于是形成递归
 * 并且将节点深度更新到map中
 * baseCase：遍历发现没有子节点，直接更新map为自己的+1长度，并返回
 */
public class Code04_TopologicalOrderDFS_ChangDu {
    public class Node {
        public int value;
        public ArrayList<Node> nextNodes;

        public Node(int value) {
            this.value = value;
            nextNodes = new ArrayList<>();
        }
    }

    // 再次包装
    public class Record {
        public Node node;
        public int deep;

        public Record(Node node, int deep) {
            this.node = node;
            this.deep = deep;
        }
    }

    public Record changDu(Node node, HashMap<Node, Record> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        int deep = 0;
        for (Node nextNode : node.nextNodes) {
            deep += Math.max(deep, changDu(nextNode, map).deep);
        }
        // baseCase，也是要返回上一级的处理
        Record record = new Record(node, deep + 1);
        map.put(node, record);

        return record;
    }

    public ArrayList<Node> start(ArrayList<Node> nodeList) {
        HashMap<Node, Record> map = new HashMap<>();
        ArrayList<Record> arrayList = new ArrayList<>();
        ArrayList<Node> ans = new ArrayList<>();
        for (Node node : nodeList) {
            arrayList.add(changDu(node, map));
        }
        arrayList.sort(new nodeComparator());
        for (Record record : arrayList) {
            ans.add(record.node);
        }
        return ans;
    }

    public class nodeComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            // 深度逆序
            return o2.deep - o1.deep;
        }
    }

}
