package class15.mycalss15;

public class Code01_FriendCircles {
    /**
     * 一群朋友中，有几个不相交的朋友圈
     * Leetcode题目：https://leetcode.com/problems/friend-circles/
     * 即1234组成16格子的九宫格--二维数组，1认识2，默认2也认识1，所以实际上只有上半部分格子有效。
     * 如果1认识2，则在他们的格子上标记1，不认识标记0
     * 如果1认识2，2认识3，默认123为一个朋友圈，即便1不认识3
     * 问：给出一个九宫格(二维数组)，多少个朋友圈
     * <p>
     * 解法：
     * 使用并查集，先将每个人作为一个独立集合
     * 遍历九宫格上三角部分，如果碰到1，将两者归为一个集合
     * 优化：
     * 使用数组代替原本的HashMap
     * 即数组下标对应第几号人，数组的值也对应第几号人
     */
    public class UnionFind {
        private int[] parents;
        private int[] size;
        // 集合数量
        private int setNums;
        // 用于扁平化代替栈的辅助数组
        private int[] help;

        public UnionFind(int peopleNums) {
            parents = new int[peopleNums];
            size = new int[peopleNums];
            help = new int[peopleNums];
            setNums = peopleNums;
            // 初始化
            for (int i = 0; i < peopleNums; i++) {
                // 每个集合parent都是自己
                parents[i] = i;
                // 每个集合大小都是1一开始
                size[i] = 1;
            }
        }

        public int findParent(int index) {
            int i = 0;
            while (index != parents[index]) {
                help[i++] = index;// 0有值,但是指针在1
                index = parents[index];
            }
            while (i > 0) {
                i--;
                parents[help[i]] = index;
            }
            return index;
        }

        public void union(int i, int j) {
            int iParent = findParent(i);
            int jParent = findParent(j);
            // 不在同一个集合，合并
            if (i != j) {
                int iSize = size[i];
                int jSize = size[j];
                if (iSize >= jSize) {
                    size[i] += jSize;
                    parents[j] = i;
                } else {
                    size[j] += iSize;
                    parents[i] = j;
                }
                // 集合数量--
                setNums--;
            }
        }

        public int howManySets() {
            return setNums;
        }
    }

    public int start(int[][] ints) {
        // 列数
        int peopleNums = ints.length;
        UnionFind unionFind = new UnionFind(peopleNums);
        // 根据二维数组，合并
        for (int i = 0; i < peopleNums; i++) {
            // 忽略分割线上的自己与自己是朋友,j=i+1
            for (int j = i + 1; j < peopleNums; j++) {
                if (ints[i][j] == 1) {
                    // 是朋友，合并
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.howManySets();
    }
}
