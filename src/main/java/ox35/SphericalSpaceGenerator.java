package ox35;

import common.io.InputReader;
import common.io.OutputWriter;

public class SphericalSpaceGenerator {
    int N = 15;
    int n;
    double[][] a = new double[N][N + 1];
    double eps = 1e-6;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        double[][] b = new double[n + 1][n];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = in.nextDouble();
            }
        }
        double[] c = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = 2 * (b[i][j] - b[i + 1][j]);
                c[i] += b[i][j] * b[i][j] - b[i + 1][j] * b[i + 1][j];
            }
            a[i][n] = c[i];
        }
        gauss();
        for (int i = 0; i < n; i++) {
            out.printf("%.3f ", a[i][n]);
        }
    }

    int gauss() {
        int c, r;
        for (c = 0, r = 0; c < n; c++) {
            int t = r;
            // 找到绝对值最大的行
            for (int i = r; i < n; i++) {
                if (Math.abs(a[i][c]) > Math.abs(a[t][c])) {
                    t = i;
                }
            }
            if (Math.abs(a[t][c]) < eps) {
                continue;
            }
            // 将绝对值最大的行换到最顶端
            for (int i = c; i <= n; i++) {
                double tmp = a[t][i];
                a[t][i] = a[r][i];
                a[r][i] = tmp;
            }
            // 将当前上的首位变成1
            for (int i = n; i >= c; i--) {
                a[r][i] /= a[r][c];
            }
            // 用当前行将下面所有的列消成0
            for (int i = r + 1; i < n; i++) {
                if (Math.abs(a[i][c]) > eps) {
                    for (int j = n; j >= c; j--) {
                        a[i][j] -= a[r][j] * a[i][c];
                    }
                }
            }
            r++;
        }

        if (r < n) {
            for (int i = r; i < n; i++) {
                if (Math.abs(a[i][n]) > eps) {
                    // 无解
                    return 2;
                }
            }
            // 有无穷多组解
            return 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                a[i][n] -= a[i][j] * a[j][n];
            }
        }
        // 有唯一解
        return 0;
    }
}
