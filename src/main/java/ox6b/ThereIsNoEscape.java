package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ThereIsNoEscape {
    int N = (int) (2e5 + 4);
    int M = N * 2;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m;

    int T = 18;
    int[] d = new int[N];
    int[][] f = new int[N][T];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        tarjanBridge(1, -1);
        tarjanBridgeEDccNo(n);
        tarjanBridgeEDccReBuild();

        bfs(eDccH, eDccNe, eDccE);

        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int a = eDccC[in.nextInt()];
            int b = eDccC[in.nextInt()];
            out.println(d[a] + d[b] - d[lca(a, b)] * 2);
        }
    }

    void bfs(int[] h, int[] ne, int[] e) {
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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    boolean[] bridge = new boolean[M];

    // 无向图的桥
    void tarjanBridge(int u, int inEdge) {
        dfn[u] = low[u] = ++timestamp;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            // 搜索树边
            if (dfn[j] == 0) {
                tarjanBridge(j, i);
                low[u] = Math.min(low[u], low[j]);
                // 桥
                if (low[j] > dfn[u]) {
                    bridge[i] = true;
                    bridge[i ^ 1] = true;
                }
            } else if (i != (inEdge ^ 1)) {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
    }

    int[] eDccC = new int[N];
    int eDccNo;

    void tarjanBridgeEDccNo(int n) {
        // 对所有的点(1-n)
        for (int i = 1; i <= n; i++) {
            if (eDccC[i] == 0) {
                // 编号
                ++eDccNo;
                tarjanBridgeEDccDfs(i);
            }
        }
    }

    void tarjanBridgeEDccDfs(int u) {
        eDccC[u] = eDccNo;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (eDccC[j] != 0 || bridge[i]) {
                continue;
            }
            tarjanBridgeEDccDfs(j);
        }
    }

    // e-DCC缩点
    int[] eDccH = new int[N];
    int[] eDccE = new int[M];
    int[] eDccNe = new int[M];
    int eDccIdx;

    void tarjanBridgeEDccReBuild() {
        Arrays.fill(eDccH, -1);
        eDccIdx = 0;
        // 对每条边
        for (int i = 0; i < idx; i++) {
            int x = e[i ^ 1];
            int y = e[i];
            if (eDccC[x] == eDccC[y]) {
                continue;
            }
            addEDcc(eDccC[x], eDccC[y]);
        }
    }

    void addEDcc(int a, int b) {
        eDccE[eDccIdx] = b;
        eDccNe[eDccIdx] = eDccH[a];
        eDccH[a] = eDccIdx++;
    }
}
