package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TheCoreOfTheTreeNet {
    int n, s;
    int N = (int) (5e5 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] dis = new int[N];
    int[] pre = new int[N];
    int[] path = new int[N];
    boolean[] vis = new boolean[N];
    int[] f = new int[N];
    int[] b = new int[N];
    int[] sum = new int[N];
    int len;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        s = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        len = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }
        int p = bfs(1);
        int q = bfs(p);
        while (q != p) {
            path[len++] = q;
            b[len] = w[pre[q]];
            q = e[pre[q] ^ 1];
        }
        path[len++] = p;
        for (int i = 0; i < len; i++) {
            vis[path[i]] = true;
        }
        int maxF = 0;
        for (int i = 0; i < len; i++) {
            dfs(path[i]);
            maxF = Math.max(maxF, f[path[i]]);
            sum[i + 1] = sum[i] + b[i + 1];
        }
        int res = 1 << 30;
        int j = 0;
        for (int i = 0; i < len; i++) {
            while (j < len - 1 && sum[j + 1] - sum[i] <= s) {
                j++;
            }
            res = Math.min(res, Math.max(maxF, Math.max(sum[i], sum[len] - sum[j])));
        }
        out.println(res);
    }

    private void dfs(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            dfs(j);
            f[u] = Math.max(f[u], f[j] + w[i]);
        }

    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    private int bfs(int u) {
        Arrays.fill(dis, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(u);
        dis[u] = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = h[cur]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dis[j] == -1) {
                    dis[j] = dis[cur] + w[i];
                    // 记录转移下标
                    pre[j] = i;
                    queue.add(j);
                }
            }
        }
        int p = 0;
        for (int i = 1; i <= n; i++) {
            if (dis[p] < dis[i]) {
                p = i;
            }
        }
        return p;
    }
}
