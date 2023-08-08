package class07.myclass07;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapGreater<T> {
    /**
     * 加强堆
     * 普通系统提供的堆，如果按照对象属性值存储自定义对象，存储之后，改变堆中某个对象的属性值。则会使得堆失效。
     * 因为系统没有提供反向索引表，即记录每个对象在堆(数组)中的位置
     * 无法做到高效删除。
     * <p>
     * 解决方法：手动提供一个Map，记录对象及其堆中位置。
     * 更新对象时一并重新对堆中该位置的对象上浮/下沉
     * 删除对象时根据Map定位堆中下标，将该下标位置的对象与堆中最后一个位置的对象交换，然后交换后再上浮/下沉
     * 交换方法除了交换对象，还需要在Map中将存储位置交换
     */

    // 防止基本数据类型包装类在Map中以基本数据类型存储导致不存在重复对象，两个对象位置也就被覆盖掉只有一个。
    // 将属性套一层Field类，将Field类对象作为放入Map，hash结果不同就会被认为是不同的对象


    private ArrayList<Field<T>> heap;
    private HashMap<Field<T>, Integer> map;
    private Integer size;
    private Comparator<? super Field<T>> comparator;

    public HeapGreater(Comparator<Field<T>> comparator) {
        this.comparator = comparator;
        heap = new ArrayList<>();
        map = new HashMap<>();
        size = 0;
    }

    public void swap(int i, int j) {
        Field<T> o1 = heap.get(i);
        Field<T> o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        map.put(o1, j);
        map.put(o2, i);
    }

    // 上浮
    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) > 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // 下沉
    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < size) {
            int maxChild = left + 1 < size && comparator.compare(heap.get(left), heap.get(left + 1)) < 0 ? left + 1 : left;
            if (comparator.compare(heap.get(index), heap.get(maxChild)) >= 0) {
                break;
            }
            swap(index, maxChild);
            index = maxChild;
            left = index * 2 + 1;
        }
    }

    public void push(Field<T> field) {
        heap.add(size, field);
        map.put(field, size);
        heapify(size++);
    }

    public Field<T> poll() {
        Field<T> field = heap.get(0);
        swap(0, size - 1);
        map.remove(field);
        // 动态数组最好不用的删除掉
        heap.remove(--size);
        heapify(0);
        return field;
    }

    public void del(Field<T> field) {
        Field<T> endField = heap.get(size - 1);
        Integer fieldIndex = map.get(field);
        map.remove(field);
        heap.remove(--size);
        if (field != endField) {
            heap.set(fieldIndex, endField);
            map.put(endField, fieldIndex);
            heapInsert(fieldIndex);
            heapify(fieldIndex);
        }
    }

    public void resign(Field<T> field) {
        heapInsert(map.get(field));
        heapify(map.get(field));
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return size;
    }

    public boolean containsKey(Field<T> field) {
        return map.containsKey(field);
    }

    public Field<T> peek() {
        return heap.get(0);
    }


}
