package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Rendezvous {

    int N = 500005;
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int[] dfn = new int[N];
    int[] fa = new int[N];
    int[] sum = new int[N];
    int[] in = new int[N];
    int[] lenCycle = new int[N];
    int[] d = new int[N];
    int[] s = new int[N];
    int T = 18;
    int[][] f = new int[N][T];
    int num, cnt, p;
    int n, q;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        q = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 1; i <= n; i++) {
            int tmp = in.nextInt();
            add(i, tmp);
            add(tmp, i);
        }

        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                dfs(i);
                for (int j = 1; j <= p; j++) {
                    bfs(j);
                }
            }
        }

        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int[] res = calc(a, b);
            out.println(res[0] + " " + res[1]);
        }


    }

    private int[] calc(int a, int b) {
        int ha = f[a][T - 1];
        int hb = f[b][T - 1];
        if (in[ha] != in[hb]) {
            return new int[]{-1, -1};
        }
        if (ha != hb) {
            int x = d[a] - d[ha];
            int y = d[b] - d[hb];
            int l = Math.abs(sum[ha] - sum[hb]);
            int r = lenCycle[in[ha]] - l;


        } else {
            return new int[]{d[a] - d[lca(a, b)], d[b] - d[lca(a, b)]};
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


    void bfs(int s) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        for (int k = 0; k < T; k++) {
            f[s][k] = s;
        }
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

    private void dfs(int u) {
        dfn[u] = ++num;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                fa[j] = i;
                dfs(j);
            }
            // 考虑自环的话可以取到 dfn[j] >= dfn[u]
            else if ((i ^ 1) != fa[u] & dfn[j] >= dfn[u]) {
                getCycle(u, j, i);
            }
        }
    }

    private void getCycle(int u, int j, int z) {
        cnt++;
        sum[j] = 1;
        p = 0;
        while (j != u) {
            in[j] = cnt;
            d[j] = 1;

            s[++p] = j;
            int nextJ = e[fa[j] ^ 1];
            sum[nextJ] = sum[j] + 1;
            j = nextJ;

        }
        in[u] = cnt;
        d[u] = 1;
        s[++p] = u;

        lenCycle[cnt] = sum[u];
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
