package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wedding {
    int n, m;
    int N = 205;
    int M = N * 2;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;

    int sccCnt;
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

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        Arrays.fill(h, -1);
        idx = 0;
        sccCnt = 0;
        timestamp = 0;
        top = 0;
        Arrays.fill(dfn, 0);
        add(0, n);
        for (int i = 0; i < m; i++) {
            String fi = in.nextString();
            String se = in.nextString();
            int a = Integer.parseInt(fi.substring(0, fi.length() - 1));
            char b = fi.charAt(fi.length() - 1);
            int c = Integer.parseInt(se.substring(0, se.length() - 1));
            char d = se.charAt(se.length() - 1);
            int pa, pc;
            if (b == 'h') {
                pa = a + n;
            } else {
                pa = a;
                a += n;
            }
            if (d == 'h') {
                pc = c + n;
            } else {
                pc = c;
                c += n;
            }
            add(pa, c);
            add(pc, a);
        }
        for (int i = 0; i < 2 * n; ++i) {
            if (dfn[i] == 0) {
                tarjanScc(i);
            }
        }
        if (!check()) {
            out.println("bad luck");
            return;
        }
        for (int i = 1; i < n; i++) {
            out.print(i);
            if (sccC[i] < sccC[i + n]) {
                out.print("h ");
            } else {
                out.print("w ");
            }
        }
        out.println();
    }

    private boolean check() {
        for (int i = 0; i < n; ++i) {
            if (sccC[i] == sccC[i + n]) {
                return false;
            }
        }
        return true;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
