package ox62;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DarkCastle {
    int N = 1005;
    int M = 1000005;
    int[] dist = new int[N];
    boolean[] vis = new boolean[N];

    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }
        dijkstra2();
        int[] cnt = new int[n + 1];
        for (int u = 1; u <= n; u++) {
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] == dist[u] + w[i]) {
                    cnt[j]++;
                }
            }
        }
        long res = 1;
        int mod = 2147483647;
        for (int i = 1; i <= n; i++) {
            if (cnt[i] == 0) {
                continue;
            }
            res = res * cnt[i] % mod;
        }
        out.println(res);
    }

    void dijkstra2() {
        Arrays.fill(dist, 0x3f3f3f3f);
        dist[1] = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        priorityQueue.add(new int[]{0, 1});
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            int d = poll[0];
            int v = poll[1];
            if (vis[v]) {
                continue;
            }
            vis[v] = true;
            for (int i = h[v]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > d + w[i]) {
                    dist[j] = d + w[i];
                    priorityQueue.add(new int[]{dist[j], j});
                }
            }
        }
    }

    void add(int a, int b, int v) {
        e[idx] = b;
        w[idx] = v;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
