package ox58;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class CleaningShifts {
    int n, m, e;
    int N = 10010;
    int M = 90000;
    Node[] tr = new Node[M * 4];
    long inf = (long) 1e15;
    Node[] a;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        e = in.nextInt();
        build(1, m - 1, e);
        modify(1, m - 1, 0);
        a = new Node[n];
        for (int i = 0; i < n; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            int w = in.nextInt();
            a[i] = new Node(l, r, w);
        }
        Arrays.sort(a, Comparator.comparingInt(x -> x.r));
        for (int i = 0; i < n; i++) {
            int l = a[i].l;
            int r = a[i].r;
            long v = a[i].v;
            long w = query(1, l - 1, r - 1) + v;
            modify(1, r, w);
        }
        long res = query(1, e, e);
        if (res != inf) {
            out.println(res);
        } else {
            out.println(-1);
        }
    }


    void pushUp(int u) {
        tr[u].v = Math.min(tr[u << 1].v, tr[u << 1 | 1].v);
    }

    void build(int u, int l, int r) {
        tr[u] = new Node(l, r, inf);
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }

    long query(int u, int l, int r) {
        if (tr[u].l >= l && tr[u].r <= r) {
            return tr[u].v;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        long v = inf;
        if (l <= mid) {
            v = query(u << 1, l, r);
        }
        if (r > mid) {
            v = Math.min(v, query(u << 1 | 1, l, r));
        }
        return v;
    }

    void modify(int u, int x, long v) {
        if (tr[u].l == x && tr[u].r == x) {
            tr[u].v = Math.min(tr[u].v, v);
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
