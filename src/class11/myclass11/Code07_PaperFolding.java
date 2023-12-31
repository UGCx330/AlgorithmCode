package class11.myclass11;

public class Code07_PaperFolding {
    /**
     * 折纸问题
     * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开
     * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面
     * 如果从纸条的下边向上方连续对折2次，压出折痕后展开
     * 此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
     * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次
     * 请从上到下打印所有折痕的方向。
     * N=1时，打印: down
     * N=2时，打印: down down up
     */

    /**
     * 折纸问题=二叉树
     * 第一次对折产生的凹折痕就是root
     * 第二次产生的②凹折痕就是左节点，产生的②凸折痕就是右节点
     * 第三次将会在②凹折痕上面产生③凹折痕，下面产生③凸折痕。   ②凸折痕上面产生③凹折痕，下面产生③凸折痕。
     * ......
     * 由此可见，根就是凹折痕，然后每个节点的左节点是凹折痕，右节点是凸折痕
     * 折痕从上往下就是中序遍历
     * 折叠N次代表树有N层。我们可以随意输入i--代表从第几层开始，输入true--代表从i层的一个凹折痕开始，或者输入i--代表从第几层开始，输入false--代表从i层的一个凸折痕开始
     * 从这个节点i层开始往下中序遍历到N层，递归的方式打印每个节点。
     */
    public void foldInHalf(int i, int N, boolean crease) {
        if (i <= N) {
            foldInHalf(i + 1, N, true);
            System.out.println(crease ? "凹" : "凸");
            foldInHalf(i + 1, N, false);
        }
    }
}
