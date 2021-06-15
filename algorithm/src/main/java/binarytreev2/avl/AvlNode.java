package binarytreev2.avl;

import binarytreev2.BTNode;

/**
 * @author xuliangliang
 * @Description avl 节点
 * @Date 2021/6/15
 */
public class AvlNode<K extends Comparable<K>, V> extends BTNode<K, V> {
    /**
     * 平衡因子（树高）
     */
    private Integer height;

    public AvlNode(K key, V value) {
        super(key, value);
        this.height = 1;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
