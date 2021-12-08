package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class GoingFromUToVOrFromVToU {
    int N = 1005;
    int M = 6005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int n;
    int sccCnt = 0;
    int[] sccC = new int[N];
    List<Integer>[] sccList = new List[N];
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
        if (dfn[u] == low[u]) {
            ++sccCnt;
            sccList[sccCnt] = new ArrayList<>();
            int y;
            do {
                y = sccStack[top--];
                inSccStack[y] = false;
                sccC[y] = sccCnt;
                sccList[sccCnt].add(y);
            } while (y != u);
        }
    }

    // SCC缩点
    int[] sccH = new int[N];
    int[] sccE = new int[M];
    int[] sccNe = new int[M];
    int sccIdx;
    int[] inDeg = new int[N];

    void tarjanSccReBuild() {
        Arrays.fill(sccH, -1);
        sccIdx = 0;
        for (int u = 1; u <= n; u++) {
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (sccC[u] == sccC[j]) {
                    continue;
                }
                addScc(sccC[u], sccC[j]);
                inDeg[sccC[j]]++;
            }
        }
    }

    void addScc(int a, int b) {
        sccE[sccIdx] = b;
        sccNe[sccIdx] = sccH[a];
        sccH[a] = sccIdx++;
    }

    int m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        top = 0;
        timestamp = 0;
        sccCnt = 0;
        Arrays.fill(dfn, 0);
        Arrays.fill(inSccStack, false);
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
        }
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }
        tarjanSccReBuild();
        Queue<Integer> queue = new ArrayDeque<>();
        int cnt = 0;
        for (int i = 1; i <= sccCnt; i++) {
            if (inDeg[i] == 0) {
                cnt++;
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            cnt = 0;
            for (int i = sccH[cur]; i != -1; i = sccNe[i]) {
                int j = sccE[i];
                if (--inDeg[j] == 0) {
                    queue.add(j);
                    cnt++;
                }
            }
            if (cnt > 1) {
                out.println("No");
                return;
            }
        }
        out.println("Yes");
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
