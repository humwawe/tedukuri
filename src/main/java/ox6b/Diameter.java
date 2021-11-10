package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Diameter {
    int N = (int) (2e5 + 10);
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx, len;
    int n;
    long[] dist = new long[N];
    int[] pre = new int[N];
    int[] path = new int[N];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        len = 0;
        n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int v = in.nextInt();
            add(a, b, v);
            add(b, a, v);
        }
        int p = bfs(1);
        int q = bfs(p);
        while (q != p) {
            path[len++] = q;
            q = e[pre[q] ^ 1];
        }
        path[len++] = p;
        for (int i = 0; i < len; i++) {
            vis[path[i]] = true;
        }
        int l = 0;
        int r = len - 1;
        for (int i = 0; i < len; i++) {
            int u = path[i];
            long s = dfs(u);
            if (s == dist[path[0]] - dist[u]) {
                l = i;
            }
            if (s == dist[u]) {
                r = i;
                break;
            }
        }
        out.println(dist[path[0]]);
        out.println(r - l);
    }

    private long dfs(int u) {
        vis[u] = true;
        long sum = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            sum = Math.max(sum, w[i] + dfs(j));
        }
        return sum;
    }

    private int bfs(int u) {
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
                    pre[j] = i;
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

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
