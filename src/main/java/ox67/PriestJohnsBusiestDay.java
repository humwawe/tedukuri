package ox67;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class PriestJohnsBusiestDay {
    int n;
    int N = 2005, M = 4000005;
    int[] dfn = new int[N];
    int[] low = new int[N];

    int timestamp;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx, top;
    int sccCnt = 0;
    int[] sccC = new int[N];
    int[] sccStack = new int[N];
    boolean[] inSccStack = new boolean[N];
    Point[] a = new Point[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int sh, sm, th, tm;
            String s1 = in.nextString();
            sh = Integer.parseInt(s1.split(":")[0]);
            sm = Integer.parseInt(s1.split(":")[1]);
            String s2 = in.nextString();
            th = Integer.parseInt(s2.split(":")[0]);
            tm = Integer.parseInt(s2.split(":")[1]);
            int d = in.nextInt();
            a[i] = new Point(sh * 60 + sm, th * 60 + tm, d);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (overlap(a[i].s, a[i].s + a[i].d, a[j].s, a[j].s + a[j].d)) {
                    add(i, n + j);
                    add(j, n + i);
                }
                if (overlap(a[i].s, a[i].s + a[i].d, a[j].t - a[j].d, a[j].t)) {
                    add(i, j);
                    add(n + j, n + i);
                }
                if (overlap(a[i].t - a[i].d, a[i].t, a[j].s, a[j].s + a[j].d)) {
                    add(n + i, n + j);
                    add(j, i);
                }
                if (overlap(a[i].t - a[i].d, a[i].t, a[j].t - a[j].d, a[j].t)) {
                    add(n + i, j);
                    add(n + j, i);
                }
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
        for (int i = 0; i < n; i++) {
            if (sccC[i] < sccC[n + i]) {
                out.printf("%02d:%02d %02d:%02d\n", a[i].s / 60, a[i].s % 60, (a[i].s + a[i].d) / 60, (a[i].s + a[i].d) % 60);
            } else {
                out.printf("%02d:%02d %02d:%02d\n", (a[i].t - a[i].d) / 60, (a[i].t - a[i].d) % 60, a[i].t / 60, a[i].t % 60);
            }
        }

    }

    // [a,b] [c,d] 是否重叠
    boolean overlap(int a, int b, int c, int d) {
        return a >= c && a < d || b > c && b <= d || a <= c && b >= d;
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

    class Point {
        int s;
        int t;
        int d;

        public Point(int s, int t, int d) {
            this.s = s;
            this.t = t;
            this.d = d;
        }
    }
}
