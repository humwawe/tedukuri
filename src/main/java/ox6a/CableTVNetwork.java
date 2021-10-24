package ox6a;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class CableTVNetwork {
    int n, m;
    int N = 56;
    int M = 20006;
    int[] a = new int[N * N];
    int[] b = new int[N * N];
    int[] d = new int[N << 1];

    int[] h = new int[N << 1];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx;
    int inf = 0x3f3f3f3f;
    int s, t;
    int maxflow;
    int[] now = new int[M];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            String s = in.nextString();
            String[] split = s.split(",");
            a[i] = Integer.parseInt(split[0].substring(1));
            b[i] = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
        }
        int res = inf;
        for (s = 0; s < n; s++) {
            for (t = 0; t < n; t++) {
                if (s != t) {
                    Arrays.fill(h, -1);
                    idx = 0;
                    maxflow = 0;
                    for (int i = 0; i < n; i++) {
                        if (i == s || i == t) {
                            add(i, i + n, inf);
                            add(i + n, i, 0);
                        } else {
                            add(i, i + n, 1);
                            add(i + n, i, 0);
                        }
                    }
                    for (int i = 0; i < m; i++) {
                        add(a[i] + n, b[i], inf);
                        add(b[i], a[i] + n, 0);
                        add(b[i] + n, a[i], inf);
                        add(a[i], b[i] + n, 0);
                    }
                    res = Math.min(res, dinic());
                }
            }
        }
        if (n <= 1 || res == inf) {
            res = n;
        }
        out.println(res);

    }

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

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
