package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

public class Hotel {
    int n, m;
    Node[] tr;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        tr = new Node[n * 4];
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            int t = in.nextInt();
            if (t == 1) {
                int len = in.nextInt();
                int l = query(1, len);
                out.println(l);
                if (l != 0) {
                    modify(1, l, l + len - 1, 2);
                }
            } else {
                int l = in.nextInt();
                int len = in.nextInt();
                modify(1, l, l + len - 1, 1);
            }
        }
    }


    void pushUp(int u) {
        tr[u].ld = tr[u << 1].ld;
        if (tr[u << 1].d == tr[u << 1].r - tr[u << 1].l + 1) {
            tr[u].ld = tr[u << 1].d + tr[u << 1 | 1].ld;
        }
        tr[u].rd = tr[u << 1 | 1].rd;
        if (tr[u << 1 | 1].d == tr[u << 1 | 1].r - tr[u << 1 | 1].l + 1) {
            tr[u].rd = tr[u << 1 | 1].d + tr[u << 1].rd;
        }
        tr[u].d = Math.max(tr[u << 1].d, tr[u << 1 | 1].d);
        tr[u].d = Math.max(tr[u].d, tr[u << 1].rd + tr[u << 1 | 1].ld);
    }

    void pushDown(int u) {
        if (tr[u].add != 0) {
            if (tr[u].add == 1) {
                tr[u << 1].d = tr[u << 1].ld = tr[u << 1].rd = tr[u << 1].r - tr[u << 1].l + 1;
                tr[u << 1 | 1].d = tr[u << 1 | 1].ld = tr[u << 1 | 1].rd = tr[u << 1 | 1].r - tr[u << 1 | 1].l + 1;
            } else if (tr[u].add == 2) {
                tr[u << 1].d = tr[u << 1].ld = tr[u << 1].rd = 0;
                tr[u << 1 | 1].d = tr[u << 1 | 1].ld = tr[u << 1 | 1].rd = 0;
            }
            tr[u << 1].add = tr[u << 1 | 1].add = tr[u].add;
            tr[u].add = 0;
        }
    }

    void build(int u, int l, int r) {
        if (l == r) {
            tr[u] = new Node(l, r, 1, 1, 1, 0);
            return;
        }
        tr[u] = new Node(l, r);
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
        pushUp(u);
    }

    int query(int u, int len) {
        if (tr[u].d < len) {
            return 0;
        }
        pushDown(u);
        if (tr[u << 1].d >= len) {
            return query(u << 1, len);
        }
        if (tr[u << 1].rd + tr[u << 1 | 1].ld >= len) {
            return tr[u << 1].r - tr[u << 1].rd + 1;
        }
        if (tr[u << 1 | 1].d >= len) {
            return query(u << 1 | 1, len);
        }
        return 0;
    }

    void modify(int u, int l, int r, int c) {
        if (tr[u].l >= l && tr[u].r <= r) {
            if (c == 1) {
                tr[u].d = tr[u].ld = tr[u].rd = tr[u].r - tr[u].l + 1;
            } else {
                tr[u].d = tr[u].ld = tr[u].rd = 0;
            }
            tr[u].add = c;
            return;
        }
        pushDown(u);
        int mid = tr[u].l + tr[u].r >> 1;
        if (l <= mid) {
            modify(u << 1, l, r, c);
        }
        if (r > mid) {
            modify(u << 1 | 1, l, r, c);
        }
        pushUp(u);
    }

    class Node {
        int l;
        int r;
        int d, ld, rd;
        int add;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, int d, int ld, int rd, int add) {
            this.l = l;
            this.r = r;
            this.d = d;
            this.ld = ld;
            this.rd = rd;
            this.add = add;
        }
    }
}
