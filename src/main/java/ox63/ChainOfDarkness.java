package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ChainOfDarkness {
    int N = (int) (1e5 + 5);
    int M = (int) (2e5 + 5);
    int n, m;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx = 0;
    int[] sum = new int[N];
    int[] d = new int[N];
    int[][] fa = new int[N][17];
    int res = 0;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        bfs();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int p = lca(a, b);
            sum[a]++;
            sum[b]++;
            sum[p] -= 2;
        }
        dfs(1, -1);

        for (int i = 2; i <= n; i++) {
            if (sum[i] == 0) {
                res += m;
            } else if (sum[i] == 1) {
                res++;
            }
        }
        out.println(res);
    }

    private void dfs(int u, int p) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == p) {
                continue;
            }
            dfs(j, u);
            sum[u] += sum[j];
        }
    }

    private int lca(int a, int b) {
        if (d[a] < d[b]) {
            int t = a;
            a = b;
            b = t;
        }
        for (int i = 16; i >= 0; i--) {
            if (d[fa[a][i]] >= d[b]) {
                a = fa[a][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = 16; i >= 0; i--) {
            if (fa[a][i] != fa[b][i]) {
                a = fa[a][i];
                b = fa[b][i];
            }
        }
        return fa[a][0];
    }

    void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (d[j] != 0) {
                    continue;
                }
                d[j] = d[u] + 1;
                fa[j][0] = u;
                for (int k = 1; k < 17; k++) {
                    fa[j][k] = fa[fa[j][k - 1]][k - 1];
                }
                queue.add(j);
            }
        }

    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
