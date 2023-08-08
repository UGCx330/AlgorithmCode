package class07.myclass072;

import java.io.LineNumberReader;
import java.util.*;

public class HeapGreater<T> {
    private Integer size;
    private ArrayList<Field<T>> heap;
    private Map<Field<T>, Integer> indexTable;
    private Comparator<? super Field<T>> comparator;

    public HeapGreater(Comparator<Field<T>> comparator) {
        this.comparator = comparator;
        heap = new ArrayList<>();
        indexTable = new HashMap<>();
        size = 0;
    }

    public void swap(int i, int j) {
        Field<T> iField = heap.get(i);
        Field<T> jField = heap.get(j);
        heap.set(i, jField);
        heap.set(j, iField);
        indexTable.put(jField, i);
        indexTable.put(iField, j);
    }

    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < size) {
            int best = left + 1 < size && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            best = comparator.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    public void resign(Field<T> field) {
        Integer index = indexTable.get(field);
        heapInsert(index);
        heapify(index);
    }

    public void push(Field<T> field) {
        heap.add(field);
        indexTable.put(field, size);
        heapInsert(size++);
    }

    public Field<T> poll() {
        Field<T> value = heap.get(0);
        swap(0, size - 1);
        indexTable.remove(value);
        heap.remove(--size);
        heapify(0);
        return value;
    }

    public void del(Field<T> field) {
        int delIndex = indexTable.get(field);
        Field<T> lastField = heap.get(size - 1);
        heap.remove(--size);
        indexTable.remove(field);
        if (field != lastField) {
            heap.set(delIndex, lastField);
            indexTable.put(lastField, delIndex);
            resign(lastField);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(Field<T> field) {
        return indexTable.containsKey(field);
    }

    public Field<T> peek() {
        return heap.get(0);
    }

    public List<Field<T>> getAllElements() {
        ArrayList<Field<T>> arrayList = new ArrayList<>();
        for (Field<T> field : heap) {
            arrayList.add(field);
        }
        return arrayList;
    }
}
