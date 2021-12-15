package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KingsQuest {
    int N = 4005;
    int M = 200005;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int[] dfn = new int[N];
    int[] low = new int[N];
    int timestamp;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            int k = in.nextInt();
            for (int j = 0; j < k; j++) {
                int y = in.nextInt() + n;
                add(i, y);
            }
        }
        for (int i = 1; i <= n; i++) {
            int x = in.nextInt();
            add(x + n, i);
        }
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }

        for (int u = 1; u <= n; u++) {
            List<Integer> list = new ArrayList<>();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (sccC[u] == sccC[j]) {
                    list.add(j - n);
                }
            }
            Collections.sort(list);
            out.print(list.size());
            for (Integer integer : list) {
                out.print(" " + integer);
            }
            out.println();
        }
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

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
