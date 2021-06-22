package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class XorPathSum {
    int N = 105;
    int M = 20005;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int[] deg = new int[N];
    int idx;
    int n, m;
    double[][] a;
    double eps = 1e-9;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int v = in.nextInt();
            if (a == b) {
                add(a, b, v);
                deg[a]++;
            } else {
                add(a, b, v);
                add(b, a, v);
                deg[a]++;
                deg[b]++;
            }
        }
        double res = 0;
        for (int k = 0; k < 31; k++) {
            a = new double[N][N];
            for (int u = 1; u < n; u++) {
                a[u][u] = -deg[u];
                for (int i = h[u]; i != -1; i = ne[i]) {
                    int j = e[i];
                    int v = (w[i] >> k) & 1;
                    if (v == 1) {
                        a[u][j] -= 1;
                        a[u][n + 1] -= 1;
                    } else {
                        a[u][j] += 1;
                    }
                }
            }
            a[n][n] = 1;
            gause();
            res += (1 << k) * a[1][n + 1];
        }
        out.printf("%.3f", res);

    }

    void gause() {
        int c, r;
        for (c = 1, r = 1; c <= n; c++) {
            int t = r;
            // 找到绝对值最大的行
            for (int i = r; i <= n; i++) {
                if (Math.abs(a[i][c]) > Math.abs(a[t][c])) {
                    t = i;
                }
            }
            if (Math.abs(a[t][c]) < eps) {
                continue;
            }
            // 将绝对值最大的行换到最顶端
            for (int i = c; i <= n + 1; i++) {
                double tmp = a[t][i];
                a[t][i] = a[r][i];
                a[r][i] = tmp;
            }
            // 将当前上的首位变成1
            for (int i = n + 1; i >= c; i--) {
                a[r][i] /= a[r][c];
            }
            // 用当前行将下面所有的列消成0
            for (int i = r + 1; i <= n; i++) {
                if (Math.abs(a[i][c]) > eps) {
                    for (int j = n + 1; j >= c; j--) {
                        a[i][j] -= a[r][j] * a[i][c];
                    }
                }
            }
            r++;
        }

        for (int i = n; i >= 1; i--) {
            for (int j = i + 1; j <= n; j++) {
                a[i][n + 1] -= a[i][j] * a[j][n + 1];
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
