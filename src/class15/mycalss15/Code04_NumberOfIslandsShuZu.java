package class15.mycalss15;

public class Code04_NumberOfIslandsShuZu {
    /**
     * 岛问题（递归解法 + 并查集解法 + 并行解法）
     * 给定一个二维数组matrix，里面的值不是1就是0，上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量
     * 并查集解法：老三样升级版：数组代替HashMap，更快
     * 区分1的方法：由于每个1在matrix二维数组中都有一个唯一的下标，我们可以将下标换算为一维数组下标
     * 公式：对于任意二维数组下标matrix[i][j]==一维下标:i*列数+j
     * 准备一个matrix行*列规模的一维数组parent，用于存储i(二维转化而来)位置上的parent是哪个位置（二维转化而来）
     * 准备一个matrix行*列规模的一维数组size,用于存储i(二维转化而来)为代表元素的集合的大小
     * 遍历matrix数组，将1的位置，转为一维数组的下标，并初始化parent为自己，初始化size为1
     * 随后与HasMap的并查集解法一样，再次遍历matrix数组，左和上合并。
     * 只不过合并的时候需要将二维左边转为一维罢了
     */
    public class UnionFind {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int setNums;
        private int lieShu;

        public UnionFind(char[][] chars) {
            lieShu = chars[0].length;
            int total = chars.length * lieShu;
            parent = new int[total];
            size = new int[total];
            help = new int[total];
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < lieShu; j++) {
                    if (chars[i][j] == '1') {
                        int index = index(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        setNums++;
                    }
                }
            }
        }

        // 二维转一维下标
        public int index(int i, int j) {
            return i * lieShu + j;
        }

        public int findParent(int index) {
            int i = 0;
            while (index != parent[index]) {
                help[i++] = index;
                index = parent[index];
            }
            while (i > 0) {
                i--;
                parent[help[i]] = index;
            }
            return index;
        }

        public void union(int i1, int j1, int i2, int j2) {
            int i = index(i1, j1);
            int j = index(i2, j2);
            int iParent = findParent(i);
            int jParent = findParent(j);
            if (iParent != jParent) {
                int iSize = size[iParent];
                int jSize = size[jParent];
                if (iSize >= jSize) {
                    parent[jParent] = iParent;
                    size[iParent] = iSize + jSize;
                } else {
                    parent[iParent] = jParent;
                    size[jParent] = iSize + jSize;
                }
            }
            setNums--;
        }

        public int howManyCollections() {
            return setNums;
        }
    }

    public int start(char[][] matrix) {
        UnionFind unionFind = new UnionFind(matrix);
        int hang = matrix.length;
        int lie = matrix[0].length;
        // 依旧分三次
        for (int j = 1; j < lie; j++) {
            if (matrix[0][j] == '1') {
                if (matrix[0][j - 1] == '1') {
                    unionFind.union(0, j - 1, 0, j);
                }
            }
        }

        for (int i = 1; i < hang; i++) {
            if (matrix[i][0] == '1') {
                if (matrix[i - 1][0] == '1') {
                    unionFind.union(i - 1, 0, i, 0);
                }
            }
        }

        for (int i = 1; i < hang; i++) {
            for (int j = 1; j < lie; j++) {
                if (matrix[i][j] == '1') {
                    if (matrix[i - 1][j] == '1') {
                        unionFind.union(i - 1, j, i, j);
                    }
                    if (matrix[i][j - 1] == '1') {
                        unionFind.union(i, j - 1, i, j);
                    }
                }
            }
        }
        return unionFind.howManyCollections();
    }
}
