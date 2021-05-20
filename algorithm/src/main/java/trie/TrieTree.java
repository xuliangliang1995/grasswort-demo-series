package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuliangliang
 * @Description 字典树(前缀树)
 * 不考虑空白字符串,也没有做多余校验
 * @Date 2020/7/19
 */
public class TrieTree {
    /**
     * 根节点
     */
    private final Node root = new Node();

    class Node {
        /**
         * 通过该节点的数量
         */
        private int pass;
        /**
         * 在该节点终止的数量
         */
        private int end;
        /**
         * 后续节点
         */
        private Map<Integer, Node> next;
    }

    /**
     * 添加字符串
     * @param str
     */
    public void add(String str) {
        char[] chars = str.toCharArray();
        Node current = root;
        pass(root);
        for (int i = 0; i < chars.length ; i++) {
            Node nextNode = nextNode(current, chars[i]);

            if (i == chars.length - 1) {
                passThenEnd(nextNode);
                return;
            }

            pass(nextNode);
            current = nextNode;
        }
    }

    /**
     * 查找字符串数量
     * @param str
     * @return
     */
    public int search(String str) {
        char[] chars = str.toCharArray();
        Node current = root;
        for (int i = 0; i < chars.length ; i++) {
            char c = chars[i];
            if (current.next.get((int) c) == null) {
                return 0;
            }
            current = current.next.get((int) c);
        }
        return current == root ? 0 : current.end;
    }

    /**
     * 容量
     * @return
     */
    public int size() {
        return root.pass;
    }

    /**
     * 删除字符串并返回删除数量
     * @param str
     * @return
     */
    public int remove(String str) {
        int count = search(str);
        if (count > 0) {
            char[] chars = str.toCharArray();
            Node current = root;
            root.pass -= count;
            for (int i = 0; i < chars.length ; i++) {
                Node nextNode = nextNode(current, chars[i]);
                nextNode.pass -= count;
                nextNode.end -= count;
                if (nextNode.pass == 0) {
                    current.next.remove((int) (chars[i]));
                }
                current = nextNode;
            }
        }
        return count;
    }

    /**
     * 创建节点,如果不存在的话
     * @param current
     * @param c
     * @return
     */
    private Node nextNode(Node current, char c) {
        if (null == current.next) {
            current.next = new HashMap<>(5);
        }
        if (null == current.next.get((int) c)) {
            current.next.put((int) c, new Node());
        }
        return current.next.get((int) c);
    }

    /**
     * 路过
     * @param node
     */
    private void pass(Node node) {
        node.pass++;
    }

    /**
     * 终止
     * @param node
     */
    private void passThenEnd(Node node) {
        pass(node);
        node.end++;
    }
}
