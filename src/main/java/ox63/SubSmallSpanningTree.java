package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;

public class SubSmallSpanningTree {
    int N = (int) (1e5 + 5);
    int M = N * 3;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] d = new int[N];
    int T = 18;
    int[][] f = new int[N][T];
    int[][][] g = new int[N][T][2];
    int n, m;
    int[] p = new int[N];
    boolean[] used = new boolean[M];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        int[][] edge = new int[m][3];
        for (int i = 0; i < m; i++) {
            edge[i][0] = in.nextInt();
            edge[i][1] = in.nextInt();
            edge[i][2] = in.nextInt();
        }
        Arrays.sort(edge, Comparator.comparingInt(x -> x[2]));
        for (int i = 0; i <= n; i++) {
            p[i] = i;
        }
        long mst = 0;

        for (int i = 0; i < m; i++) {
            int x = find(edge[i][0]);
            int y = find(edge[i][1]);
            if (x != y) {
                used[i] = true;
                mst += edge[i][2];
                p[y] = x;
                add(edge[i][0], edge[i][1], edge[i][2]);
                add(edge[i][1], edge[i][0], edge[i][2]);
            }
        }
        bfs();
        int delta = 1 << 30;
        for (int i = 0; i < m; i++) {
            if (used[i]) {
                continue;
            }
            if (edge[i][0] == edge[i][1]) {
                continue;
            }
            int[] tmp = new int[2];
            lca(tmp, edge[i][0], edge[i][1]);
            if (tmp[0] < edge[i][2]) {
                delta = Math.min(delta, edge[i][2] - tmp[0]);
            } else if (tmp[1] > 0) {
                delta = Math.min(delta, edge[i][2] - tmp[1]);
            }
        }
        out.println(mst + delta);
    }

    private void lca(int[] ans, int a, int b) {
        if (d[a] < d[b]) {
            int t = a;
            a = b;
            b = t;
        }
        for (int i = T - 1; i >= 0; i--) {
            if (d[f[a][i]] >= d[b]) {
                merge(ans, ans, g[a][i]);
                a = f[a][i];
            }
        }
        if (a == b) {
            return;
        }
        for (int i = T - 1; i >= 0; i--) {
            if (f[a][i] != f[b][i]) {
                merge(ans, ans, g[a][i]);
                merge(ans, ans, g[b][i]);
                a = f[a][i];
                b = f[b][i];
            }
        }
        merge(ans, ans, g[a][0]);
        merge(ans, ans, g[b][0]);
    }

    private void bfs() {
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
                    g[j][0][0] = w[i];
                    for (int k = 1; k < T; k++) {
                        f[j][k] = f[f[j][k - 1]][k - 1];
                        merge(g[j][k], g[j][k - 1], g[f[j][k - 1]][k - 1]);
                    }
                    queue.add(j);
                }
            }
        }
    }

    private void merge(int[] c, int[] a, int[] b) {
        c[0] = Math.max(a[0], b[0]);
        if (a[0] == b[0]) {
            c[1] = Math.max(a[1], b[1]);
        } else if (a[0] > b[0]) {
            c[1] = Math.max(a[1], b[0]);
        } else {
            c[1] = Math.max(a[0], b[1]);
        }
    }

    private int find(int x) {
        if (p[x] != x) {
            return p[x] = find(p[x]);
        }
        return p[x];
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
