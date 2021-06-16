package binarytree;

/**
 * @author xuliangliang
 * @Description 二叉树节点
 * @Date 2021/6/15
 */
public class BTNode<K extends Comparable<K>, V> {

    private K key;

    private V value;

    private BTNode<K, V> parent;

    private BTNode<K, V> left;

    private BTNode<K, V> right;

    public BTNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public BTNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BTNode<K, V> parent) {
        this.parent = parent;
    }

    public BTNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BTNode<K, V> left) {
        this.left = left;
    }

    public BTNode<K, V> getRight() {
        return right;
    }

    public void setRight(BTNode<K, V> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return key.toString();
    }

    public boolean isLessThan(K otherKey) {
        return otherKey != null && key != null
                && key.compareTo(otherKey) < 0;
    }

    public boolean isMoreThan(K otherKey) {
        return otherKey != null && key != null
                && key.compareTo(otherKey) > 0;
    }

    public boolean isEqualsKey(K otherKey) {
        return key != null && otherKey != null
                && key.compareTo(otherKey) == 0;
    }
}
