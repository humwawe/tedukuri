package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class RedundantPaths {
    int N = 5005;
    int M = 20005;
    int[] dfn = new int[N];
    int[] low = new int[N];
    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m;
    int[] d = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }

        tarjanBridge(1, -1);
        tarjanBridgeEDccNo(n);

        for (int i = 0; i < idx; ++i) {
            if (bridge[i]) {
                d[eDccC[e[i]]]++;
            }
        }
        int cnt = 0;
        for (int i = 1; i <= eDccNo; i++) {
            if (d[i] == 1) {
                cnt++;
            }
        }
        out.println((cnt + 1) / 2);

    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    boolean[] bridge = new boolean[M];

    // 无向图的桥 tarjanBridge(root, -1)
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
}
