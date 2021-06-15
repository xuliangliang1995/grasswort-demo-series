package binarytreev2;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/15
 */
public interface SortedMap<K extends Comparable<K>, V> {

    V put(K key, V value);

    V remove(K key);

    boolean containsKey(K key);

    V get(K key);

    int size();
}
