package ox66;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Network {
    int N = 100005;
    int M = 400005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m, q;
    boolean[] bridge = new boolean[M];
    int[] eDccC = new int[N];
    int eDccNo;
    int[] eDccH = new int[N];
    int[] eDccE = new int[M];
    int[] eDccNe = new int[M];
    int eDccIdx;
    int res;
    int[] d = new int[N];
    int[] father = new int[N];
    int[] p = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        Arrays.fill(h, -1);
        Arrays.fill(dfn, 0);
        Arrays.fill(low, 0);
        Arrays.fill(bridge, false);
        Arrays.fill(eDccC, 0);
        Arrays.fill(d, 0);
        idx = 0;
        timestamp = 0;
        eDccNo = 0;

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        tarjanBridge(1, -1);
        tarjanBridgeEDccNo();
        tarjanBridgeEDccReBuild();
        res = eDccNo - 1;

        d[1] = 1;
        dfs(1);
        for (int i = 1; i <= n; i++) {
            p[i] = i;
        }
        out.println("Case " + testNumber + ":");
        q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int x = eDccC[a];
            int y = eDccC[b];
            if (x != y) {
                calc(x, y);
            }
            out.println(res);
        }
        out.println();
    }

    private void calc(int x, int y) {
        x = find(x);
        y = find(y);
        while (x != y) {
            if (d[x] < d[y]) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            if (x == 1) {
                break;
            }
            p[x] = find(father[x]);
            res--;
            x = find(x);
        }
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    void dfs(int u) {
        for (int i = eDccH[u]; i != -1; i = eDccNe[i]) {
            int j = eDccE[i];
            if (d[j] == 0) {
                d[j] = d[u] + 1;
                father[j] = u;
                dfs(j);
            }
        }
    }

    void tarjanBridgeEDccNo() {
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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
