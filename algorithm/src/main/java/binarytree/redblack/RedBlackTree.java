package binarytree.redblack;

import binarytree.BTNode;
import binarytree.SearchBinaryTree;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/16
 */
public class RedBlackTree<K extends Comparable<K>, V> extends SearchBinaryTree<K, V> {

    /**
     * 节点构造扩展接口
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    protected BTNode<K, V> constructor(K key, V value) {
        return new RedBlackNode<>(key, value);
    }

    /**
     * 添加节点扩展接口
     *
     * @param newNode
     */
    @Override
    protected void postInsert(BTNode<K, V> newNode) {
        fixupInsert((RedBlackNode<K, V>) newNode);
    }

    /**
     * 删除节点
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        RedBlackNode<K, V> node = (RedBlackNode<K, V>) findNodeToInsert(key);
        if (node != null && node.isEqualsKey(key)) {
            V previousVal = node.getValue();
            size--;
            fixupDelete(node);
            return previousVal;
        }
        return null;
    }

    /**
     * 修复红黑树（插入新节点之后操作）
     * @param node
     */
    private void fixupInsert(RedBlackNode<K, V> node) {
        RedBlackNode<K, V> cursor = node;
        while (true) {
            RedBlackNode<K, V> parent = (RedBlackNode<K, V>) cursor.getParent();
            boolean pIsRed = parent != null && parent.getColor() == Color.RED;
            if (! pIsRed) {
                break;
            }

            if (parent == head) {
                break;
            }

            boolean pIsLeft = parent == parent.getParent().getLeft();
            RedBlackNode<K, V> uncle = (RedBlackNode<K, V>) (pIsLeft ? parent.getParent().getRight() : parent.getParent().getLeft());
            boolean uncleIsRed = uncle != null && uncle.getColor() == Color.RED;

            if (uncleIsRed) {
                // 【情况 1】变色
                parent.setColor(Color.BLACK);
                uncle.setColor(Color.BLACK);
                RedBlackNode<K, V> grandpa = (RedBlackNode<K, V>) uncle.getParent();
                grandpa.setColor(Color.RED);
                cursor = grandpa;
            } else if (pIsLeft) {
                boolean curIsRight = cursor == parent.getRight();
                if (curIsRight) {
                    // 【情况2 => 转情况 3】左旋
                    leftRotate(parent);
                    cursor = (RedBlackNode<K, V>) cursor.getLeft();
                    parent = (RedBlackNode<K, V>) cursor.getParent();
                }
                // 【情况3】右旋 + 变色
                parent.setColor(Color.BLACK);
                RedBlackNode<K, V> grandpa = (RedBlackNode<K, V>) parent.getParent();
                grandpa.setColor(Color.RED);
                rightRotate(grandpa);
                break;
            } else {
                // 和 pIsLeft 一样，只是 left 变 right , right 变 left
                boolean curIsLeft = cursor == parent.getLeft();
                if (curIsLeft) {
                    rightRotate(parent);
                    cursor = (RedBlackNode<K, V>) cursor.getRight();
                }
                ((RedBlackNode<K, V>)cursor.getParent()).setColor(Color.BLACK);
                ((RedBlackNode)(cursor.getParent().getParent())).setColor(Color.RED);
                leftRotate(cursor.getParent().getParent());
                break;
            }
        }
        ((RedBlackNode<K, V>) head).setColor(Color.BLACK);
    }


    /**
     * 修复红黑树(删除节点之后)
     * @param z
     */
    private void fixupDelete(RedBlackNode<K, V> z) {
        int zChildCount = (z.getLeft() != null ? 1 : 0) + (z.getRight() != null ? 1 : 0);
        if (zChildCount == 0) {
            boolean zIsRed = Color.RED == z.getColor();
            if (zIsRed) {
                // 删除
                if (z.getParent().getLeft() == z) {
                    z.getParent().setLeft(null);
                } else {
                    z.getParent().setRight(null);
                }
            } else {
                if (z.getParent() == null) {
                    head = null;
                    z.setParent(null);
                    return;
                }
                while (true) {
                    if (z.getParent() == null) {
                        assert z.getWeight() > 1;
                        z.setWeight(1);
                        break;
                    }
                    boolean zIsLeft = z == z.getParent().getLeft();
                    RedBlackNode<K, V> brother = (RedBlackNode<K, V>) (zIsLeft ? z.getParent().getRight() : z.getParent().getLeft());
                    boolean brotherIsRed = Color.RED == brother.getColor();

                    if (brotherIsRed) {
                        // [情况四] 兄为红
                        ((RedBlackNode<K, V>)z.getParent()).setColor(Color.RED);
                        brother.setColor(Color.BLACK);
                        if (zIsLeft) {
                            leftRotate(z.getParent());
                        } else {
                            rightRotate(z.getParent());
                        }
                        // 情况四 => 情况一,二,三之一
                    } else {
                        boolean brotherIsLeft = brother == brother.getParent().getLeft();
                        // 兄同侧子(用 son 表示)
                        RedBlackNode<K, V> son = (RedBlackNode<K, V>) (brotherIsLeft ? brother.getLeft() : brother.getRight());
                        // 兄异侧子(用 daughter 表示)
                        RedBlackNode<K, V> daughter = (RedBlackNode<K, V>) (brotherIsLeft ? brother.getRight() : brother.getLeft());
                        boolean sonIsRed = son != null && Color.RED == son.getColor();
                        boolean daughterIsRed = daughter != null && Color.RED == daughter.getColor();
                        if (sonIsRed) {
                            // [情况一] 兄为黑,且同侧子为红
                            RedBlackNode<K, V> brotherParent = (RedBlackNode<K, V>) brother.getParent();
                            brother.setColor(brotherParent.getColor());
                            brotherParent.setColor(Color.BLACK);
                            son.setColor(Color.BLACK);
                            if (zIsLeft) {
                                leftRotate(brotherParent);
                            } else {
                                rightRotate(brotherParent);
                            }

                            if (z.getWeight() > 1) {
                                // 减去一层权重
                                z.setWeight(1);
                            } else {
                                if (zIsLeft) {
                                    z.getParent().setLeft(null);
                                } else {
                                    z.getParent().setRight(null);
                                }
                                z.setParent(null);
                            }
                            break;

                        } else if (daughterIsRed) {
                            // [情况二] 兄为黑,同侧子为黑,异侧子为红
                            brother.setColor(Color.RED);
                            daughter.setColor(Color.BLACK);
                            if (zIsLeft) {
                                rightRotate(brother);
                            } else {
                                leftRotate(brother);
                            }
                            // 情况二 => 情况一, 递归进入情况一
                        } else {
                            // [情况三] 兄为黑,兄无红子
                            RedBlackNode<K, V> father = (RedBlackNode<K, V>) brother.getParent();
                            boolean fatherIsRed = Color.RED == father.getColor();
                            if (fatherIsRed) {
                                // 变色 + 删除 Z
                                father.setColor(Color.BLACK);
                                brother.setColor(Color.RED);

                                if (z.getWeight() > 1) {
                                    z.setWeight(1);
                                } else {
                                    z.setParent(null);
                                    if (zIsLeft) {
                                        father.setLeft(null);
                                    } else {
                                        father.setRight(null);
                                    }
                                }
                                break;
                            } else {
                                // 变色 + 删除 Z + 加权重
                                father.setWeight(2);
                                brother.setColor(Color.RED);

                                if (z.getWeight() > 1) {
                                    z.setWeight(1);
                                } else {
                                    z.setParent(null);
                                    if (zIsLeft) {
                                        father.setLeft(null);
                                    } else {
                                        father.setRight(null);
                                    }
                                }
                                // father 成为新的 z
                                z = father;
                            }

                        }
                    }
                }

            }
        } else if (zChildCount == 1) {
            // 根据红黑树性质
            // z 必为 黑色, 且子节点必为红色
            RedBlackNode<K, V> zUniqueChild = (RedBlackNode<K, V>) (z.getLeft() != null ? z.getLeft() : z.getRight());
            z.setKey(zUniqueChild.getKey());
            z.setValue(zUniqueChild.getValue());
            zUniqueChild.setKey(null);
            zUniqueChild.setValue(null);
            // 递归调用必进 zChildCount == 0 且节点为红,必被删
            fixupDelete(zUniqueChild);
        } else {
            // zChildCount == 2
            RedBlackNode<K, V> successor = (RedBlackNode<K, V>) successor(z);
            // successor 仅可能存在一个右红子节点
            z.setKey(successor.getKey());
            z.setValue(successor.getValue());
            // 递归调用必进 zChildCount <= 1 , 若有子节点,子节点为红
            fixupDelete(successor);
        }
    }
}
