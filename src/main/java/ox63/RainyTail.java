package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class RainyTail {
    int[] h;
    int[] e;
    int[] ne;
    int idx;
    // (int)log(N)+1
    int T = 18;
    int[] d;
    int[][] f;
    int n, m;
    int[] root;
    int num = 0;
    Node[] tr;
    int cnt = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {

        n = in.nextInt();
        m = in.nextInt();
        h = new int[n + 5];
        e = new int[2 * n + 5];
        ne = new int[2 * n + 5];
        d = new int[n + 5];
        root = new int[n + 5];
        f = new int[n + 5][T];
        tr = new Node[(int) 1e5 * 72];

        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i <= n; i++) {
            root[i] = num++;
            tr[i] = new Node();
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        bfs();
        int[] u = new int[m];
        int[] v = new int[m];
        int[] z = new int[m];
        int[] c = new int[m];
        for (int i = 0; i < m; i++) {
            u[i] = in.nextInt();
            v[i] = in.nextInt();
            c[i] = in.nextInt();
            z[i] = c[i];
        }
        discrete(c);

        for (int i = 0; i < m; i++) {
            int p = lca(u[i], v[i]);
            z[i] = map.get(z[i]) + 1;
            insert(root[u[i]], 1, cnt, z[i], 1);
            insert(root[v[i]], 1, cnt, z[i], 1);
            insert(root[p], 1, cnt, z[i], -1);
            if (p != 1) {
                insert(root[f[p][0]], 1, cnt, z[i], -1);
            }
        }
        dfs(1);
        for (int i = 1; i <= n; i++) {
            if (tr[i].pos == 0) {
                out.println(0);
            } else {
                out.println(c[tr[i].pos - 1]);
            }
        }
    }

    int find(int x) {
        return map.get(x);
    }

    private void dfs(int u) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (d[j] <= d[u]) {
                continue;
            }
            dfs(j);
            root[u] = merge(root[u], root[j], 1, cnt);
        }

    }

    Map<Integer, Integer> map = new HashMap<>();

    void discrete(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            if (i == 0 || a[i] != a[i - 1]) {
                map.put(a[i], cnt);
                a[cnt++] = a[i];
            }
        }
    }

    private int merge(int p, int q, int l, int r) {
        if (p == 0) {
            return q;
        }
        if (q == 0) {
            return p;
        }
        if (l == r) {
            tr[p].max += tr[q].max;
            tr[p].pos = tr[p].max != 0 ? l : 0;
            return p;
        }
        int mid = l + r >> 1;
        tr[p].l = merge(tr[p].l, tr[q].l, l, mid);
        tr[p].r = merge(tr[p].r, tr[q].r, mid + 1, r);
        pushup(p);
        return p;
    }

    private void insert(int p, int l, int r, int pos, int delta) {
        if (tr[p] == null) {
            tr[p] = new Node();
        }
        if (l == r) {
            tr[p].max += delta;
            tr[p].pos = tr[p].max != 0 ? pos : 0;
            return;
        }
        int mid = l + r >> 1;
        if (mid >= pos) {
            if (tr[p].l == 0) {
                tr[p].l = num++;
            }
            insert(tr[p].l, l, mid, pos, delta);
        } else {
            if (tr[p].r == 0) {
                tr[p].r = num++;
            }
            insert(tr[p].r, mid + 1, r, pos, delta);
        }
        pushup(p);
    }

    void pushup(int u) {
        tr[u].max = Math.max(tr[tr[u].l].max, tr[tr[u].r].max);
        tr[u].pos = tr[tr[u].l].max >= tr[tr[u].r].max ? tr[tr[u].l].pos : tr[tr[u].r].pos;
    }


    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (d[j] == 0) {
                    d[j] = d[u] + 1;
                    f[j][0] = u;
                    for (int k = 1; k < T; k++) {
                        f[j][k] = f[f[j][k - 1]][k - 1];
                    }
                    queue.add(j);
                }
            }
        }
    }

    private int lca(int a, int b) {
        if (d[a] < d[b]) {
            int t = a;
            a = b;
            b = t;
        }
        // 走到统一层
        for (int i = T - 1; i >= 0; i--) {
            if (d[f[a][i]] >= d[b]) {
                a = f[a][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = T - 1; i >= 0; i--) {
            if (f[a][i] != f[b][i]) {
                a = f[a][i];
                b = f[b][i];
            }
        }
        return f[a][0];
    }

    class Node {
        int l;
        int r;
        int max;
        int pos;
    }
}
