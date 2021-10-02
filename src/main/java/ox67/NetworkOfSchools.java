package ox67;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class NetworkOfSchools {
    int N = 105, M = 10005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int[] inDeg = new int[N];
    int[] outDeg = new int[N];
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            while (true) {
                int j = in.nextInt();
                if (j == 0) {
                    break;
                }
                add(i, j);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }
        for (int u = 1; u <= n; u++) {
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (sccC[u] == sccC[j]) {
                    continue;
                }
                outDeg[sccC[u]]++;
                inDeg[sccC[j]]++;
            }
        }

        int zeroIn = 0;
        int zeroOut = 0;
        for (int i = 1; i <= sccCnt; i++) {
            if (inDeg[i] == 0) {
                zeroIn++;
            }
            if (outDeg[i] == 0) {
                zeroOut++;
            }
        }

        out.println(zeroIn);
        out.println(sccCnt == 1 ? 0 : Math.max(zeroIn, zeroOut));
    }

    int sccCnt = 0;
    // 某个点属于哪个强连通分量
    int[] sccC = new int[N];
    int[] sccStack = new int[N];
    boolean[] inSccStack = new boolean[N];

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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
