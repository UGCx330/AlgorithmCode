package class10.myclass10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {
    public class Node {
        private Node left;
        private Node right;
        private int value;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 非递归实现：二叉树前序遍历
     * 准备一个栈，将头节点压入，并且弹出(输出相当于遍历此节点)
     * 弹出头节点后压入右孩子，左孩子
     * 弹出左孩子并输出，压入左孩子的右孩子，左孩子
     * 循环
     */
    public void headPre(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> nodeStack = new Stack<>();
        Node popNode = null;
        nodeStack.push(head);
        while (!nodeStack.isEmpty()) {
            popNode = nodeStack.pop();
            System.out.println(popNode.value + "|");
            if (popNode.right != null) {
                nodeStack.push(popNode.right);
            }
            if (popNode.left != null) {
                nodeStack.push(popNode.left);
            }
        }
    }

    /**
     * 非递归实现：二叉树中序遍历
     * 核心思想：树就是由一条条的祖先节点组成的，形状:/
     * 准备一个栈，先将头节点的左侧一整条祖先节点压入栈中直到节点为null弹出第一个节点并输出
     * 继续将弹出节点的左侧一整条祖先节点压入栈中直到节点为null。如果弹出节点的左侧没有节点，将右子树节点压入栈中
     * 弹出这个节点并输出，将弹出节点的左侧一整条祖先节点压入栈中直到节点为null。如果右子树节点也是null。从栈中继续弹出节点并输出。然后将弹出节点的左侧一整条祖先节点压入栈中直到节点为null。
     * 循环
     */
    public void headMiddle(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node current = head;
        while (current != null || !stack.isEmpty()) {
            // 栈为空，指针不为null代表要压入，不能结束。指针为null，栈不为null，说明某棵子树左子树到了最底下了，不能结束需要弹出子树头节点并压入右节点。
            if (current != null) {
                // 压入整条左祖先节点
                stack.push(current);
                current = current.left;
            } else {
                // current为null
                current = stack.pop();
                System.out.println(current.value + "|");
                // 处理完左子树，输出头节点，处理右子树
                current = current.right;
            }
        }
    }

    /**
     * 非递归实现：二叉树的后序遍历
     * 非递归实现先序遍历的时候，从栈中弹出的节点压入另一个栈2，栈2中节点的顺序就是后续遍历，不过左右相反。
     * 所以要实现左右头要让先序遍历变成头右左
     */
    public void headEnd(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> preStack = new Stack<>();
        Stack<Node> endStack = new Stack<>();
        Node current = head;
        preStack.push(current);
        while (!preStack.isEmpty()) {
            current = preStack.pop();
            endStack.push(current);
            if (current.left != null) {
                preStack.push(current.left);
            }
            if (current.right != null) {
                preStack.push(current.right);
            }
        }
        // 栈2出栈
        while (!endStack.isEmpty()) {
            System.out.println(endStack.pop().value + "|");
        }
    }

    /**
     * 非递归实现：二叉树的后序遍历
     * O(1)空间复杂度
     * 核心思想：树就是由一条条的祖先节点组成的，形状:/
     * <p>
     * c记录栈顶节点(stack.peek)，h记录的就是该子树弹出的节点，可能是左也可能是右节点，left和right是c的左右节点
     * 如果left！=null且！=h，说明左节点没有入栈，就压入左节点，继续下一个循环。
     * 如果left=h，说明左节点被弹出，就不管左节点了，看right！=h且right！=null，说明右节点没有入栈，压入右节点
     * 第一个if判断left是否为null且是否为h，都不是压入左节点。c=左节点
     * 第二个else if应该就是right是否为null，right是否为h，都不是压入右节点c=右节点
     * 但是第一个id后面要紧跟判断right是否等于h。
     * 如果左节点和右节点都没有被压入，那么判断left的时候就打住了，压入left
     * 如果左节点是被弹出的，h就会来到左节点的位置。第一个if不会进入，跳到第二个else if，此时打住，压入右节点，c=右节点
     * 下面就是处理右节点过程，直到右节点被弹出，h=右节点
     * 再循环，就会发现第一个if和第二个else if都不会进入，因为两个都含有h是否等于right这个条件，现在h就是=right的
     * 所以当前子树的左右节点遍历就完成了
     * h就会来到当前子树的头节点位置，c就会来到上一层子树的头节点位置，h此时就是左节点。
     * <p>
     * 即h=右节点的前提是左节点已经处理完被弹出。然后右节点被弹出，所以如果h=右节点了，说明这个子树就处理完了
     * 形成这个局面的前提就是我们直接先压入的整个树的最左侧的一条祖先节点，所以处理的就是最左下角那个节点，然后处理最左下角节点的子树的右节点，然后往上逐渐升层
     * 就形成了一种自下而上的左右头的规律
     */
    public void headEndO1(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node current = null;
        Node h = head;
        while (!stack.isEmpty()) {
            // 一个循环处理一棵子树，要么不弹出节点，压入左右节点,current=左右节点
            current = stack.peek();
            if (current.left != null && current.left != h && current.right != h) {
                stack.push(current.left);
            } else if (current.left != h && current.right != h) {
                stack.push(current.right);
            } else {
                // 要么弹出节点(左右没有子节点或者h=右节点，代表这棵树已经遍历完了)，h=记下弹出节点的位置，current=顶部节点(也就是弹出节点的父节点)
                System.out.println(stack.pop().value + "|");
                h = current;
            }
        }
    }


}
