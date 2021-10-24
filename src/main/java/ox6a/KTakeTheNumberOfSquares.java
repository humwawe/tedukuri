package ox6a;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class KTakeTheNumberOfSquares {
    int N = 5005, M = 100005;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] cost = new int[M];
    int[] ne = new int[M];
    int idx;
    int inf = 0x3f3f3f3f;
    int s, t;
    int n, k, res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        k = in.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int c = in.nextInt();
                add(num(i, j, 0), num(i, j, 1), 1, c);
                add(num(i, j, 1), num(i, j, 0), 0, -c);
                add(num(i, j, 0), num(i, j, 1), k - 1, 0);
                add(num(i, j, 1), num(i, j, 0), 0, 0);
                if (j < n) {
                    add(num(i, j, 1), num(i, j + 1, 0), k, 0);
                    add(num(i, j + 1, 0), num(i, j, 1), 0, 0);
                }
                if (i < n) {
                    add(num(i, j, 1), num(i + 1, j, 0), k, 0);
                    add(num(i + 1, j, 0), num(i, j, 1), 0, 0);
                }
            }
        }
        s = 0;
        t = 2 * n * n;
        add(s, num(1, 1, 0), k, 0);
        add(num(1, 1, 0), s, 0, 0);
        while (spfaEK()) {
            updateEK();
        }
        out.println(res);
    }

    private void updateEK() {
        int x = t;
        while (x != s) {
            int i = pre[x];
            w[i] -= incf[t];
            w[i ^ 1] += incf[t];
            x = e[i ^ 1];
        }
        res += dist[t] * incf[t];
    }

    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    int[] incf = new int[N];
    int[] pre = new int[N];

    private boolean spfaEK() {
        Arrays.fill(vis, false);
        Arrays.fill(dist, -inf);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        vis[s] = true;
        dist[s] = 0;
        incf[s] = inf;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            vis[u] = false;
            for (int i = h[u]; i != -1; i = ne[i]) {
                // 剩余容量为0的话，说明不在残留网络中，不遍历
                if (w[i] > 0) {
                    int j = e[i];
                    if (dist[j] < dist[u] + cost[i]) {
                        dist[j] = dist[u] + cost[i];
                        incf[j] = Math.min(incf[u], w[i]);
                        pre[j] = i;
                        if (!vis[j]) {
                            queue.add(j);
                            vis[j] = true;
                        }
                    }
                }
            }
        }
        // 汇点是否可达，不可达表示已求出最大流
        return dist[t] != -inf;
    }

    void add(int a, int b, int c, int d) {
        e[idx] = b;
        w[idx] = c;
        cost[idx] = d;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    int num(int i, int j, int k) {
        return (i - 1) * n + j + k * n * n;
    }
}
