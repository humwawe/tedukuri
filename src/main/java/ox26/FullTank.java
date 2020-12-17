package ox26;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FullTank {
    int N = 1010;
    int M = 20010;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] price = in.nextIntArray(n);
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }
        int q = in.nextInt();
        f:
        while (q-- > 0) {
            int c = in.nextInt();
            int s = in.nextInt();
            int t = in.nextInt();
            int[][] dist = new int[n][c + 1];
            boolean[][] vis = new boolean[n][c + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dist[i], inf);
            }
            dist[s][0] = 0;
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> x[2]));
            priorityQueue.add(new int[]{s, 0, 0});
            while (!priorityQueue.isEmpty()) {
                int[] cur = priorityQueue.poll();
                int u = cur[0];
                int cc = cur[1];
                int d = cur[2];
                if (u == t) {
                    out.println(dist[u][cc]);
                    continue f;
                }
                if (vis[u][cc]) {
                    continue;
                }
                vis[u][cc] = true;
                if (cc < c) {
                    if (dist[u][cc + 1] > d + price[u]) {
                        dist[u][cc + 1] = d + price[u];
                        priorityQueue.add(new int[]{u, cc + 1, dist[u][cc + 1]});
                    }
                }
                for (int i = h[u]; i != -1; i = ne[i]) {
                    int j = e[i];
                    if (cc >= w[i]) {
                        if (dist[j][cc - w[i]] > d) {
                            dist[j][cc - w[i]] = d;
                            priorityQueue.add(new int[]{j, cc - w[i], dist[j][cc - w[i]]});
                        }
                    }
                }
            }
            out.println("impossible");
        }


    }

    private void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
