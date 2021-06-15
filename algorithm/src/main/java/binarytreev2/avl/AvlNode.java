package binarytreev2.avl;

import binarytreev2.BTNode;

/**
 * @author xuliangliang
 * @Description avl 节点
 * @Date 2021/6/15
 */
public class AvlNode<K, V> extends BTNode {
    /**
     * 平衡因子（树高）
     */
    private Integer height;

    public AvlNode(Comparable key, Object value) {
        super(key, value);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
