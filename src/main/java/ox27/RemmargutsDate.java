package ox27;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class RemmargutsDate {
    int N = (int) (1e4 + 10);
    int M = (int) (2e5 + 10);
    int[] h = new int[N];
    int[] rh = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        idx = 0;
        Arrays.fill(h, -1);
        Arrays.fill(rh, -1);
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(h, a, b, c);
            add(rh, b, a, c);
        }
        int s = in.nextInt();
        int t = in.nextInt();
        int k = in.nextInt();
        if (s == t) {
            k++;
        }
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
        int[] f = new int[n + 5];
        int inf = 0x3f3f3f3f;
        Arrays.fill(f, inf);
        int[] vis = new int[n + 5];
        priorityQueue.add(new int[]{t, 0});
        f[t] = 0;
        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();
            if (vis[cur[0]] != 0) {
                continue;
            }
            vis[cur[0]] = 1;
            for (int i = rh[cur[0]]; i != -1; i = ne[i]) {
                int j = e[i];
                if (f[j] > f[cur[0]] + w[i]) {
                    f[j] = f[cur[0]] + w[i];
                    priorityQueue.add(new int[]{j, f[j]});
                }
            }
        }
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
        priorityQueue.add(new int[]{s, f[s], 0});
        Arrays.fill(vis, 0);
        while (!priorityQueue.isEmpty()) {
            int[] cur = priorityQueue.poll();
            vis[cur[0]]++;
            if (cur[0] == t && vis[cur[0]] == k) {
                out.println(cur[2]);
                return;
            }
            for (int i = h[cur[0]]; i != -1; i = ne[i]) {
                int j = e[i];
                if (vis[j] >= k) {
                    continue;
                }
                priorityQueue.add(new int[]{j, cur[2] + w[i] + f[j], cur[2] + w[i]});
            }
        }
        out.println(-1);
    }

    private void add(int[] h, int a, int b, int c) {
        w[idx] = c;
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
