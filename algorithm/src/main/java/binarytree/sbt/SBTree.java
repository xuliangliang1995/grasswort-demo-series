package binarytree.sbt;

import binarytree.BTNode;
import binarytree.SearchBinaryTree;

/**
 * @author xuliangliang
 * @Description SBT
 * @Date 2021/6/16
 */
public class SBTree<K extends Comparable<K>, V> extends SearchBinaryTree<K, V> {

    /**
     * 节点构造扩展接口
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    protected BTNode<K, V> constructor(K key, V value) {
        return new SBNode<>(key, value);
    }

    /**
     * 添加节点扩展接口
     *
     * @param newNode
     */
    @Override
    protected void postInsert(BTNode<K, V> newNode) {
        regulateSize((SBNode<K, V>) newNode);
        SBNode<K, V> cur = balance((SBNode<K, V>) newNode);
        while (cur.getParent() != null) {
            cur = balance((SBNode<K, V>) cur.getParent());
        }
    }


    /**
     * 校正节点 Size(保证子节点 Size 正确)
     * @param node
     */
    private void regulateSize(SBNode<K, V> node) {
        if (node == null) {
            return;
        }
        SBNode<K, V> left = (SBNode<K, V>) node.getLeft();
        SBNode<K, V> right = (SBNode<K, V>) node.getRight();
        int size = (left != null ? left.getSize() : 0) + (right != null ? right.getSize() : 0);
        int oldSize = node.getSize();
        node.setSize(size);
        if (size != oldSize) {
            regulateSize((SBNode<K, V>) node.getParent());
        }
    }

    /**
     * 平衡
     * @param node
     */
    private SBNode<K, V> balance(SBNode<K, V> node) {
        SBNode<K, V> cur = node;
        SBNode<K, V> left = (SBNode<K, V>) node.getLeft();
        SBNode<K, V> right = (SBNode<K, V>) node.getRight();
        SBNode<K, V> rightRight = right != null ? (SBNode<K, V>) right.getRight() : null;
        SBNode<K, V> rightLeft = right != null ? (SBNode<K, V>) right.getLeft() : null;
        SBNode<K, V> leftLeft = left != null ? (SBNode<K, V>) left.getLeft() : null;
        SBNode<K, V> leftRight = left != null ? (SBNode<K, V>) left.getRight() : null;

        boolean isLL = (leftLeft != null ? leftLeft.getSize() : 0) > (right != null ? right.getSize() : 0);
        if (isLL) {
            cur = (SBNode<K, V>) rightRotate(node);
            balance((SBNode<K, V>) cur.getRight());
            cur = balance(cur);
            return cur;
        }

        boolean isLR = (leftRight != null ? leftRight.getSize() : 0) > (right != null ? right.getSize() : 0);
        if (isLR) {
            leftRotate(left);
            cur = (SBNode<K, V>) rightRotate(node);
            balance((SBNode<K, V>) cur.getLeft());
            balance((SBNode<K, V>) cur.getRight());
            cur = balance(cur);
            return cur;
        }

        boolean isRR = (rightRight != null ? rightRight.getSize() : 0) > (left != null ? left.getSize() : 0);
        if (isRR) {
            cur = (SBNode<K, V>) leftRotate(node);
            balance((SBNode<K, V>) cur.getLeft());
            cur = balance(cur);
            return cur;
        }

        boolean isRL = (rightLeft != null ? rightLeft.getSize() : 0) > (left != null ? left.getSize() : 0);
        if (isRL) {
            rightRotate(right);
            cur = (SBNode<K, V>) leftRotate(node);
            balance((SBNode<K, V>) cur.getLeft());
            balance((SBNode<K, V>) cur.getRight());
            cur = balance(cur);
            return cur;
        }

        return cur;
    }


    /**
     * 左旋
     *
     * @param node
     */
    @Override
    protected BTNode<K, V> leftRotate(BTNode<K, V> node) {
        BTNode<K, V> topoHead = super.leftRotate(node);
        regulateSize((SBNode<K, V>) node);
        return topoHead;
    }

    /**
     * 右旋
     *
     * @param node
     */
    @Override
    protected BTNode<K, V> rightRotate(BTNode<K, V> node) {
        BTNode<K, V> topoHead = super.rightRotate(node);
        regulateSize((SBNode<K, V>) node);
        return topoHead;
    }
}
