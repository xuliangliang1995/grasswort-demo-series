package binarytree.sbt;

import binarytree.BTNode;

/**
 * @author xuliangliang
 * @Description SBT node
 * @Date 2021/6/16
 */
public class SBNode<K extends Comparable<K>, V> extends BTNode<K, V> {

    /**
     * 平衡因子（子树节点数）
     */
    private Integer size;

    public SBNode(K key, V value) {
        super(key, value);
        this.size = 0;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
