package heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuliangliang
 * @Description 加强堆
 * @Date 2021/6/23
 */
public class HeapPlus<V> {

    private ArrayList<V> heap;

    private Map<V, Integer> heapIndex;

    private Comparator<? super V> comparator;

    private int heapSize;


    /**
     * Constructor
     * @param comparator
     */
    public HeapPlus(Comparator<? super V> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.heapIndex = new HashMap<>();
    }

    /**
     * 添加元素
     * @param value
     */
    public void put(V value) {
        if (this.contains(value)) {
            throw new ElementAlreadyExistsException(value);
        }
        this.offer(value);
    }

    /**
     * 添加元素
     * @param value
     */
    public void offer(V value) {
        this.heap.add(heapSize++, value);
        this.heapIndex.put(value, heapSize - 1);
        this.heapInsert(heapSize - 1);
    }

    /**
     * 查看堆顶元素（不做边界校验）
     * @return
     */
    public V peek() {
        return this.heap.get(0);
    }

    /**
     * 弹出堆顶元素（不做边界校验）
     * @return
     */
    public V poll() {
        return this.remove(heap.get(0));
    }

    /**
     * 是否包含
     * @param value
     * @return
     */
    public boolean contains(V value) {
        return this.heapIndex.containsKey(value);
    }

    /**
     * 堆大小
     * @return
     */
    public int size() {
        return heapSize;
    }

    /**
     * 堆是否为空
     * @return
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * 移除元素
     * @param value
     */
    public V remove(V value) {
        if (contains(value)) {
            int index = this.heapIndex.get(value);
            V removed = this.heap.get(index);
            V replaced = this.heap.remove(--heapSize);
            this.heapIndex.remove(replaced);
            if (! replaced.equals(removed)) {
                this.heap.set(index, replaced);
                this.heapIndex.put(replaced, index);
                this.resign(index);
            }
            return removed;
        }
        return null;
    }

    /**
     * 重新调整位置
     * @param value
     */
    public void resign(V value) {
        if (this.contains(value)) {
            this.resign(heapIndex.get(value));
        }
    }

    /**
     * 调整堆
     * @param index
     */
    private void resign(int index) {
        this.heapInsert(index);
        this.heapify(index);
    }

    /**
     * 上升
     * @param index
     */
    private void heapInsert(int index) {
        while (comparator.compare(heap.get(parent(index)), heap.get(index)) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    /**
     * 下沉
     * @param index
     */
    private void heapify(int index) {
        V v = heap.get(index);
        int leftIndex = left(index);
        int rightIndex = right(index);
        V left = leftIndex < heapSize ? heap.get(leftIndex) : null;
        V right = rightIndex < heapSize ? heap.get(rightIndex) : null;
        if (left == null && right == null) {
            return;
        }
        boolean leftLessThanRight = right == null || (left != null && comparator.compare(left, right) < 0);
        if (leftLessThanRight) {
            if (comparator.compare(left, v) < 0) {
                swap(leftIndex, index);
                heapify(leftIndex);
            }
        } else {
            if (comparator.compare(right, v) < 0) {
                swap(rightIndex, index);
                heapify(rightIndex);
            }
        }

    }

    /**
     * 交换
     * @param a
     * @param b
     */
    private void swap(int a, int b) {
        V aVal = heap.get(a);
        V bVal = heap.get(b);
        heap.set(a, bVal);
        heap.set(b, aVal);
        heapIndex.put(aVal, b);
        heapIndex.put(bVal, a);
    }


    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return index * 2 + 2;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }


    public static class ElementAlreadyExistsException extends RuntimeException {
        private final Object e;

        public ElementAlreadyExistsException(Object e) {
            super("Element already exists : " + e);
            this.e = e;
        }
    }
}
