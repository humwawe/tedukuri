package ox64;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FredasPager {
    int N = 12005;
    int M = N * 4;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] dfn = new int[N];
    int res;
    int[] fa = new int[N];
    int[] dist = new int[N];
    int[] sum = new int[N];
    int[] in = new int[N];
    boolean[] vis = new boolean[N];
    boolean[] broken = new boolean[M];
    int[] lenCycle = new int[N];
    int[] d = new int[N];
    int T = 18;
    int[][] f = new int[N][T];
    int n, m, t, num, cnt;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        t = in.nextInt();
        for (int i = 1; i <= m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }

        spfa();
        dfs(1);
        bfs();

        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            out.println(calc(a, b));
        }

    }

    private int calc(int a, int b) {
        if (d[a] < d[b]) {
            int t = a;
            a = b;
            b = t;
        }
        int oa = a;
        int ob = b;
        for (int i = T - 1; i >= 0; i--) {
            if (d[f[a][i]] >= d[b]) {
                a = f[a][i];
            }
        }
        if (a == b) {
            return dist[oa] - dist[ob];
        }
        for (int i = T - 1; i >= 0; i--) {
            if (f[a][i] != f[b][i]) {
                a = f[a][i];
                b = f[b][i];
            }
        }
        if (in[a] == 0 || in[a] != in[b]) {
            return dist[oa] + dist[ob] - 2 * dist[f[a][0]];
        }
        // 环上某个方向的距离
        int l = Math.abs(sum[a] - sum[b]);
        return dist[oa] - dist[a] + dist[ob] - dist[b] + Math.min(l, lenCycle[in[a]] - l);
    }

    void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (d[j] == 0 && !broken[i]) {
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

    private void dfs(int u) {
        dfn[u] = ++num;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                fa[j] = i;
                dfs(j);
            } else if ((i ^ 1) != fa[u] & dfn[j] >= dfn[u]) {
                getCycle(u, j, i);
            }
        }
    }

    private void getCycle(int u, int j, int z) {
        cnt++;
        sum[j] = w[z];
        broken[z] = broken[z ^ 1] = true;
        while (j != u) {
            in[j] = cnt;
            int nextJ = e[fa[j] ^ 1];
            sum[nextJ] = sum[j] + w[fa[j]];

            broken[fa[j]] = broken[fa[j] ^ 1] = true;
            add(u, j, dist[j] - dist[u]);
            add(j, u, dist[j] - dist[u]);

            j = nextJ;
        }
        in[u] = cnt;
        lenCycle[cnt] = sum[u];

    }

    void spfa() {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        vis[1] = true;
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
