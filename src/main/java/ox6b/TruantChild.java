package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TruantChild {
    int N = (int) (2e5 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    long[] w = new long[M];
    int idx;
    int n, m;
    long[] dist = new long[N];
    long[] dist2 = new long[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            add(a, b, c);
            add(b, a, c);
        }
        int p = bfs(1, dist);
        int q = bfs(p, dist);
        bfs(q, dist2);
        long res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, Math.min(dist[i], dist2[i]) + dist[q]);
        }
        out.println(res);

    }

    private int bfs(int u, long[] dist) {
        Arrays.fill(dist, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(u);
        dist[u] = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] == -1) {
                    dist[j] = dist[cur] + w[i];
                    queue.add(j);
                }
            }
        }
        int p = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[p] < dist[i]) {
                p = i;
            }
        }
        return p;
    }

    void add(int a, int b, long c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
