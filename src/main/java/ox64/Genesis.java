package ox64;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Genesis {
    int N = (int) (1e6 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int[] dfn = new int[N];
    int n;
    int root;
    int broken;
    int num;
    int res;
    int resAll;
    int[] a = new int[N];
    int[] fa = new int[N];
    int[][] f = new int[N][2];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            add(i, a[i]);
            add(a[i], i);
        }

        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                dfs(u);
                dp(root, 1);
                res = Math.max(f[root][0], f[root][1]);
                dp(root, 2);
                res = Math.max(res, f[root][0]);
                resAll += res;
            }
        }
        out.println(resAll);

    }

    private void dp(int u, int times) {
        f[u][0] = f[u][1] = 0;
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j] && i != broken && (i ^ 1) != broken) {
                dp(j, times);
                f[u][0] += Math.max(f[j][0], f[j][1]);
            }
        }

        if (times == 2 && u == a[root]) {
            f[u][1] = f[u][0] + 1;
        } else {
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (!vis[j] && i != broken && (i ^ 1) != broken) {
                    f[u][1] = Math.max(f[u][1], f[j][0] + f[u][0] - Math.max(f[j][0], f[j][1]) + 1);
                }
            }
        }
        vis[u] = false;
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
        if (a[u] == j) {
            root = u;
        } else {
            root = j;
        }
        broken = z;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

}
