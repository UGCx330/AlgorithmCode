package class07.myclass07;

import java.util.Comparator;
import java.util.Map;

public class Code02_EveryStepShowBoss {
    // 两个加强堆中存储的是客户对象
    public static class Customer {
        private int id;
        private int buyCount;
        private int time;

        public Customer(int id, int buyCount, int time) {
            this.id = id;
            this.buyCount = buyCount;
            this.time = 0;
        }
    }

    // 候选区堆，购买数从大到小排序+时间从前往后排序
    public static class StandComparator implements Comparator<Field<Customer>> {
        @Override
        public int compare(Field<Customer> o1, Field<Customer> o2) {
            return o1.value.buyCount != o2.value.buyCount ? o2.value.buyCount - o1.value.buyCount : o1.value.time - o2.value.time;
        }
    }

    // 获奖区堆，从小到大排序+时间从前往后排序
    public static class BingoComparator implements Comparator<Field<Customer>> {
        @Override
        public int compare(Field<Customer> o1, Field<Customer> o2) {
            return o1.value.buyCount != o2.value.buyCount ? o1.value.buyCount - o2.value.buyCount : o1.value.time - o2.value.time;
        }
    }

    public static class WhosyourDady {
        private HeapGreater<Customer> standBy;
        private HeapGreater<Customer> bingo;
        private Map<Integer, Field<Customer>> customerMap;
        private final Integer k;

        public WhosyourDady(Integer k) {
            this.k = k;
            standBy = new HeapGreater<>(new StandComparator());
            bingo = new HeapGreater<>(new BingoComparator());
        }

        // 调整
        // 事件开始m每个事件的时间-i  id-arr数组中id  buyOrRefund-op数组中购买或者退货
        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customerMap.containsKey(id)) {
                // 如果此人不在map中并且是退货操作，获奖区和候选区不变,此操作直接终止，进行下一操作
                return;
            }
            // 不在map中，但是是购买操作，说明是新用户，先加入map，后续设置购买数，时间且加入候选区/获奖区
            if (!customerMap.containsKey(id)) {
                customerMap.put(id, new Field<>(new Customer(id, 0, 0)));
            }
            // 现在肯定在map中--新用户购买/老用户购买退货，说明要调整候选区或者获奖区,开始设置购买数量等
            Field<Customer> field = customerMap.get(id);
            if (buyOrRefund) {
                // 根据传入的购买/退货调整购买数量，新加入的用户肯定是购买操作
                field.value.buyCount++;
            } else {
                // 新加入的用户肯定是购买操作，不会触发这个
                field.value.buyCount--;
            }
            // 调整购买数量之后购买数尾0直接从map移除,候选区/获奖区操作后续调整--新用户肯定不会触发
            if ( field.value.buyCount == 0) {
                customerMap.remove(id);
            }
            // 调整候选区/获奖区
            // 如果都不包含此顾客，说明是新用户
            if (!standBy.containsKey(field) && !bingo.containsKey(field)) {
                if (bingo.size() < k) {
                    // 如果获奖区没满，设置时间后直接加入获奖区
                    field.value.time = time;
                    bingo.push(field);
                } else {
                    // 奖区满了，加入候选区
                    field.value.time = time;
                    standBy.push(field);
                }
            } else if (standBy.containsKey(field)) {
                // 如果是老用户，并且已经在在候选区堆里面
                if ( field.value.buyCount == 0) {
                    // 如果上面调整购买数量之后为0，移除此人从候选区
                    standBy.del(field);
                } else {
                    // 调整购买数量后！=0，则重新调整候选区堆
                    standBy.resign(field);
                }
            } else {
                // 老用户，在获奖区中
                if ( field.value.buyCount == 0) {
                    // 购买数为0，移除
                    bingo.del(field);
                } else {
                    // 购买数不为0，重新排序获奖区堆
                    bingo.resign(field);
                }
            }
            // 必走步骤， 看候选区和获奖区是否需要互换人，并且换过之后以前的人的time也必须设置为当前time
            ifMove(time);
        }

        // 调整后
        public void ifMove(int time) {
            // 候选区为空
            if (standBy.isEmpty()) {
                // 如果候选区为空，说明原本老用户可能在候选区调整后被移除后候选区空。
                // 或者候选区本来就空的，老用户原本在获奖区调整后被移除
                // 或者候选区本来就空的，新用户直接进了获奖区，总之不需要换人
                return;
            }

            // 候选区不为空
            if (bingo.size() < k) {
                // 如果调整后获奖区不满，则说明原本老用户在获奖区，调整后被移除了
                // 这时候需要从候选区直接调第一个人去获奖区
                Field<Customer> field = standBy.poll();
                field.value.time = time;
                bingo.push(field);
            } else {
                // 获奖区满了，说明老用户原本在候选区，调整后可能被移除，也可能没有被移除。
                // 或者新用户直接进了候选区
                // 或者老用户原本在获奖区，调整后购买数不为0没有被移除。
                // 此时都需要判断候选区第一个能不能替换获奖区第一个
                if (standBy.peek().value.buyCount > bingo.peek().value.buyCount) {
                    // 可以替换
                    Field<Customer> newField = standBy.poll();
                    Field<Customer> oldField = bingo.poll();
                    newField.value.time = time;
                    oldField.value.time = time;
                    // 重新排序而不是放在第一个位置，毕竟购买数一样的话，时间从小到大，照顾老用户，而新替换的时间都是新的
                    standBy.push(oldField);
                    bingo.push(newField);
                }
            }
        }
    }


}
