package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class SortingSlides {
    int N = 35 * 2;
    int M = N * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int maxflow;
    int s, t;
    int inf = 0x3f3f3f3f;
    int[] dfn = new int[N];
    int[] low = new int[N];
    int timestamp;
    int n;
    int[][] a = new int[N][4];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        top = 0;
        sccCnt = 0;
        maxflow = 0;
        timestamp = 0;
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
            a[i][2] = in.nextInt();
            a[i][3] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            for (int j = 1; j <= n; j++) {
                if (cover(a[j], x, y)) {
                    add(j, i + n, 1);
                    add(i + n, j, 0);
                }
            }
        }
        s = 0;
        t = 2 * n + 1;

        for (int i = 1; i <= n; i++) {
            add(s, i, 1);
            add(i, s, 0);
        }
        for (int i = 1; i <= n; i++) {
            add(n + i, t, 1);
            add(t, n + i, 0);
        }
        dinic();
        Arrays.fill(dfn, 0);
        Arrays.fill(now, 0);
        for (int i = s; i <= t; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }

        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < idx; i += 2) {
            int a = e[i ^ 1];
            int b = e[i];
            if (a == s || a == t || b == s || b == t) {
                continue;
            }
            if (w[i] == 0 && sccC[a] != sccC[b]) {
                res.add(new int[]{a, b - n});
            }
        }
        out.println("Heap " + testNumber);
        if (res.size() == 0) {
            out.println("none");
        } else {
            res.sort(Comparator.comparingInt(x -> x[0]));
            for (int[] r : res) {
                out.printf("(%c,%d) ", r[0] - 1 + 'A', r[1]);
            }
            out.println();
        }
        out.println();

    }

    private boolean cover(int[] slide, int x, int y) {
        return x >= slide[0] && x <= slide[1] && y >= slide[2] && y <= slide[3];
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    int sccCnt = 0;
    int[] sccC = new int[N];
    int[] sccStack = new int[N];
    boolean[] inSccStack = new boolean[N];

    // 有向图的强联通分量
    void tarjanScc(int u) {
        dfn[u] = low[u] = ++timestamp;
        sccStack[++top] = u;
        inSccStack[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            if (w[i] > 0) {
                int j = e[i];
                if (dfn[j] == 0) {
                    tarjanScc(j);
                    low[u] = Math.min(low[u], low[j]);
                } else if (inSccStack[j]) {
                    low[u] = Math.min(low[u], dfn[j]);
                }
            }
        }
        // 联通分量编号递减即是拓扑序
        if (dfn[u] == low[u]) {
            ++sccCnt;
            int y;
            do {
                y = sccStack[top--];
                inSccStack[y] = false;
                sccC[y] = sccCnt;
            } while (y != u);
        }
    }

    int[] d = new int[N];
    // 当前弧优化
    int[] now = new int[M];

    int dinic() {
        int flow;
        while (bfsDinic()) {
            while ((flow = dfsDinic(s, inf)) != 0) {
                maxflow += flow;
            }
        }
        return maxflow;
    }

    // 残差网络上构造分层图
    private boolean bfsDinic() {
        Arrays.fill(d, 0);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        d[s] = 1;
        now[s] = h[s];
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (w[i] > 0 && d[j] == 0) {
                    d[j] = d[u] + 1;
                    now[j] = h[j];
                    queue.add(j);
                    if (j == t) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int dfsDinic(int u, int flow) {
        if (u == t) {
            return flow;
        }
        int rest = flow;
        for (int i = now[u]; i != -1 && rest > 0; i = ne[i]) {
            // 当前弧优化（避免重复遍历从u出发不可扩展的边）
            now[u] = i;
            int j = e[i];
            if (w[i] > 0 && d[j] == d[u] + 1) {
                int k = dfsDinic(j, Math.min(rest, w[i]));
                // 剪枝，去掉增广完毕的点
                if (k == 0) {
                    d[j] = 0;
                }
                w[i] -= k;
                w[i ^ 1] += k;
                rest -= k;
            }
        }
        return flow - rest;
    }
}
