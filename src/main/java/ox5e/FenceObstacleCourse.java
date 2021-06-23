package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class FenceObstacleCourse {
    int N = (int) (3e4 + 10);
    int base = (int) (1e5 + 1);
    int M = base * 2;
    int n, s;
    int[] l = new int[N];
    int[] r = new int[N];
    int[][] f = new int[N][2];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        s = in.nextInt();
        s += base;
        build(1, 1, M);
        l[0] = r[0] = base;
        for (int i = 1; i <= n; i++) {
            l[i] = in.nextInt() + base;
            r[i] = in.nextInt() + base;
            int lId = query(1, l[i], l[i]);
            int rId = query(1, r[i], r[i]);
            f[i][0] = Math.min(f[lId][0] + Math.abs(l[lId] - l[i]), f[lId][1] + Math.abs(r[lId] - l[i]));
            f[i][1] = Math.min(f[rId][0] + Math.abs(l[rId] - r[i]), f[rId][1] + Math.abs(r[rId] - r[i]));
            modify(1, l[i], r[i], i);
        }
        int res = Math.min(f[n][0] + Math.abs(l[n] - s), f[n][1] + Math.abs(r[n] - s));
        out.println(res);
    }

    Node[] tr = new Node[M * 4];


    void pushDown(int u) {
        if (tr[u].add != 0) {
            tr[u << 1].add = tr[u].add;
            tr[u << 1].id = tr[u].id;
            tr[u << 1 | 1].add = tr[u].add;
            tr[u << 1 | 1].id = tr[u].id;
            tr[u].add = 0;
        }
    }

    void build(int u, int l, int r) {
        tr[u] = new Node(l, r);
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }

    int query(int u, int l, int r) {
        if (tr[u].l >= l && tr[u].r <= r) {
            return tr[u].id;
        }
        pushDown(u);
        int mid = tr[u].l + tr[u].r >> 1;
        if (l <= mid) {
            return query(u << 1, l, r);
        } else {
            return query(u << 1 | 1, l, r);
        }
    }

    void modify(int u, int l, int r, int d) {
        if (tr[u].l >= l && tr[u].r <= r) {
            tr[u].id = d;
            tr[u].add = 1;
            return;
        }
        pushDown(u);
        int mid = tr[u].l + tr[u].r >> 1;
        if (l <= mid) {
            modify(u << 1, l, r, d);
        }
        if (r > mid) {
            modify(u << 1 | 1, l, r, d);
        }
    }

    class Node {
        int l;
        int r;
        int id;
        // 懒标记，给儿子加上add，不包括自己
        int add;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, int id, int add) {
            this.l = l;
            this.r = r;
            this.id = id;
            this.add = add;
        }
    }
}


