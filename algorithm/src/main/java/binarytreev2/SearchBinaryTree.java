package binarytreev2;

/**
 * @author xuliangliang
 * @Description Search Binary Tree
 * @Date 2021/6/15
 */
public class SearchBinaryTree<K extends Comparable<K>, V> implements SortedMap<K, V> {

    private BTNode<K, V> head;

    private int size = 0;

    @Override
    public V put(K key, V value) {
        BTNode<K, V> node = findNodeToInsert(key);

        if (node != null && node.isEqualsKey(key)) {
            // update
            V previousValue = node.getValue();
            node.setValue(value);
            return previousValue;
        }
        size++;

        BTNode<K, V> newNode = new BTNode<>(key, value);

        if (node == null) {
            head = newNode;
            return null;
        }

        newNode.setParent(node);

        if (node.isLessThan(key)) {
            node.setRight(newNode);
        } else {
            node.setLeft(newNode);
        }
        return null;
    }

    @Override
    public V remove(K key) {
        BTNode<K, V> node = findNodeToInsert(key);
        if (node != null && node.isEqualsKey(key)) {
            // remove
            size--;
            return deleteNode(node);
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        if (head == null) {
            return false;
        }
        return findNodeToInsert(key).isEqualsKey(key);
    }

    @Override
    public V get(K key) {
        BTNode<K, V> node = findNodeToInsert(key);
        return node != null && node.isEqualsKey(key)
                ? node.getValue() : null;
    }

    @Override
    public int size() {
        return size;
    }


    /**
     * 删除节点
     * @param node
     * @return
     */
    protected V deleteNode(BTNode<K, V> node) {
        V removedValue = node.getValue();

        if (node.getLeft() != null && node.getRight() != null) {
            BTNode<K, V> successor = successor(node);
            V removedSuccessorValue = deleteNode(successor);
            node.setKey(successor.getKey());
            node.setValue(removedSuccessorValue);
        } else {
            BTNode<K, V> left = node.getLeft();
            BTNode<K, V> right = node.getRight();
            BTNode<K, V> parent = node.getParent();
            BTNode<K, V> replaceNode = left != null ? left : right;

            if (replaceNode != null) {
                replaceNode.setParent(parent);
            }

            if (parent != null) {
                boolean nodeIsLeft = parent.getLeft() == node;
                if (nodeIsLeft) {
                    parent.setLeft(replaceNode);
                } else {
                    parent.setRight(replaceNode);
                }
            } else {
                head = replaceNode;
            }

        }
        return removedValue;
    }

    /**
     * 寻找插入的节点
     * @param key
     * @return
     */
    protected BTNode<K, V> findNodeToInsert(K key) {
        BTNode<K, V> cursor = head;
        BTNode<K, V> last = null;
        while (cursor != null) {
            if (cursor.isEqualsKey(key)) {
                return cursor;
            }

            last = cursor;

            if (cursor.isLessThan(key)) {
                cursor = cursor.getRight();
            } else {
                cursor = cursor.getLeft();
            }
        }
        return last;
    }

    /**
     * 继任节点
     * @param node
     * @return
     */
    protected BTNode<K, V> successor(BTNode<K, V> node) {
        BTNode<K, V> successor = node.getRight();
        while (successor != null && successor.getLeft() != null) {
            successor = successor.getLeft();
        }
        return successor;
    }

}
