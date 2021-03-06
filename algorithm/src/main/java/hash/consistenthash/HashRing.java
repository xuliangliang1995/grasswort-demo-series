package hash.consistenthash;

import hash.rbtree.RbNode;
import hash.rbtree.RedBlackTree;
import hash.rbtree.RedBlackTreeCheckUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ðºxuliangliangðº
 * @Description åå¸ç¯
 * @Date 2021/6/13
 */
public class HashRing {

    /**
     * åå¸ç¯(èæèç¹ååºæå)
     */
    private final RedBlackTree rbTree = new RedBlackTree();

    /**
     * åå¸èæèç¹ => æºå¨ç¼å·çæ å°
     */
    private final Map<Long, Integer> hashNodeToMachineMap = new HashMap<>(3000);


    /**
     * æ ¹æ® hash å¼è·åæºå¨ id
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
     * æ·»å ä¸ä¸ªèæèç¹(å¦æ hashValue å·²å­å¨,åä¸æ·»å å¹¶è¿å false, æ­¤ç§æåµæ¦çæä½)
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
     * å é¤èç¹
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
     * ç¸é»èç¹
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
     * ä¸ä¸ä¸ªèç¹
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
     * ä¸ä¸ä¸ªèç¹
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
     * èæèç¹
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
