package codetop;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuliangliang
 * @Description LRU 缓存
 * @Date 2021/8/15
 * 输入
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 输出
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 *
 * 解释
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // 缓存是 {1=1}
 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
 * lRUCache.get(1);    // 返回 1
 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * lRUCache.get(2);    // 返回 -1 (未找到)
 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * lRUCache.get(1);    // 返回 -1 (未找到)
 * lRUCache.get(3);    // 返回 3
 * lRUCache.get(4);    // 返回 4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/lru-cache
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LRUCache {

    private final Map<Integer, Node> cacheMap = new HashMap<>();

    private final int capacity;

    private Node head;

    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        // 1. 判断 key 是否存在？不存在 -> -1
        Node node = cacheMap.get(key);
        if (node == null) {
            return -1;
        }
        // 2. 置于链表结尾
        if (tail != node) {
            // (1) unlink
            // pre may be null
            Node pre = node.pre;
            // next != null
            Node next = node.next;
            next.pre = pre;
            if (pre != null) {
                pre.next = next;
            } else {
                head = next;
            }
            node.pre = null;
            node.next = null;
            // (2) link to tail
            tail.next = node;
            node.pre = tail;
            tail = node;
        }
        return node.value;
    }

    public void put(int key, int value) {
        // 1. 判断是否存在？ 存在 -> 更新
        Node node = cacheMap.get(key);
        if (node != null) {
            node.value = value;
            return;
        }
        // 2. 判断缓存是否已满? 满，则删掉头
        boolean overflow = cacheMap.size() >= capacity;
        if (overflow) {
            Node preHead = head;
            cacheMap.remove(preHead.key);
            boolean tailEqualsHead = tail == preHead;
            if (tailEqualsHead) {
                head = null;
                tail = null;
            } else {
                head = preHead.next;
                head.pre = null;
            }
            preHead.pre = null;
            preHead.next = null;
        }
        // 3. 不满，添至尾
        node = new Node(key, value);
        Node preTail = tail;
        if (preTail != null) {
            preTail.next = node;
            node.pre = preTail;
            tail = node;
        } else {
            head = node;
            tail = node;
        }
        cacheMap.put(key, node);
    }


    static class Node {
        private int key;
        private int value;
        private Node pre;
        private Node next;

        public Node() {
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
