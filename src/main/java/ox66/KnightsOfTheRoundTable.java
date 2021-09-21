package ox66;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightsOfTheRoundTable {
    int N = 1005;
    int M = 1000005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m;
    boolean[][] hate = new boolean[N][N];
    int root;
    List<Integer>[] vDccs = new List[N];
    int vDccNo;
    int[] vDccStack = new int[N];
    int top;
    int[] vDccC = new int[N];
    int[] c = new int[N];
    boolean[] able = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        Arrays.fill(h, -1);
        Arrays.fill(dfn, 0);
        Arrays.fill(low, 0);
        Arrays.fill(vDccC, 0);
        for (int i = 1; i <= n; i++) {
            Arrays.fill(hate[i], false);
        }
        Arrays.fill(able, false);
        idx = 0;
        top = 0;
        timestamp = 0;
        vDccNo = 0;
        for (int i = 1; i <= m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            hate[a][b] = hate[b][a] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && !hate[i][j]) {
                    add(i, j);
                }
            }
        }
        vDccNo = 0;
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                root = i;
                tarjanCutVDccNo(i);
            }
        }
        for (int i = 1; i <= vDccNo; i++) {
            for (int j = 0; j < vDccs[i].size(); j++) {
                int x = vDccs[i].get(j);
                vDccC[x] = i;
                c[x] = 0;
            }
            if (dfs(vDccs[i].get(0), i, 1)) {
                for (int j = 0; j < vDccs[i].size(); j++) {
                    able[vDccs[i].get(j)] = true;
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (!able[i]) {
                res++;
            }
        }
        out.println(res);

    }

    private boolean dfs(int u, int vDccIdx, int color) {
        c[u] = color;
        boolean odd = false;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vDccC[j] == vDccIdx) {
                if (c[j] == 0) {
                    odd |= dfs(j, vDccIdx, 3 - color);
                } else if (c[j] != 3 - color) {
                    odd = true;
                }
            }
        }
        return odd;
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
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                tarjanCutVDccNo(j);
                low[u] = Math.min(low[u], low[j]);
                if (low[j] >= dfn[u]) {
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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
