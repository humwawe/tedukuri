package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class TrafficRealTimeQuerySystem {
    int N = 20005;
    int M = 200005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int n, m;
    int T = 20;
    int[] d = new int[N];
    boolean[] vis = new boolean[N];
    int[][] f = new int[N][T];
    // v-DCC缩点
    // 把每个v-dcc和每个割点当做新图中的节点
    int[] vDccH = new int[N];
    int[] vDccE = new int[M];
    int[] vDccNe = new int[M];
    int vDccIdx;
    // 某个x所属点双联通编号
    int[] vDccC = new int[N];
    int[] point = new int[M];
    int num;
    boolean[] cut = new boolean[N];
    int root;

    List<Integer>[] vDccs = new List[N];
    // 从1开始编号
    int vDccNo;
    int[] vDccStack = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        Arrays.fill(h, -1);
        Arrays.fill(dfn, 0);
        Arrays.fill(cut, false);
        idx = 0;
        top = 0;
        timestamp = 0;
        for (int i = 1; i <= vDccNo; i++) {
            vDccs[i].clear();
        }
        vDccNo = 0;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }

        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                root = i;
                tarjanCutVDccNo(i);

            }
        }
        tarjanCutVDccReBuild(n);
        Arrays.fill(d, 0);
        Arrays.fill(vis, false);
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                bfs(i, vDccH, vDccNe, vDccE);
            }
        }
        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int x = point[in.nextInt() - 1];
            int y = point[in.nextInt() - 1];
            int p = lca(x, y);
            int res = (d[x] + d[y] - d[p] * 2) / 2;
            out.println(res);
        }

    }

    void bfs(int start, int[] h, int[] ne, int[] e) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        d[start] = 1;
        vis[start] = true;
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
                    vis[j] = true;
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


    void tarjanCutVDccNo(int u) {
        dfn[u] = low[u] = ++timestamp;
        vDccStack[++top] = u;
        // 孤立点
        if (u == root && h[u] == -1) {
            vDccNo++;
            vDccs[vDccNo] = new ArrayList<>();
            vDccs[vDccNo].add(u);
            return;
        }
        int son = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                tarjanCutVDccNo(j);
                low[u] = Math.min(low[u], low[j]);
                if (low[j] >= dfn[u]) {
                    son++;
                    if (u != root || son > 1) {
                        cut[u] = true;
                    }
                    vDccNo++;
                    vDccs[vDccNo] = new ArrayList<>();
                    vDccs[vDccNo].add(u);
                    do {
                        vDccs[vDccNo].add(vDccStack[top]);
                        top--;
                    } while (vDccStack[top + 1] != j);
                }
            } else {
                low[u] = Math.min(low[u], dfn[j]);
            }
        }
    }


    void tarjanCutVDccReBuild(int n) {
        Arrays.fill(vDccH, -1);
        vDccIdx = 0;
        // 从v-dcc编号的下一个开始
        num = vDccNo;
        // 对所有的点(1-n)
        int[] cutNewId = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (cut[i]) {
                cutNewId[i] = ++num;
            }
        }
        // 对每个v-dcc，在每个割点和包含它的v-dcc中连边
        for (int i = 1; i <= vDccNo; i++) {
            for (int j = 0; j < vDccs[i].size(); j++) {
                int x = vDccs[i].get(j);
                if (cut[x]) {
                    addVDcc(i, cutNewId[x]);
                    addVDcc(cutNewId[x], i);
                }
                vDccC[x] = i;
            }
            for (int j = 0; j < vDccs[i].size(); j++) {
                int x = vDccs[i].get(j);
                for (int k = h[x]; k != -1; k = ne[k]) {
                    int v = e[k];
                    // 在同一个联通块
                    if (vDccC[v] == i) {
                        point[k / 2] = i;
                    }
                }
            }

        }
    }

    void addVDcc(int a, int b) {
        vDccE[vDccIdx] = b;
        vDccNe[vDccIdx] = vDccH[a];
        vDccH[a] = vDccIdx++;
    }
}
