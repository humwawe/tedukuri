package ox43;

import common.io.InputReader;
import common.io.OutputWriter;

public class CanYouAnswerTheseQueriesIII {
    int n, m;
    int[] w;
    Node[] tr;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        tr = new Node[n * 4];
        w = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            int t = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            if (t == 1) {
                if (y < x) {
                    int tmp = y;
                    y = x;
                    x = tmp;
                }
                out.println(query(1, x, y).v);
            } else {
                modify(1, x, y);
            }
        }
    }

    void pushUp(int u) {
        tr[u].sum = tr[u << 1].sum + tr[u << 1 | 1].sum;
        tr[u].lMax = Math.max(tr[u << 1].lMax, tr[u << 1].sum + tr[u << 1 | 1].lMax);
        tr[u].rMax = Math.max(tr[u << 1 | 1].rMax, tr[u << 1 | 1].sum + tr[u << 1].rMax);
        tr[u].v = Math.max(Math.max(tr[u << 1].v, tr[u << 1 | 1].v), tr[u << 1].rMax + tr[u << 1 | 1].lMax);
    }

    void build(int u, int l, int r) {
        if (l == r) {
            tr[u] = new Node(l, r, w[r]);
            return;
        }
        tr[u] = new Node(l, r);
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
        pushUp(u);
    }

    // 从u开始查找
    Node query(int u, int l, int r) {
        // 已经完全在[l,r]中了
        if (tr[u].l >= l && tr[u].r <= r) {
            return tr[u];
        }
        int mid = tr[u].l + tr[u].r >> 1;
        Node node = new Node(-1, -1, Integer.MIN_VALUE);
        Node left = new Node(-1, -1, Integer.MIN_VALUE);
        Node right = new Node(-1, -1, Integer.MIN_VALUE);
        if (l <= mid) {
            left = query(u << 1, l, r);
        }
        if (r > mid) {
            right = query(u << 1 | 1, l, r);
        }
        node.sum = left.sum + right.sum;
        node.v = Math.max(Math.max(left.v, right.v), left.rMax + right.lMax);
        node.lMax = Math.max(left.lMax, left.sum + right.lMax);
        node.rMax = Math.max(right.rMax, right.sum + left.rMax);
        return node;
    }

    // 从u开始，修改x位置的值为v
    void modify(int u, int x, int v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].v = v;
            tr[u].sum = v;
            tr[u].lMax = v;
            tr[u].rMax = v;
        } else {
            int mid = tr[u].l + tr[u].r >> 1;
            if (x <= mid) {
                modify(u << 1, x, v);
            } else {
                modify(u << 1 | 1, x, v);
            }
            pushUp(u);
        }
    }

    class Node {
        int l;
        int r;
        long v;
        long lMax;
        long rMax;
        long sum;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, long v) {
            this.l = l;
            this.r = r;
            this.v = v;
            lMax = v;
            rMax = v;
            sum = v;
        }
    }
}
