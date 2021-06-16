package hash.consistenthash;

import hash.rbtree.RbNode;
import hash.rbtree.RedBlackTree;
import hash.rbtree.RedBlackTreeCheckUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description 哈希环
 * @Date 2021/6/13
 */
public class HashRing {

    /**
     * 哈希环(虚拟节点升序排列)
     */
    private final RedBlackTree rbTree = new RedBlackTree();

    /**
     * 哈希虚拟节点 => 机器编号的映射
     */
    private final Map<Long, Integer> hashNodeToMachineMap = new HashMap<>(3000);


    /**
     * 根据 hash 值获取机器 id
     * @param hashValue
     * @return
     */
    public int loadBalance(long hashValue) {
        if (hashNodeToMachineMap.containsKey(hashValue)) {
            return hashNodeToMachineMap.get(hashValue);
        }
        if (rbTree.getRoot() == null) {
            return -1;
        }
        long next = next(hashValue);
        return hashNodeToMachineMap.get(next);
    }


    /**
     * 添加一个虚拟节点(如果 hashValue 已存在,则不添加并返回 false, 此种情况概率极低)
     * @param machineId
     * @param hashValue
     * @return
     */
    public synchronized VirtualNode[] addVirtualNode(int machineId, long hashValue) {
        if (hashNodeToMachineMap.containsKey(hashValue)) {
            return null;
        }
        hashNodeToMachineMap.put(hashValue, machineId);
        rbTree.addNode(hashValue);
        RedBlackTreeCheckUtil.isBlackRedTree(rbTree.getRoot());
        return neighborNode(hashValue);
    }

    /**
     * 删除节点
     * @param hash
     */
    public synchronized void removeVirtualNode(long hash) {
        if (! hashNodeToMachineMap.containsKey(hash)) {
            return;
        }
        hashNodeToMachineMap.remove(hash);
        rbTree.deleteNode(hash);
        RedBlackTreeCheckUtil.isBlackRedTree(rbTree.getRoot());
    }

    /**
     * 相邻节点
     * @param hashValue
     * @return
     */
    public synchronized VirtualNode[] neighborNode(long hashValue) {
        long next = next(hashValue);
        long last = last(hashValue);

        return new VirtualNode[] {
                new VirtualNode(last, hashNodeToMachineMap.get(last)),
                new VirtualNode(next, hashNodeToMachineMap.get(next))
        };
    }


    /**
     * 下一个节点
     * @param hashValue
     * @return
     */
    private long next(long hashValue) {
        RbNode next = null;
        RbNode cur = rbTree.getRoot();
        while (cur != null) {
            if (cur.getVal() <= hashValue) {
                cur = cur.getR();
                continue;
            }
            if (cur.getVal() > hashValue) {
                next = cur;
                cur = cur.getL();
            }
        }
        if (next == null) {
            cur = rbTree.getRoot();
            while (cur.getL() != null) {
                cur = cur.getL();
            }
            return cur.getValue();
        }
        return next.getValue();
    }

    /**
     * 上一个节点
     * @param hashValue
     * @return
     */
    private long last(long hashValue) {
        RbNode last = null;
        RbNode cur = rbTree.getRoot();
        while (cur != null) {
            if (cur.getVal() >= hashValue) {
                cur = cur.getL();
                continue;
            }
            if (cur.getVal() < hashValue) {
                last = cur;
                cur = cur.getR();
            }
        }
        if (last == null) {
            cur = rbTree.getRoot();
            while (cur.getR() != null) {
                cur = cur.getR();
            }
            return cur.getValue();
        }
        return last.getValue();
    }

    /**
     * 虚拟节点
     */
    public static class VirtualNode {
        private long hash;
        private int machineId;

        public VirtualNode(long hash, int machineId) {
            this.hash = hash;
            this.machineId = machineId;
        }

        public long getHash() {
            return hash;
        }

        public int getMachineId() {
            return machineId;
        }
    }


}
