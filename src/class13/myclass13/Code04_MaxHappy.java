package class13.myclass13;

import java.util.ArrayList;
import java.util.List;

public class Code04_MaxHappy {
    public class Employee {
        public int happy;
        public List<Employee> employees;

        public Employee(int h) {
            happy = h;
            employees = new ArrayList<>();
        }
    }

    /**
     * 一颗多叉树节点模拟公司人员
     * 头节点是boss，它的子节点就是boss直属下属。
     * 现在每个人员(节点)都有快乐值，要举办一场聚会
     * 如果boss来了，那么他的子节点就不回来，但是子节点的子节点可以来
     * 如果boss没来，那么他的所有子节点包括子节点的子节点都可以来。
     * 现在求邀请人员可以达到最大快乐值是多少。
     * <p>
     * 可行性分析：
     * 如果head来，总体快乐值=head快乐值+各个子树的head不来的快乐值合
     * 如果head不来，总体快乐值=Math.max(各个子树的head来的快乐值合,各个子树head不来的快乐值合)
     * <p>
     * 递归信息体：
     * ①左右子树head来的快乐值之和
     * ②左右子树head不来的快乐值之和
     */
    public class Info {
        public int noBossHappySum;
        public int haveBossHappySum;

        public Info(int noBossHappySum, int haveBossHappySum) {
            this.noBossHappySum = noBossHappySum;
            this.haveBossHappySum = haveBossHappySum;
        }
    }

    public Info maxHappy(Employee employee) {
        if (employee == null) {
            return new Info(0, 0);
        }
        int noBossHappySum = 0;
        int haveBossHappySum = employee.happy;
        for (Employee employee1 : employee.employees) {
            Info info = maxHappy(employee1);
            noBossHappySum += Math.max(info.haveBossHappySum, info.noBossHappySum);
            haveBossHappySum += info.noBossHappySum;
        }
        return new Info(noBossHappySum, haveBossHappySum);
    }

}
