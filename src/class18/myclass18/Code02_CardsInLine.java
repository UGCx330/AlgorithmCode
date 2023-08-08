package class18.myclass18;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数
 * <p>
 * 如题，如果是正常情况，必然是先手者胜利，除了一些特殊情况如1，100，1  先手无论拿左右哪个都会输
 * 解：
 * 先手的人有两种拿法，拿左或者拿右。
 * 并且由于先手的人绝顶聪明，一定会考虑所有情况，拿的牌后续回让后手的人拿到最差的牌。
 * 先手者拿完牌之后变成后手。
 * 后手者由于被先手制裁，虽然会想办法拿所有情况最好的牌，但是那种情况被先手者锁死，所以只能拿最差的牌
 */
public class Code02_CardsInLine {
    public int start1(int[] arr) {
        int N = arr.length - 1;
        int firstPeople = firstHand(arr, 0, N);
        int lastPeople = lastHand(arr, 0, N);
        return Math.max(firstPeople, lastPeople);
    }

    // arr为所有牌的分数，left为当前人可以拿的左边的牌，right为当前人可以拿的右边的牌
    public int firstHand(int[] arr, int left, int right) {
        // baseCase:left==right只剩下最后一张牌，由于此人此时是先手，拿走这张牌
        if (left == right) {
            return arr[left];
        }
        // 拿左边牌,然后+后手拿牌的分数
        int leftCore = arr[left] + lastHand(arr, left + 1, right);
        // 拿右边牌,然后+后手拿牌的分数
        int rightCore = arr[right] + lastHand(arr, left, right - 1);
        // 由于是先手，所以返回最大的分数
        return Math.max(leftCore, rightCore);
    }

    public int lastHand(int[] arr, int left, int right) {
        // baseCase:只剩一张牌，由于是后手，所以会被先手拿走。
        if (left == right) {
            return 0;
        }
        // 后由于先手肯定会拿走一张牌，所以如果先手拿左边的牌，就在剩余牌中当作先手拿牌
        int leftBeTake = firstHand(arr, left + 1, right);
        // 后由于先手肯定会拿走一张牌，所以如果先手拿右边的牌，就在剩余牌中当作先手拿牌
        int rightBeTake = firstHand(arr, left, right - 1);
        // 由于此人此时是后手，所以先手拿走的牌肯定是让后手后续当作先手拿牌的时候拿到的分数最小的牌
        return Math.min(leftBeTake, rightBeTake);
    }

    /**
     * 动态递归缓存优化
     * 缓存法：影响递归的方法形参为left和right
     * 但是两个方法相互依赖，所以建立两张数组缓存表，同时传给每个方法
     */
    public int start2(int[] arr) {
        int N = arr.length;
        int[][] fistHandCache = new int[N][N];
        int[][] lastHandCache = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fistHandCache[i][j] = -1;
                lastHandCache[i][j] = -1;
            }
        }
        int firstCore = firstHand2(arr, 0, N - 1, fistHandCache, lastHandCache);
        int lastCore = lastHand2(arr, 0, N - 1, fistHandCache, lastHandCache);
        return Math.max(firstCore, lastCore);
    }

    public int firstHand2(int[] arr, int left, int right, int[][] firstHandCache, int[][] lastHandCache) {
        // 如果缓存中有，返回
        if (lastHandCache[right][left] != -1) {
            return lastHandCache[right][left];
        }
        int core = 0;
        if (left == right) {
            core = arr[left];
        }
        // 先手拿左边
        int leftCore = arr[left] + lastHand2(arr, left + 1, right, firstHandCache, lastHandCache);
        // 先手拿右边
        int rightCore = arr[right] + lastHand2(arr, left, right - 1, firstHandCache, lastHandCache);
        // 由于是先手，取所有情况最大
        core = Math.max(leftCore, rightCore);
        return core;
    }

    public int lastHand2(int[] arr, int left, int right, int[][] firstHandCache, int[][] lastHandCache) {
        if (lastHandCache[right][left] != -1) {
            return lastHandCache[right][left];
        }
        int core = 0;
        if (left == right) {
            core = 0;
        }
        // 后由于先手肯定会拿走一张牌，所以如果先手拿左边的牌，就在剩余牌中当作先手拿牌
        int leftCore = firstHand2(arr, left + 1, right, firstHandCache, lastHandCache);
        // 后由于先手肯定会拿走一张牌，所以如果先手拿右边的牌，就在剩余牌中当作先手拿牌
        int rightCore = firstHand2(arr, left, right - 1, firstHandCache, lastHandCache);
        // 由于此人此时是后手，所以先手拿走的牌肯定是让后手后续当作先手拿牌的时候拿到的分数最小的牌
        core = Math.min(leftCore, rightCore);
        return core;
    }

    /**
     * 动态递归数组表示优化
     * 分析巴暴力递归及其暴力递归建立起来的缓存:
     * 由于baseCase限制left=right返回，所以left是不可能大于right的，两表的下半部分无用
     * 先手表和后手表的行数为left，列数为right，那么两表的格子就代表在牌的left到right，能拿到的分数
     * 分析baseCase：
     * 先手表中，如果baseCase，left=right，即对角线，拿走这张牌，那么对角线的值就是arr中left下标的值
     * 其他格子的值有两部分取最大构成：
     * ①如果拿左边的牌，leftCore=arr[left]+后手表的对应位置的下边格子的值(left+1,right)
     * ②先手表中，如果拿右边的牌，rightCore=arr[right]+后手表的对应位置的左边格子的值(left,right-1)
     * 然后先手表中其他格子的值=①和②取一个最大的填上, 杨辉三角形
     * <p>
     * 后手表中，如果baseCase，left=right，即对角线，这张牌会被先手拿走，那么对角线的值就是0
     * 其他格子的值有两部分取最小构成：
     * ①如果左边的牌被拿走，leftCore=先手表的对应位置的下边格子的值(left+1,right)
     * ②先手表中，如果拿右边的牌，rightCore=后手表的对应位置的左边格子的值（left，right-1）
     * 然后先手表中其他格子的值=①和②取一个最大小的填上，杨辉三角形
     * <p>
     * 分析两边格子的值的依赖关系得知，在两表的baseCase补全的情况下，应该逐渐补齐与对角线平行的格子的值
     * 且由于两者的对角线的值都可以通过baseCase补齐，所以循环中可以直接将两表的所有与对角线平行的格子补齐
     */
    public int pokerGameArrWinner(int[] arr) {
        int N = arr.length;
        int[][] firstHandArr = new int[N][N];
        int[][] lastHandArr = new int[N][N];
        // 补齐baseCase
        for (int i = 0; i < N; i++) {
            firstHandArr[i][i] = arr[i];
            // lastHandArr默认值就是0不需要初始化
        }
        // 补齐其他格子,row就是left，col就是right,col永远大于等于row，所以col先越界
        for (int i = 1; i < N; i++) {
            int row = 0;
            int col = i;
            while (col < N) {
                firstHandArr[row][col] = Math.max(arr[row] + lastHandArr[row + 1][col], arr[col] + lastHandArr[row][col - 1]);
                lastHandArr[row][col] = Math.min(firstHandArr[row + 1][col], firstHandArr[row][col - 1]);
                row++;
                col++;
            }
        }
        return Math.max(firstHandArr[0][N - 1], lastHandArr[0][N - 1]);

    }

}
