package class18.myclass18;

/**
 * 机器人走步问题：
 * 当前有1到N个位置如1到6，现在有一个机器人可以在任意位置如2。
 * 要求指定一个机器人最终要到达的位置如4，指定要走的步数如4
 * 问机器人从2位置走到4位置且必须移动4步的路径数有多少
 * 规则：机器人在1位置时只能往右移动，机器人在N位置只能往左移动，机器人在其他位置可以往左右移动
 * 暴力递归无视重复情况的解：
 * 当前位置向下一个位置递归要路径数
 * baseCase：步数用完时，如果在目标位置，返回1，说明此路行得通，否则返回0.
 */
public class Code01_RobotWalk {
    // index为当前位置，rest为剩余步数，aim为目标位置，N为总位置数
    public int way(int index, int rest, int aim, int N) {
        if (rest == 0) {
            if (index == aim) {
                return 1;
            }
            return 0;
        }
        // 向下一个位置要是否可以有路
        if (index == 1) {
            // 如果当前位置是1，只能往右一步再看路
            return way(2, rest - 1, aim, N);
        }
        if (index == N) {
            // 如果当前位置是N，只能往左一步再看路
            return way(N - 1, rest - 1, aim, N);
        }
        // 其他位置既可以往左也可以往右，所以就是加和
        return way(index - 1, rest - 1, aim, N) + way(index + 1, rest - 1, aim, N);
    }

    /**
     * 基于暴力递归的初步动态规划：
     * 由于递归中可能重复递归别的递归已经递归过的子过程
     * 所以在每个递归过程中，将本次递归结果存入缓存，如果别的递归也是递归这个过程，则直接从缓存中拿出来使用即可。
     * 缓存：每个递归由方法影响形参唯一决定，所以根据方法形参new一个数组出来，长宽为形参大小。数组的每个格子即为递归的结果。
     */
    // 主函数，给定一开始的位置，总剩余步数，目标位置，总位置数
    public void startCache(int index, int rest, int aim, int N) {
        // 由于位置从1到N，第一行不用
        // 第一列是rest为0的情况也就是baseCase
        int[][] cache = new int[rest + 1][N + 1];
        // 只要是没缓存的地方，初始化都是-1
        for (int row = 1; row < cache.length; row++) {
            for (int col = 0; col < cache[0].length; col++) {
                cache[row][col] = -1;
            }
        }
        System.out.println(wayCache(index, rest, aim, N, cache));
    }

    // cache的长-列数=rest一开始给定的大小，宽-行数=index所有可能的情况也就是1到N
    public int wayCache(int index, int rest, int aim, int N, int[][] cache) {
        // 检测cache中有无此次递归的结果
        if (cache[rest][index] != -1) {
            return cache[rest][index];
        }
        // cache没有结果，递归，并将递归最终结果保存到cache
        int cacheAns = 0;
        // caseCase
        if (rest == 0) {
            if (index == aim) {
                cacheAns = 1;
            }
        }
        if (index == 1) {
            cacheAns = wayCache(2, rest - 1, aim, N, cache);
        } else if (index == N) {
            cacheAns = wayCache(N - 1, rest - 1, aim, N, cache);
        } else {
            cacheAns = wayCache(index - 1, rest - 1, aim, N, cache) + wayCache(index + 1, rest - 1, aim, N, cache);
        }
        // 保存
        cache[rest][index] = cacheAns;
        return cacheAns;
    }

    /**
     * 基于暴力递归的进一步动态的规划：
     * 动态规划：暴力递归中有重复递归出现
     * 动态规划的本质：将递归+影响递归形参，转化为数组，数组的长宽就是影响形参，数组中每个格子就对应一个递归，且只要能改成动态规划，说明递归之间必有联系，那么格子之间也必有联系。
     * 就从baseCase的格子开始，根据这种联系，一步步推出其他格子--也就是其他递归结果
     * 推格子的过程直接是根据其他格子加和还是相减还是就等于哪个格子直接推出来的，不需要递归过程！所以就没有重复过程这一说，这就是动态规划。
     * 动态规划首先要自然将暴力递归写出来，然后根据暴力递归改写！！！
     * <p>
     * 从暴力递归中可以得知：
     * 第0列也就是rest为0的情况，只要index！=aim，肯定答案都是0，所以第一列除了index=aim的位置为1，其他格子都是0.
     * 如果index为1，对应数组中第二行，结果=往右走一步的递归结果，即rest-1，index+1，那么数组中第二行的格子=其左下方第三行的格子
     * 如果index为N，对应数组中最后一行，结果=往左走的递归结果，即rest-1，index-1，那么数组中最后一行的格子=其左上方倒数第二行的格子
     * 如果index为其他位置，对应数组中其他行，结果=其左上角上一行的格子+其左下角下一行的格子---杨辉三角形
     * 如此一来，我们可以根据baseCase将第0列的数据填上，然后根据暴力递归，将第二列，第三列...最后一列，依此填上数据。
     * 最终返回题目给定的格子中的数据--即给定的index，rest
     */
    // rest为一开始给定的剩余步数总数，index=一开始给定的位置，aim=目标位置，N为位置总数
    public int wayArray(int index, int rest, int aim, int N) {
        int[][] array = new int[rest + 1][index + 1];
        // 第一列填数据，由于默认为0，所以只需要将aim位置置为1即可
        array[aim][0] = 1;
        for (int col = 1; col < rest; col++) {
            // 第一行的此列位置和最后一行的此列位置由于依赖特殊性单独设置
            array[1][col] = array[2][col - 1];
            array[N][col] = array[N - 1][col - 1];
            // 此列的其他格子
            for (int i = 2; i < N; i++) {
                array[i][col] = array[i - 1][col - 1] + array[i + 1][col - 1];
            }
        }
        return array[index][rest];
    }


}
