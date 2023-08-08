package class15.mycalss15;

import java.util.ArrayList;
import java.util.List;

public class Code05_NumberOfIslandsII {
    /**
     * 动态岛问题：
     * 给m行，n列的全是0的二维数组。
     * 再给定一组二维数组positions，里面的每个位置表示在全0的二维数组中将某位置的0改为1.
     * 要求每执行一次position，返回岛的数量。
     * ps：如果position多次为同一个位置，则不做任何效果
     * 解法：
     * 仍然是老三样升级版，并查集
     * 动态初始化：
     * 每次落下position，size[position++]
     * 下一次落下position检测size[position]如果不是0，则不做任何反应
     * union修改：
     * 每次落下1的位置，上下左右去union,注意不是递归，单纯上下左右各union一次
     * 即落点先自成一个集合，然后上下左右去union
     */
    public class UnionFind {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int setNums;
        private int hang;
        private int lie;

        public UnionFind(int m, int n) {
            hang = m;
            lie = n;
            int total = m * n;
            parent = new int[total];
            size = new int[total];
            help = new int[total];
        }

        public int index(int i, int j) {
            return i * hang + j;
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

        // 落点上下左右union
        public int connect(int i, int j) {
            int index = index(i, j);
            // 如果是新落点，先初始化，再尝试去union，不是新落点直接结束
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                setNums++;
                // union
                union(i - 1, j, i, j);
                union(i + 1, j, i, j);
                union(i, j - 1, i, j);
                union(i, j + 1, i, j);
            }
            return setNums;
        }

        public void union(int i1, int j1, int i2, int j2) {
            // 判断一下落点要union的上下左右是否越界
            if (i1 < 0 || j1 < 0 || i2 < 0 || j2 < 0 || i1 >= hang || i2 >= hang || j1 >= lie || j2 >= lie) {
                return;
            }
            int i = index(i1, j1);
            int j = index(i2, j2);
            // 如果上下左右要union的那个点，size为0，说明此时这个点还是0不是1，直接return
            if (size[i] == 0 || size[j] == 0) {
                return;
            }
            int iParent = findParent(i);
            int jParent = findParent(j);
            if (iParent != jParent) {
                if (size[iParent] >= size[jParent]) {
                    size[iParent] += size[jParent];
                    parent[jParent] = iParent;
                } else {
                    size[jParent] += size[iParent];
                    parent[iParent] = jParent;
                }
            }
            setNums--;
        }
    }

    // positions是两列，n行的数组
    public List<Integer> start(int m, int n, int[][] positions) {
        UnionFind unionFind = new UnionFind(m, n);
        // 存储每次落点的岛数
        ArrayList<Integer> list = new ArrayList<>();
        for (int[] position : positions) {
            // 每次取一行
            list.add(unionFind.connect(position[0], position[1]));
        }
        return list;
    }

}
