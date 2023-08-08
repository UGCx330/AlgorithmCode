package class22.myclass22;

/**
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 * 解：范围尝试模型，N滴血，0到N，K次打击，0到K
 * 每次打击掉0到M滴血，N次打击，共有(M+1)^N种打击结果。
 * 如果第X次打击后，血量<=0，那么剩下的(M+1)^(K-X+1）种打击结果一直在鞭尸。但是这些结果也要算上
 * 即最后K次打击后返回能将怪兽血量杀到<=0的所有打击结果/(M+1)^N就是概率
 */
public class Code01_KillMonster {
    public double start(int N, int M, int K) {
        return (double) process(N, M, K) / (double) Math.pow(M + 1, K);
    }

    public long process(int hp, int M, int dao) {
        // baseCase:能砍到最后一刀,如果血量<=0，说明有效击杀
        if (dao == 0) {
            return hp <= 0 ? 1 : 0;
        }
        // baseCase剪枝：如果刀数没用完，如果上一刀导致怪兽血量<=0，那么直接返回(M+1)^剩余刀数种答案
        // 如果不剪枝，动态规划数组就需要将负数列也画上，过于冗余
        // 此时如果列是剩余血量，那么如果这一刀还没砍怪兽血量已经<=0,那么有效击杀数就是M + 1^dao，也就是第0列的某一行的值
        if (hp <= 0) {
            return (long) Math.pow(M + 1, dao);
        }
        long ans = 0;
        for (int i = 0; i <= M; i++) {
            ans += process(hp - i, M, dao - 1);
        }
        return ans;
    }

    /**
     * 动态规划数组
     * 变化形参为剩余的血量hp，和刀数dao
     */
    public long process2(int hp, int M, int dao) {
        int row = dao + 1;
        int col = hp + 1;
        // 行：刀数，列：剩余hp
        long[][] ints = new long[dao + 1][hp + 1];
        // baseCase
        ints[0][0] = 1;
        // 其他格子
        for (int i = 1; i < row; i++) {
            // 剪枝
            ints[i][0] = (long) Math.pow(M + 1, i);
            for (int j = 1; j < col; j++) {
                long ans = 0;
                for (int k = 0; k < M; k++) {
                    // j-砍去的血量可能越界，越界是因为此刀把怪兽血量砍到<=0了，所以后续是M+1^剩余刀数种有效击杀
                    if (j - k <= 0) {
                        ans += ints[i - 1][0];
                    } else {
                        ans += ints[i - 1][j - k];
                    }
                }
                ints[i][j] = ans;
            }
        }
        return ints[hp][dao];
    }

    /**
     * 枚举优化:
     * 每一个格子依赖于上方及上方往左的格子，假设格子A左边的为格子B
     * 格子A=砍0到M滴血的情况，格子B也是，并且格子B=格子A-1
     * 所以格子A=格子B+格子A上方格子-格子B上方最左格子，如果格子B上方最左格子是越界情况，说明这一刀砍的是M滴血的情况，剩余刀数所有都是有效击杀。
     * 格子B上方最左格子=格子B所在行的上一行第0列的值
     */
    public long process3(int hp, int M, int dao) {
        int row = dao + 1;
        int col = hp + 1;
        // 行：刀数，列：剩余hp
        long[][] ints = new long[dao + 1][hp + 1];
        // baseCase
        ints[0][0] = 1;
        // 其他格子
        for (int i = 1; i < row; i++) {
            // 剪枝
            ints[i][0] = (long) Math.pow(M + 1, i);
            for (int j = 1; j < col; j++) {
                ints[i][j] = ints[i - 1][j] + ints[i][j - 1];
                if (j - 1 - M < 0) {
                    ints[i][j] -= ints[i - 1][0];
                } else {
                    ints[i][j] -= ints[i - 1][j - 1 - M];
                }
            }
        }
        return ints[hp][dao];
    }
}
