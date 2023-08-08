package class07.myclass072;

import java.util.*;

public class LuckDraw {

    public List<List<Integer>> startDraw(int[] arr, boolean[] op, int k) {
        List<List<Integer>> drawTable = new ArrayList<>();
        StartDraw startDraw = new StartDraw(k);
        for (int i = 0; i < arr.length; i++) {
            startDraw.start(arr[i], op[i], i);
            drawTable.add(startDraw.getDraws());
        }
        return drawTable;
    }


    public class StartDraw {
        private Integer k;
        private HeapGreater<Customer> waitingHeap;
        private HeapGreater<Customer> bingoHeap;
        private Map<Integer, Field<Customer>> everyBingoCustomersMap;

        public ArrayList<Integer> getDraws() {
            ArrayList<Integer> list = new ArrayList<>();
            List<Field<Customer>> fieldList = bingoHeap.getAllElements();
            for (Field<Customer> field : fieldList) {
                list.add(field.value.id);
            }
            return list;
        }

        public class WaitingComparator implements Comparator<Field<Customer>> {

            @Override
            public int compare(Field<Customer> o1, Field<Customer> o2) {
                return !Objects.equals(o1.value.buyCount, o2.value.buyCount) ? o2.value.buyCount - o1.value.buyCount : o1.value.time - o2.value.time;
            }
        }

        public class BingoComparator implements Comparator<Field<Customer>> {

            @Override
            public int compare(Field<Customer> o1, Field<Customer> o2) {
                return !Objects.equals(o1.value.buyCount, o2.value.buyCount) ? o1.value.buyCount - o2.value.buyCount : o1.value.time - o2.value.time;
            }
        }

        public StartDraw(Integer k) {
            this.k = k;
            waitingHeap = new HeapGreater<>(new WaitingComparator());
            bingoHeap = new HeapGreater<>(new BingoComparator());
            everyBingoCustomersMap = new HashMap<>();
        }

        public void start(int id, boolean buyOrReturn, int time) {
            if (!buyOrReturn && !everyBingoCustomersMap.containsKey(id)) {
                return;
            }
            if (!everyBingoCustomersMap.containsKey(id)) {
                everyBingoCustomersMap.put(id, new Field<>(new Customer(id, 0, 0)));
            }
            Field<Customer> customerField = everyBingoCustomersMap.get(id);
            if (buyOrReturn) {
                customerField.value.buyCount++;
            } else {
                customerField.value.buyCount--;
            }
            if (customerField.value.buyCount == 0) {
                everyBingoCustomersMap.remove(id);
            }
            if (!bingoHeap.contains(customerField) && !waitingHeap.contains(customerField)) {
                if (bingoHeap.size() < k) {
                    customerField.value.time = time;
                    bingoHeap.push(customerField);
                } else {
                    customerField.value.time = time;
                    waitingHeap.push(customerField);
                }
            } else if (bingoHeap.contains(customerField)) {
                if (customerField.value.buyCount == 0) {
                    bingoHeap.del(customerField);
                } else {
                    bingoHeap.resign(customerField);
                }
            } else {
                if (customerField.value.buyCount == 0) {
                    bingoHeap.del(customerField);
                } else {
                    waitingHeap.resign(customerField);
                }
            }

            ifMove(time);
        }

        public void ifMove(int time) {
            if (waitingHeap.isEmpty()) {
                return;
            }
            if (bingoHeap.size() < k) {
                Field<Customer> customerField = waitingHeap.poll();
                customerField.value.time = time;
                bingoHeap.push(customerField);
            } else {
                if (waitingHeap.peek().value.buyCount > bingoHeap.peek().value.buyCount) {
                    Field<Customer> customerField1 = waitingHeap.poll();
                    customerField1.value.time = time;
                    Field<Customer> customerField2 = bingoHeap.poll();
                    customerField2.value.time = time;
                    waitingHeap.push(customerField2);
                    bingoHeap.push(customerField1);
                }
            }
        }
    }


}
