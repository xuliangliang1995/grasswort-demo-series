package hash.map;

import binarytree.redblack.RedBlackNode;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/24
 */
public class HashesMap<K, V> implements Map<K, V> {

    private double loadFactor = 0.75;

    private int capacity;

    public HashesMap(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }


    static class Node<K, V> implements Map.Entry<K, V> {

        final int hash;

        final K key;

        V value;

        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V preV = this.value;
            this.value = value;
            return preV;
        }
    }


    static class TreeNode<K, V> extends Node<K, V> {


        public TreeNode(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }
}
