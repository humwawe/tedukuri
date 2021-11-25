package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MineConstruction {
    int N = 1005;
    int M = 1005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int n, m;
    boolean[] cut = new boolean[N];
    int root;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        Arrays.fill(dfn, 0);
        Arrays.fill(cut, false);
        n = 0;
        idx = 0;
        top = 0;
        timestamp = 0;
        for (int i = 1; i <= vDccNo; i++) {
            vDccs[i].clear();
        }
        vDccNo = 0;
        m = in.nextInt();
        if (m == 0) {
            return;
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            n = Math.max(n, a);
            n = Math.max(n, b);
            add(a, b);
            add(b, a);
        }
        for (int i = 1; i <= n; i++) {
            root = i;
            if (dfn[i] == 0) {
                tarjanCutVDccNo(i);
            }
        }
        int res = 0;
        long num = 1;
        for (int i = 1; i <= vDccNo; i++) {
            int cnt = 0;
            for (int j = 0; j < vDccs[i].size(); j++) {
                if (cut[vDccs[i].get(j)]) {
                    cnt++;
                }
            }
            if (cnt == 0) {
                if (vDccs[i].size() > 1) {
                    res += 2;
                    num *= vDccs[i].size() * (vDccs[i].size() - 1) / 2;
                } else {
                    res++;
                }
            } else if (cnt == 1) {
                res++;
                num *= vDccs[i].size() - 1;
            }
        }
        out.printf("Case %d: %d %d\n", testNumber, res, num);

    }

    List<Integer>[] vDccs = new List[N];
    int vDccNo;
    int[] vDccStack = new int[N];

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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

}
