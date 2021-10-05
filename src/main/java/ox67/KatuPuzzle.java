package ox67;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class KatuPuzzle {
    int n, m;
    int N = 2005, M = 4000005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int sccCnt = 0;
    // 某个点属于哪个强连通分量
    int[] sccC = new int[N];
    int[] sccStack = new int[N];
    boolean[] inSccStack = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int k = in.nextInt();
            String str = in.nextString();
            char c = str.charAt(0);
            if (c == 'A' && k == 0) {
                add(n + a, b);
                add(n + b, a);
            }
            if (c == 'A' && k == 1) {
                add(a, n + a);
                add(b, n + b);
            }
            if (c == 'O' && k == 0) {
                add(n + a, a);
                add(n + b, b);
            }
            if (c == 'O' && k == 1) {
                add(a, n + b);
                add(b, n + a);
            }
            if (c == 'X' && k == 0) {
                add(a, b);
                add(b, a);
                add(n + a, n + b);
                add(n + b, n + a);
            }
            if (c == 'X' && k == 1) {
                add(a, n + b);
                add(b, n + a);
                add(n + a, b);
                add(n + b, a);
            }
        }
        for (int i = 0; i < 2 * n; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (sccC[i] == sccC[i + n]) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

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
}
