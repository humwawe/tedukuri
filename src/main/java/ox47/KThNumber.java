package ox47;

import common.io.InputReader;
import common.io.OutputWriter;

public class KThNumber {
    int n, m;
    int N = 100010 + 10010;
    Node[] q = new Node[N];
    Node[] lq = new Node[N];
    Node[] rq = new Node[N];
    int[] res = new int[N];


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            int x = in.nextInt();
            q[i] = new Node(1, x, i, 0, 0);
        }
        for (int i = 1; i <= m; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            int k = in.nextInt();
            q[i + n] = new Node(2, l, r, k, i);
        }
        helper((int) -1e9, (int) 1e9, 1, n + m);
        for (int i = 1; i <= m; i++) {
            out.println(res[i]);
        }
    }

    private void helper(int vl, int vr, int ql, int qr) {
        if (ql > qr || vl > vr) {
            return;
        }
        if (vl == vr) {
            for (int i = ql; i <= qr; i++) {
                if (q[i].op == 2) {
                    res[q[i].id] = vr;
                }
            }
            return;
        }
        int mid = vl + vr >> 1;
        int nl = 0;
        int nr = 0;
        for (int i = ql; i <= qr; i++) {
            if (q[i].op == 1) {
                if (q[i].x <= mid) {
                    add(q[i].y, 1);
                    lq[++nl] = q[i];
                } else {
                    rq[++nr] = q[i];
                }
            } else {
                int cnt = query(q[i].y) - query(q[i].x - 1);
                if (cnt >= q[i].k) {
                    lq[++nl] = q[i];
                } else {
                    q[i].k -= cnt;
                    rq[++nr] = q[i];
                }
            }
        }
        for (int i = ql; i <= qr; i++) {
            if (q[i].op == 1) {
                if (q[i].x <= mid) {
                    add(q[i].y, -1);
                }
            }
        }

        for (int i = 1; i <= nl; i++) {
            q[ql + i - 1] = lq[i];
        }
        for (int i = 1; i <= nr; i++) {
            q[ql + nl + i - 1] = rq[i];
        }

        helper(vl, mid, ql, ql + nl - 1);
        helper(mid + 1, vr, ql + nl, qr);

    }

    int[] tr = new int[N];

    class Node {
        int op;
        int x, y, k;
        int id;

        public Node(int op, int x, int y, int k, int id) {
            this.op = op;
            this.x = x;
            this.y = y;
            this.k = k;
            this.id = id;
        }

    }

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int v) {
        for (; x <= n; x += lowbit(x)) {
            tr[x] += v;
        }
    }

    int query(int x) {
        int res = 0;
        for (; x > 0; x -= lowbit(x)) {
            res += tr[x];
        }
        return res;
    }
}
