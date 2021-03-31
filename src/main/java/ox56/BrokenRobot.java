package ox56;

import common.io.InputReader;
import common.io.OutputWriter;

public class BrokenRobot {
    int N = 1005;
    double[][] a = new double[N][N];
    double[][] f = new double[N][N];
    int n, m, x, y;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        x = in.nextInt();
        y = in.nextInt();
        if (m == 1) {
            out.printf("%.4f", (double) 2 * (n - x));
            return;
        }
        for (int i = n - 1; i >= x; i--) {
            a[1][1] = 2.0 / 3;
            a[1][2] = -1.0 / 3;
            a[1][m + 1] = f[i + 1][1] / 3 + 1;
            a[m][m] = 2.0 / 3;
            a[m][m - 1] = -1.0 / 3;
            a[m][m + 1] = f[i + 1][m] / 3 + 1;
            for (int j = 2; j < m; j++) {
                a[j][j - 1] = -1.0 / 4;
                a[j][j] = 3.0 / 4;
                a[j][j + 1] = -1.0 / 4;
                a[j][m + 1] = f[i + 1][j] / 4 + 1;
            }
            gauss();
            for (int j = 1; j <= m; j++) {
                f[i][j] = a[j][m + 1];
            }
        }
        out.printf("%.4f", f[x][y]);
    }

    private void gauss() {
        for (int i = 1; i <= m; i++) {
            double r = a[i][i];
            a[i][i] /= r;
            a[i][i + 1] /= r;
            if (i < m) {
                a[i][m + 1] /= r;
            }
            double t = a[i + 1][i];
            int[] d = new int[]{i, i + 1, m + 1};
            for (int j = 0; j < 3; j++) {
                a[i + 1][d[j]] -= t * a[i][d[j]];
            }
        }
        for (int i = m; i > 0; i--) {
            a[i - 1][m + 1] -= a[i - 1][i] * a[i][m + 1];
            a[i - 1][i] -= a[i - 1][i] * a[i][i];
        }
    }
}
