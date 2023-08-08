package class14.myclass14;

import java.util.HashSet;

public class Code01_Light {
    /**
     * 给定一个字符串str，只由'X'和'.'两种字符构成
     * 'X'表示墙，不能放灯，也不需要点亮；'.'表示居民点，可以放灯，需要点亮
     * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
     * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
     * <p>
     * 情况分析：
     * 当前来到i位置
     * ①只要i位置是墙也就是X，不放灯，直接到i+1位置
     * ②如果i位置不是X，而是.，说明此处可以放灯。如果i+1位置是X，必须自己为自己点灯，也就是i位置必须放灯
     * ③如果i位置是.,i+1位置也是.，则i和i+1位置都可以放灯，定位i+1位置放灯。
     * ④如果i，i+1，i+2位置都是.,则在i+2位置放灯
     * <p>
     * 所以只要i位置是.，默认要点一台灯(位置可能是i，i+1)，
     * 然后如果i+1位置是X，则点灯位置是i，跳到i+2位置继续循环。
     * 如果i+1位置是.，则点灯位置就是i+1，此时无论i+2位置是.还是X，都会被i+1位置的灯照亮。所以直接跳到i+3位置继续循环
     */
    public int light(String string) {
        char[] chars = string.toCharArray();
        if (chars.length == 0) {
            return 0;
        }
        int lights = 0;
        int i = 0;
        while (i < chars.length) {
            if (chars[i] == 'X') {
                i += 1;
            } else {
                // i位置可以放灯，lights数量(三种情况都是)必先+1个
                lights++;
                if (i + 1 == string.length()) {
                    // 如果来到了最后一个位置，此位置不是X，lights++后直接结束
                    break;
                }
                if (chars[i + 1] == 'X') {
                    i += 2;
                } else {
                    // i+1位置不是X，直接去i+3
                    i += 3;
                }
            }
        }
        return lights;
    }

    /**
     * 暴力
     * 每个位置不是X就可以放，也可以不管是不是X都不放
     * 也就是第一个str有放和不放两种情况，第一个放，对应第二个放不放，第三个放不放......    第一个不放，对应第二个放不放，第三个放不放......
     * 也就是一个方法中，必须递归自己两次，一次是当前点放的递归，一个是当前点不放的递归
     * 然后从最后级放，不放两者取最优解给倒数第二级，倒数第二级的放，不放的最优解给倒数第三级，逐渐返回上一级(递归核心思想)
     * 总有一条路径如放-不放-放-不放-不放-放-不放  是整体的最优解情况
     * 每个点递归前我们如果放灯了，就在容器Set中存储放灯的位置，不放灯直接递归
     * 这样一条路径下来到baseCase的时候，我们可以检测当前路径往Set中放灯的位置，然后在baseCase中判断此路径是否合法，不合法，直接返回一个Integer的最大值，合法返回set的大小
     * 如果这条路径合法则直接返回Set的size，就是放灯的数量
     * 在上层中对两种递归的返回值(不合法Integer最大值，合法就是Set大小)取最小的返回上层
     */
    // chars和set配合检测路径是否合法，index用于递归
    public int violence(char[] chars, HashSet<Integer> set, int index) {
        // baseCase，每条路径走到最后了，如放-不放-放-不放-不放-放-不放已经都弄完了
        // 开始检测路径是否合法，合法返回set的长度，不合法返回Integer最大值，上层取最优解的时候肯定舍弃最大值
        if (index == chars.length) {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != 'X') {
                    // 如果此路径下来，只要是可以点灯的位置，左右都没有灯，说明这条路径不合法
                    if (!set.contains(i) && !set.contains(i - 1) && !set.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            // 此路径合法，但不一定是最优解，只负责返回上层
            return set.size();
        } else {
            // 当前位置不放灯，进入下一个递归
            int buFang = violence(chars, set, index + 1);
            // 当前位置放灯，但是先=int最大值，因为当前位置为'.'的时候才可以放,否则返回值就跟不放一样，所以直接=int最大值得了
            int fang = Integer.MAX_VALUE;
            if (chars[index] == '.') {
                // 放灯的位置添加进set
                set.add(index);
                // 下一个递归
                fang = violence(chars, set, index + 1);
                // 记得移除当前路径每处放灯位置，以便上层递归重新往这些位置放灯
                set.remove(index);
            }
            return Math.min(fang, buFang);
        }
    }


}
