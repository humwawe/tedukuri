package ox68;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MissileDefenseTower {
    int N = 55;
    double eps = 1e-10;
    int n, m;
    double t1, t2, v;
    Point[] a = new Point[N];
    Point[] b = new Point[N];
    boolean[] vis = new boolean[N * N];
    int[] match = new int[N * N];
    int M = N * N * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    double[][] dist = new double[N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        t1 = in.nextInt();
        t2 = in.nextInt();
        v = in.nextInt();
        t1 /= 60;
        for (int i = 1; i <= m; i++) {
            a[i] = new Point(in.nextInt(), in.nextInt());
        }
        for (int i = 1; i <= n; i++) {
            b[i] = new Point(in.nextInt(), in.nextInt());
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dist[i][j] = Math.sqrt((a[i].x - b[j].x) * (a[i].x - b[j].x) + (a[i].y - b[j].y) * (a[i].y - b[j].y)) / v;
            }
        }
        double l = 0, r = 1e8;
        while (r - l > 1e-8) {
            Arrays.fill(h, -1);
            idx = 0;
            double mid = (l + r) / 2;
            int p = (int) ((mid + t2) / (t1 + t2));
            p = Math.min(p, m);
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    for (int k = 1; k <= p; k++) {
                        // a <= b
                        if (k * t1 + (k - 1) * t2 + dist[i][j] < mid + eps) {
                            add(i, (j - 1) * p + k);
                        }
                    }
                }
            }
            Arrays.fill(match, 0);
            boolean f = true;
            for (int i = 1; i <= m; i++) {
                Arrays.fill(vis, false);
                if (!find(i)) {
                    f = false;
                    break;
                }
            }
            if (f) {
                r = mid;
            } else {
                l = mid;
            }
        }
        out.printf("%.6f\n", l);

    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }
            }
        }
        return false;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
