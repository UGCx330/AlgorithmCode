package class16.myclass16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 拓扑排序：有向无环图的排序
 * 最先指向别的节点的那些节点们就在前面，换句话说，没有入线的那些点在前面
 * 条件：只给出点类，类中有属性为指向的点List
 * 邻居节点：每个节点指向的那些节点
 * 解法2：
 * 依然是遍历每个节点，但是此时我们的HashMap表不再是存储被指数，而是存储这个点直接或者间接指向的所有点的个数。称为点集数
 * 那么点集数大的肯定就排在前面了。
 * 点集数肯定是一个个加上来的，采用递归方法
 */
public class Code05_TopologicalOrderDFS_DianJiShu {
    public class Node {
        public int value;
        public ArrayList<Node> nextNodeList;

        public Node(int value) {
            this.value = value;
            nextNodeList = new ArrayList<>();
        }
    }

    // 再封装一个类，nextNums就是点集数
    public class Record {
        public Node node;
        public long nextNums;

        public Record(Node node, long nextNums) {
            this.node = node;
            this.nextNums = nextNums;
        }
    }

    public class DianJiCompare implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            // 点集数大的排前面，逆序
            return Long.compare(o2.nextNums, o1.nextNums);
        }
    }

    public ArrayList<Node> dianJiShu(ArrayList<Node> nodesList) {
        // 存储点和对应的封装的点集类
        HashMap<Node, Record> map = new HashMap<>();
        // 返回暂存的点集,并排好序
        ArrayList<Record> list = new ArrayList<>();
        for (Node node : nodesList) {
            list.add(nodeDianJi(node, map));
        }
        list.sort(new DianJiCompare());
        // copy返回
        ArrayList<Node> ans = new ArrayList<>();
        for (Record record : list) {
            ans.add(record.node);
        }
        return ans;
    }

    // 根据node，在map中查询到对应的点集并返回点集
    public Record nodeDianJi(Node node, HashMap<Node, Record> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        // 找邻居，将他们的点集返回，得到点集数之和，加上自己1个，更新map后返回
        long dianJiShu = 1;
        for (Node next : node.nextNodeList) {
            dianJiShu += nodeDianJi(next, map).nextNums;
        }
        // 同样也是baseCase,如果没有邻居，直接自己点集数+1更新map
        Record record = new Record(node, dianJiShu);
        map.put(node, record);
        return record;
    }

}
