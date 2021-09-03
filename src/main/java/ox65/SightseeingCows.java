package ox65;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class SightseeingCows {

    int N = 1005;
    int M = 5005;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    double[] w = new double[M];
    int idx;

    double[] dist = new double[N];
    int[] cnt = new int[N];
    boolean[] vis = new boolean[N];
    int n, m;
    int[] fun = new int[N];
    int[][] a = new int[M][3];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            fun[i] = in.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
            a[i][2] = in.nextInt();
        }
        double eps = 1e-6;
        double l = 0;
        double r = 1e6;
        while (r - l > eps) {
            double mid = (l + r) / 2;
            Arrays.fill(h, -1);
            idx = 0;
            for (int i = 1; i <= m; i++) {
                add(a[i][0], a[i][1], mid * a[i][2] - fun[a[i][0]]);
            }
            if (spfaHasCircle()) {
                l = mid;
            } else {
                r = mid;
            }
        }
        out.printf("%.2f\n", l);
    }

    boolean spfaHasCircle() {
        // 保证负环能可达（只放1，从1开始可能到不了负环，图不联通）
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            cnt[i] = 0;
            dist[i] = 0;
            vis[i] = true;
            queue.add(i);
        }
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    cnt[j] = cnt[t] + 1;
                    if (cnt[j] >= n) {
                        return true;
                    }
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }

        return false;
    }

    void add(int a, int b, double c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
