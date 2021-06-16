package binarytree.redblack;

import binarytree.BTNode;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/16
 */
public class RedBlackNode<K extends Comparable<K>, V> extends BTNode<K, V> {

    private Color color;

    private Integer weight;

    public RedBlackNode(K key, V value) {
        super(key, value);
        this.color = Color.RED;
        this.weight = 1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
