package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Patrol {
    int n, k;
    int N = (int) (1e5 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] dis = new int[N];
    int[] f = new int[N];
    boolean[] vis = new boolean[N];
    int point;
    int l1, l2;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b, 1);
            add(b, a, 1);
        }
        bfs(1);
        bfs(point);
        if (k == 1) {
            out.println(2 * n - dis[point] - 1);
            return;
        }
        l1 = dis[point];
        change(point);
        Arrays.fill(dis, 0);
        Arrays.fill(vis, false);
        l2 = 0;
        dp(1);
        out.println(2 * n - l1 - l2);
    }

    void dp(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            dp(j);
            l2 = Math.max(l2, dis[u] + dis[j] + w[i]);
            dis[u] = Math.max(dis[u], dis[j] + w[i]);
        }

    }

    private void change(int u) {
        while (f[u] != 0) {
            int fa = f[u];
            for (int i = h[fa]; i != -1; i = ne[i]) {
                int j = e[i];
                if (j == u) {
                    w[i] = -1;
                    break;
                }
            }
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (j == fa) {
                    w[i] = -1;
                    break;
                }
            }
            u = fa;
        }
    }

    private void bfs(int u) {
        f[u] = 0;
        Arrays.fill(dis, 0);
        Arrays.fill(vis, false);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(u);
        vis[u] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int j = e[i];
                if (!vis[j]) {
                    vis[j] = true;
                    dis[j] = dis[cur] + w[i];
                    queue.add(j);
                    f[j] = cur;
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (res < dis[i]) {
                res = dis[i];
                point = i;
            }
        }
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

}
