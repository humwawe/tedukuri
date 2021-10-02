package ox67;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Galaxy {
    int N = 100005, M = 300005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int[] inDeg = new int[N];
    int n, m;
    int[] d = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int op = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            if (op == 1) {
                add(a, b, 0);
                add(b, a, 0);
            } else if (op == 2) {
                add(a, b, 1);
            } else if (op == 3) {
                add(b, a, 0);
            } else if (op == 4) {
                add(b, a, 1);
            } else {
                add(a, b, 0);
            }
        }
        for (int i = 1; i <= n; i++) {
            add(0, i, 1);
        }
        for (int i = 0; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }

        if (!tarjanSccReBuild()) {
            out.println(-1);
            return;
        }
        topsort();
        long res = 0;
        for (int i = 1; i <= n; i++) {
            res += d[sccC[i]];
        }
        out.println(res);
    }

    private void topsort() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(sccC[0]);
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            for (int i = sccH[cur]; i != -1; i = sccNe[i]) {
                int j = sccE[i];
                d[j] = Math.max(d[j], d[cur] + sccW[i]);
                if (--inDeg[j] == 0) {
                    queue.add(j);
                }
            }
        }
    }

    // SCC缩点
    int[] sccH = new int[N];
    int[] sccE = new int[M];
    int[] sccW = new int[M];
    int[] sccNe = new int[M];
    int sccIdx;

    boolean tarjanSccReBuild() {
        Arrays.fill(sccH, -1);
        sccIdx = 0;
        // 对每条边
        for (int u = 0; u <= n; u++) {
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (sccC[u] == sccC[j]) {
                    if (w[i] == 1) {
                        return false;
                    }
                    continue;
                }
                inDeg[sccC[j]]++;
                // u->j
                addScc(sccC[u], sccC[j], w[i]);
            }
        }
        return true;
    }

    void addScc(int a, int b, int c) {
        sccE[sccIdx] = b;
        sccW[sccIdx] = c;
        sccNe[sccIdx] = sccH[a];
        sccH[a] = sccIdx++;
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
            int j = e[i];
            if (dfn[j] == 0) {
                tarjanScc(j);
                low[u] = Math.min(low[u], low[j]);
            } else if (inSccStack[j]) {
                low[u] = Math.min(low[u], dfn[j]);
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

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
