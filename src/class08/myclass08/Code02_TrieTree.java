package class08.myclass08;

public class Code02_TrieTree {
    /**
     * 前缀树的使用：
     * 只有小写字符的字符串数组，存储这些字符串以及以某几个字符为前缀的字符串个数。
     * 使用Node类型的对象作为站点，对象中含有该站点被经过的次数-pass属性，以该站点为重点的字符串个数-end属性，以及去往下一站点的26个小写字母的路-Node[26]属性
     * 原始数组arr[xxxx]中，取出每一个字符串，从字符串的第一个字符开始，每个字符如b减去a的ASCII码值97=1.即为从根站点开始，应该走Node[1]这条路去往下一个站点。
     * 由于根站点初始化的Node[26]只有长度，值全部为null，所以需要走哪条路，就应该将哪条路=new Node[26]，如Node[1]=new Node[26]
     * 每到达一个站点，node.pass++,如果该站点为该字符串的终点，node.end++
     * <p>
     * 查找是否存在某个字符串在前缀树中：从根站点出发，根据每个字符走不同的路，看这条路是否能到某个站点，如果字符串没遍历完，但是某条路下没有站点，则说明不存在该字符串，如果最后一条路到达的站点的end>=1，则说明存在该字符串。
     * <p>
     * 查找多少个字符串在前缀树中以某个字符串为前缀：从根站点出发，根据每个字符走不同的路，如果某条路(对应某个字符)下一个站点为null，则说明不存在该前缀的字符串
     * 如果顺利走完字符对应的所有路到达站点，则最后一个站点的pass值即为以该字符串为前缀的字符串个数。
     * <p>
     * 删除某个字符串从前缀树中：在根据字符走的路到达的站点上，提前将下一个站点的pass--，如果pass--=0，则说明该站点只有这个字符串经过，就可以将当前站点的到下一个站点的路Node[x]置为null。java自动垃圾回收
     * <p>
     * 字符串不止26个英文字母，而是复杂的符号时，可以使用HashMap作为存储站点对象的容器即原本的Node[]换为HashMap<Integer,Node>
     */

    public class Node {
        private Integer pass;
        private Integer end;
        private Node[] nodes;

        public Node() {
            nodes = new Node[26];
        }
    }

    public class PreTree {
        private Node root;

        public PreTree() {
            root = new Node();
        }

        public void add(String str) {
            if (str == null) {
                return;
            }
            // 转字符
            char[] chars = str.toCharArray();
            // 站点，循环里面复用，刚开始是根站点
            Node node = root;
            // 每个字符串必经过根，先将根的pass++
            node.pass++;
            // path即为字符-97应该走哪条路到达新站点
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                // 每个字符算出的路--nodes[]的下标位置，该路应该到达新站点，所以该下标应该new Node()
                path = chars[i] - 'a';
                if (node.nodes[path] == null) {
                    node.nodes[path] = new Node();
                }
                // 去根节点的下一个站点也就是刚根据此一个字符算出来的应该走哪条路去往的刚new 的Node-站点
                node = node.nodes[path];
                // 别忘了下一个站点的pass++
                node.pass++;
            }
            // 每个字符都建好对应的站点后，最后一个站点也就是当前站点的end++
            node.end++;
        }

        public int containsTimes(String str) {
            if (str == null) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';
                // 没走完所有字符前，当前字符对应的站点为null说明该字符不存在于前缀树中
                if (node.nodes[path] == null) {
                    return 0;
                }
                node = node.nodes[path];
            }
            return node.end;
        }

        public int preFixContainsTimes(String str) {
            if (str == null) {
                return 0;
            }
            Node node = root;
            char[] chars = str.toCharArray();
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';
                if (node.nodes[path] == null) {
                    return 0;
                }
                node = node.nodes[path];
            }
            return node.pass;
        }

        public void delete(String str) {
            if (str == null) {
                return;
            }
            if (containsTimes(str) != 0) {
                Node node = root;
                node.pass--;
                char[] chars = str.toCharArray();
                int path = 0;
                for (int i = 0; i < chars.length; i++) {
                    path = chars[i] - 'a';
                    // 从根节点来看第一个字符的到达的站点，如果pass--之后为0说明只有这个字符串有这个字符。直接将站点抹掉。后续站点也就跟随抹掉了。
                    if (--node.nodes[path].pass == 0) {
                        node.nodes[path] = null;
                        return;
                    }
                    // 如果别的字符串也有这个字符，则去往该字符的站点
                    node = node.nodes[path];
                }
                // 循环完毕说明还有有别的字符串将该字符串作为前缀使用，将最后一个字符对应的站点的end--
                node.end--;
            }
        }
    }
}
