package unionset;

import java.util.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description 并查集
 * @Date 2021/6/16
 */
public class UnionSet<V extends Comparable<V>> {

    private Map<V, V> parents;

    private Map<V, Integer> sizeMap;

    public UnionSet(Set<V> values) {
        parents =  new HashMap<>(values.size());
        sizeMap = new HashMap<>(values.size());
        for (V value : values) {
            parents.put(value, value);
            sizeMap.put(value, 1);
        }
    }

    /**
     * 合并
     * @param a
     * @param b
     */
    public void union(V a, V b) {
        V aHead = findFinalParent(a);
        V bHead = findFinalParent(b);
        if (aHead == null || bHead == null || aHead == bHead) {
            return;
        }
        V big = aHead.compareTo(bHead) > 0 ? aHead : bHead;
        V small = big == aHead ? bHead : aHead;
        parents.put(small, big);
        sizeMap.put(big, sizeMap.get(small) + sizeMap.get(big));
        sizeMap.remove(small);
    }

    /**
     * 是否处于同一集合
     * @param a
     * @param b
     * @return
     */
    public boolean isSameSet(V a, V b) {
        return findFinalParent(a) == findFinalParent(b);
    }

    /**
     * 集合数量
     * @return
     */
    public int sets() {
        return sizeMap.size();
    }

    private V findFinalParent(V v) {
        if (v == null || ! parents.containsKey(v)) {
            return null;
        }
        Stack<V> stack = new Stack<>();
        V cur = v;
        V parent = null;
        while (cur != parent) {
            if (parent != null) {
                cur = parent;
            }
            stack.push(cur);
            parent = parents.get(cur);
        }
        while (!stack.isEmpty()) {
            parents.put(stack.pop(), parent);
        }
        return parent;
    }


}
