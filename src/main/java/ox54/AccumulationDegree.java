package ox54;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class AccumulationDegree {
    int N = (int) 2e5 + 5;
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int n;
    int[] deg = new int[N];
    int[] d = new int[N];
    int[] f = new int[N];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        Arrays.fill(deg, 0);
        Arrays.fill(d, 0);
        Arrays.fill(vis, false);
        idx = 0;
        n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            deg[a]++;
            deg[b]++;
            add(a, b, c);
            add(b, a, c);
        }
        int root = 1;
        dp(root);
        Arrays.fill(vis, false);
        f[root] = d[root];
        dfs(root);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, f[i]);
        }
        out.println(res);
    }

    private void dfs(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            if (deg[u] == 1) {
                f[j] = d[j] + w[i];
            } else {
                f[j] = d[j] + Math.min(f[u] - Math.min(d[j], w[i]), w[i]);
            }
            dfs(j);
        }
    }

    private void dp(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            dp(j);
            if (deg[j] == 1) {
                d[u] += w[i];
            } else {
                d[u] += Math.min(d[j], w[i]);
            }
        }
    }


    private void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
