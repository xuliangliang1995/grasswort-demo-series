package segmenttree;

/**
 * @author 🌺xuliangliang🌺
 * @Description 线段树 (O(log^n))
 * @Date 2021/6/12
 */
public class SegmentTree {

    private final int[] arr;
    private final int[] sum;
    private final int[] lazy;
    private final int[] change;
    private final boolean[] update;

    public SegmentTree(int[] arr) {
        this.arr = new int[arr.length + 1];
        System.arraycopy(arr, 0, this.arr, 1, arr.length);
        int length = 4 * (arr.length);
        this.sum = new int[length];
        this.lazy = new int[length];
        this.change = new int[length];
        this.update = new boolean[length];
        preBuild();
    }

    public SegmentTree(int length) {
        this.arr = new int[length + 1];
        this.sum = new int[length * 4];
        this.lazy = new int[length * 4];
        this.change = new int[length * 4];
        this.update = new boolean[length * 4];
    }

    /**
     * 区间添加
     * @param L
     * @param R
     * @param C
     */
    public void add(int L, int R, int C) {
        doAdd(L + 1, R + 1, C, 1, arr.length - 1, 1);
    }

    /**
     * 区间更新
     * @param L
     * @param R
     * @param C
     */
    public void update(int L, int R, int C) {
        doUpdate(L + 1, R + 1, C, 1, arr.length - 1, 1);
    }

    /**
     * 求和
     * @param L
     * @param R
     */
    public int sum(int L, int R) {
        return doQuery(L + 1, R + 1, 1, arr.length - 1, 1);
    }

    /**
     * 预先构建
     */
    private void preBuild() {
        for (int i = 1; i < arr.length; i++) {
            add(i - 1, i - 1, arr[i]);
        }
    }

    /**
     * 查询
     * @param L
     * @param R
     * @param l
     * @param r
     * @param rt
     * @return
     */
    private int doQuery(int L, int R, int l, int r, int rt) {
        if (L <= l && R >= r) {
            return sum[rt];
        }
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        int sum = 0;
        if (L <= mid) {
            sum += doQuery(L, R, l, mid, rt << 1);
        }
        if (R > mid) {
            sum += doQuery(L, R, mid + 1, r, rt << 1 | 1);
        }
        return sum;
    }

    /**
     * 执行更新
     * @param L
     * @param R
     * @param C
     * @param l
     * @param r
     * @param rt
     */
    private void doUpdate(int L, int R, int C, int l, int r, int rt) {
        if (l == r) {
            sum[rt] = C;
            return;
        }

        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);

        if (L <= l && R >= r) {
            update[rt] = true;
            change[rt] = C;
            sum[rt] = C * (r - l + 1);
            return;
        }

        if (L <= mid) {
            doUpdate(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            doUpdate(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        sumUp(rt);
    }

    /**
     * 执行 add
     * @param L
     * @param R
     * @param C
     * @param l
     * @param r
     * @param rt
     */
    private void doAdd(int L, int R, int C, int l, int r, int rt) {
        if (l == r) {
            sum[rt] += C;
            return;
        }
        if (L <= l && R >= r) {
            lazy[rt] += C;
            sum[rt] += (r - l + 1) * C;
            // lazy add .
            return;
        }
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            doAdd(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            doAdd(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        sumUp(rt);
    }

    /**
     * 求和
     * @param rt
     */
    private void sumUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }

    /**
     * 懒更新下推
     * @param rt
     * @param ln
     * @param rn
     */
    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            change[rt << 1] = change[rt];
            update[rt << 1 | 1] = true;
            change[rt << 1 | 1] = change[rt];
            sum[rt << 1] = ln * change[rt];
            sum[rt << 1 | 1] = rn * change[rt];
            update[rt] = false;
            change[rt] = 0;
        }

        if (lazy[rt] != 0) {
            lazy[rt << 1] += lazy[rt];
            sum[rt << 1] += lazy[rt] * ln;
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1 | 1] += lazy[rt] * rn;
            lazy[rt] = 0;
        }
    }

}
