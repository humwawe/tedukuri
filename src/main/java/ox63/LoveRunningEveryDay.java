package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class LoveRunningEveryDay {
    int N = 300005;
    int[] h = new int[N];
    int[] e = new int[N * 2];
    int[] ne = new int[N * 2];
    int idx;
    int[] d = new int[N];
    int T = 19;
    int[][] f = new int[N][T];
    int n, m;
    int[] w = new int[N];
    int[] res = new int[N];
    List<Opr>[] a = new List[N];
    int[][] c = new int[2][N * 2];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            add(x, y);
            add(y, x);
        }
        for (int i = 1; i <= n; i++) {
            a[i] = new ArrayList<>();
        }

        bfs();
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }

        for (int i = 0; i < m; i++) {
            int s = in.nextInt();
            int t = in.nextInt();
            int u = lca(s, t);
            // s, d[s], +1; f[u][0], d[s], -1
            a[s].add(new Opr(d[s], 1, 0));
            if (f[u][0] != 0) {
                a[f[u][0]].add(new Opr(d[s], -1, 0));
            }
            // t, d[s]-2*d[u], +1; u, d[s]-2*d[u], -1
            // +n平移，防止负数下标
            a[t].add(new Opr(d[s] - 2 * d[u] + n, 1, 1));
            a[u].add(new Opr(d[s] - 2 * d[u] + n, -1, 1));
        }

        dfs(1);

        for (int i = 1; i <= n; i++) {
            out.print(res[i] + " ");
        }

    }

    void dfs(int u) {
        int val0 = c[0][w[u] + d[u]];
        int val1 = c[1][w[u] - d[u] + n];
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (d[j] <= d[u]) {
                continue;
            }
            dfs(j);
        }
        for (int i = 0; i < a[u].size(); i++) {
            c[a[u].get(i).kind][a[u].get(i).idx] += a[u].get(i).delta;
        }
        res[u] = c[0][w[u] + d[u]] - val0 + c[1][w[u] - d[u] + n] - val1;
    }


    class Opr {
        int idx, delta, kind;

        public Opr(int idx, int delta, int kind) {
            this.idx = idx;
            this.delta = delta;
            this.kind = kind;
        }
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
}
