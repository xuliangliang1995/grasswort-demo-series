package skiplist;

import java.util.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/14
 */
public class SkipList<K extends Comparable<K>, V> {

    private final double ODDS = 0.5;

    private SkipListNode<K, V> head;

    private int size = 0;

    private int level = 0;

    private int maxLevel = Integer.MAX_VALUE;

    public SkipList() {
        this.head = new SkipListNode<>(null, null);
        this.head.nextNodes.add(null);
    }

    public SkipList(int maxLevel) {
        this.head = new SkipListNode<>(null, null);
        this.head.nextNodes.add(null);
        this.maxLevel = maxLevel;
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public V get(K key) {
        SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
        SkipListNode<K, V> nextNode = lessNode.nextNodes.get(0);
        return nextNode != null && nextNode.isKeyEquals(key) ? nextNode.v : null;
    }

    /**
     * 放入/更新
     * @param key
     * @param value
     */
    public V put(K key, V value) {
        SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
        if (lessNode != null
                && lessNode.nextNodes.get(0) != null
                && lessNode.nextNodes.get(0).isKeyEquals(key)) {
            // 更新
            V preV = lessNode.nextNodes.get(0).v;
            lessNode.nextNodes.get(0).v = value;
            return preV;
        }

        int newLevel = 0;
        while (newLevel < maxLevel && Math.random() < ODDS) {
            newLevel++;
            if (level < newLevel) {
                head.nextNodes.add(null);
                level++;
            }
        }
        SkipListNode<K, V> newNode = new SkipListNode<>(key, value);

        lessNode = head;
        while (newLevel >= 0) {
            lessNode = mostRightLessNodeInTheLevel(key, lessNode, newLevel);
            SkipListNode<K, V> nextNode = lessNode.nextNodes.get(newLevel);
            lessNode.nextNodes.set(newLevel, newNode);
            newNode.nextNodes.add(nextNode);
            newLevel--;
        }

        size++;
        return null;
    }

    /**
     * 移除
     * @param key
     */
    public V remove(K key) {
        int traversedLevel = level;
        SkipListNode<K, V> lessNode = head;
        V removedValue = null;
        while (traversedLevel >= 0) {
            lessNode = mostRightLessNodeInTheLevel(key, lessNode, traversedLevel);
            SkipListNode<K, V> nextNode = lessNode.nextNodes.get(traversedLevel);
            if (nextNode != null && nextNode.isKeyEquals(key)) {
                // 删除本层节点
                lessNode.nextNodes.set(traversedLevel, nextNode.nextNodes.get(traversedLevel));
                removedValue = nextNode.v;
            }
            if (lessNode.isKeyEquals(null) && lessNode.nextNodes.get(traversedLevel) == null && level > 0) {
                // 降层
                head.nextNodes.remove(traversedLevel);
                level--;
            }
            traversedLevel--;
        }
        if (removedValue != null) {
            size--;
        }
        return removedValue;
    }

    /**
     * 是否包含 key
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        SkipListNode<K, V> lessNode = mostRightLessNodeInTree(key);
        return lessNode.nextNodes.get(0) != null
                && lessNode.nextNodes.get(0).isKeyEquals(key);
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int level() {
        return level;
    }

    /**
     * Node Of SkipList
     * @param <K>
     * @param <V>
     */
    static class SkipListNode<K extends Comparable<K>, V> {
        private K k;
        private V v;
        private List<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            this.k = k;
            this.v = v;
            this.nextNodes = new ArrayList<>();
        }

        /**
         * 是否比其他 key 小
         * @param otherKey
         * @return
         */
        public boolean isKeyLess(K otherKey) {
            // k == null 时, 为 head 节点, head 小于所有节点
            return otherKey != null && (k == null || k.compareTo(otherKey) < 0);
        }

        /**
         * 是否等于其他 key
         * @param otherKey
         * @return
         */
        public boolean isKeyEquals(K otherKey) {
            return (k == null && otherKey == null)
                    || (k != null && otherKey != null && k.compareTo(otherKey) == 0);
        }
    }

    /**
     * 寻找小于 key 的树中最右节点
     * @param key
     * @return
     */
    private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
        if (key == null) {
            return null;
        }
        int traversedLevel = this.level;
        SkipListNode<K, V> cur = head;
        while (traversedLevel >= 0) {
            cur = mostRightLessNodeInTheLevel(key, cur, traversedLevel--);
        }
        return cur;
    }

    /**
     * 寻找指定层中小于 key 的最右节点
     * @param key
     * @param current
     * @param level
     * @return
     */
    private SkipListNode<K, V> mostRightLessNodeInTheLevel(K key, SkipListNode<K, V> current, int level) {
        SkipListNode<K, V> next = current.nextNodes.get(level);
        while (next != null && next.isKeyLess(key)) {
            current = next;
            next = current.nextNodes.get(level);
        }
        return current;
    }
}
