package ox43;

import common.io.InputReader;
import common.io.OutputWriter;

public class IntervalGCD {
    int n, m;
    long[] a;
    long[] t;
    Node[] tr;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = new long[n + 1];
        long[] b = new long[n + 1];
        t = new long[n + 1];
        tr = new Node[n * 4];
        for (int i = 1; i <= n; i++) {
            b[i] = in.nextLong();
            a[i] = b[i] - b[i - 1];
            add(i, a[i]);
        }
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            char c = in.nextCharacter();
            if (c == 'C') {
                int l = in.nextInt();
                int r = in.nextInt();
                long d = in.nextLong();
                add(l, d);
                add(r + 1, -d);
                modify(1, l, d);
                if (r < n) {
                    modify(1, r + 1, -d);
                }
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                long res1 = sum(l);
                long res2 = 0;
                if (l < r) {
                    res2 = query(1, l + 1, r);
                }
                out.println(gcd(res1, res2));
            }
        }
    }

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, long c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t[i] += c;
        }
    }

    long sum(int x) {
        long res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
        }
        return res;
    }

    long gcd(long a, long b) {
        return b != 0 ? gcd(b, a % b) : a;
    }

    void pushUp(int u) {
        tr[u].v = gcd(tr[u << 1].v, tr[u << 1 | 1].v);
    }

    void build(int u, int l, int r) {
        if (l == r) {
            tr[u] = new Node(l, r, a[r]);
            return;
        }
        tr[u] = new Node(l, r);
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
        pushUp(u);
    }

    long query(int u, int l, int r) {
        if (tr[u].l >= l && tr[u].r <= r) {
            return Math.abs(tr[u].v);
        }
        int mid = tr[u].l + tr[u].r >> 1;
        long v = 0;
        if (l <= mid) {
            v = query(u << 1, l, r);
        }
        if (r > mid) {
            v = gcd(v, query(u << 1 | 1, l, r));
        }
        return Math.abs(v);
    }

    void modify(int u, int x, long v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].v += v;
            return;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (x <= mid) {
            modify(u << 1, x, v);
        } else {
            modify(u << 1 | 1, x, v);
        }
        pushUp(u);
    }

    class Node {
        int l;
        int r;
        long v;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, long v) {
            this.l = l;
            this.r = r;
            this.v = v;
        }
    }
}
