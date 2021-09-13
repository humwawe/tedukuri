package ox66;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Blo {
    int N = 100005;
    int M = 500005 * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] dfn = new int[N];
    int[] low = new int[N];
    int idx, timestamp;
    int root;
    boolean[] cut = new boolean[N];
    int n, m;
    int[] sz = new int[N];
    long[] res = new long[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        timestamp = 0;
        root = 1;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        tarjanCut(1);
        for (int i = 1; i <= n; i++) {
            out.println(res[i]);
        }
    }


    void tarjanCut(int u) {
        dfn[u] = low[u] = ++timestamp;
        sz[u] = 1;
        int son = 0;
        int sum = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                tarjanCut(j);
                sz[u] += sz[j];
                low[u] = Math.min(low[u], low[j]);
                if (low[j] >= dfn[u]) {
                    son++;
                    if (u != root || son > 1) {
                        cut[u] = true;
                    }
                    res[u] += (long) sz[j] * (n - sz[j]);
                    sum += sz[j];
                }
            } else {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
        if (cut[u]) {
            res[u] += (long) (n - sum - 1) * (sum + 1) + n - 1;
        } else {
            res[u] = 2 * (n - 1);
        }
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
