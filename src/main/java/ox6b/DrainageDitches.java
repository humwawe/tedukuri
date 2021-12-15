package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class DrainageDitches {
    int N = 210;
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx;
    int maxflow;
    int s, t;
    int n, m;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        n = in.nextInt();
        m = in.nextInt();
        s = 1;
        t = m;
        for (int i = 0; i < n; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, 0);
        }
        out.println(dinic());
    }

    int[] d = new int[N];
    // 当前弧优化
    int[] now = new int[M];

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
