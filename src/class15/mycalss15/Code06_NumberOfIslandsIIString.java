package class15.mycalss15;

import java.util.ArrayList;
import java.util.HashMap;

public class Code06_NumberOfIslandsIIString {
    /**
     * 空降岛问题初始化优化：
     * 如果m*n比较大，会经历很重的初始化
     * UnionFind类只是一个能代表集合的容器
     * 如果想要落点与UnionFind中数组下标一一对应，可以使用String下标代替int下标
     * 即UnionFind类的构造器无序提前制造出很大的m*n空间以便初始化落点的时候找到对应的int[]位置
     * 而是直接将落点二维坐标转为x_y的形式的字符串，再去初始化。
     * 后续union，只需要将原坐标转为x-1_y，x+1_y等再去操作即可
     * UnionFind中需要提供HasMap<String,String>parent等保存信息
     */
    public class UnionFind {
        private HashMap<String, String> parentHashMap;
        private HashMap<String, Integer> sizeHashMap;
        // 因为用String代替了下标，所以不需要使用数组下标表示位置了，也就不需要使用int[]help
        private ArrayList<String> help;
        private int sizeNums;

        public UnionFind() {
            parentHashMap = new HashMap<>();
            sizeHashMap = new HashMap<>();
            help = new ArrayList<>();
        }

        public String findParent(String index) {
            while (index != parentHashMap.get(index)) {
                help.add(index);
                index = parentHashMap.get(index);
            }
            for (String s : help) {
                parentHashMap.put(s, index);
            }
            help.clear();
            return index;
        }

        public void union(String index1, String index2) {
            // 检查union的点是否为null
            if (parentHashMap.containsKey(index1) && parentHashMap.containsKey(index2)) {
                String parent1 = findParent(index1);
                String parent2 = findParent(index2);
                if (!parent1.equals(parent2)) {
                    Integer size1 = sizeHashMap.get(parent1);
                    Integer size2 = sizeHashMap.get(parent2);
                    String bigger = size1 >= size2 ? parent1 : parent2;
                    String smaller = bigger.equals(parent1) ? parent2 : parent1;
                    parentHashMap.put(smaller, bigger);
                    sizeHashMap.put(bigger, size1 + size2);
                    sizeNums--;
                }
            }
        }

        public int connect(int i, int j) {
            String index = String.valueOf(i) + "_" + String.valueOf(j);
            if (!parentHashMap.containsKey(index)) {
                parentHashMap.put(index, index);
                sizeHashMap.put(index, 1);
                sizeNums++;
                String up = String.valueOf(i - 1) + "_" + String.valueOf(j);
                String down = String.valueOf(i + 1) + "_" + String.valueOf(j);
                String left = String.valueOf(i) + "_" + String.valueOf(j - 1);
                String right = String.valueOf(i) + "_" + String.valueOf(j + 1);
                union(up, index);
                union(down, index);
                union(left, index);
                union(right, index);
            }
            return sizeNums;
        }
    }
}
