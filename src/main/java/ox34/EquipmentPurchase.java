package ox34;

import common.io.InputReader;
import common.io.OutputWriter;

public class EquipmentPurchase {
    int n, m;
    double[][] a;
    double eps = 1e-5;
    int res = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = in.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            a[i][m] = in.nextInt();
        }

        out.println(gauss() + " " + res);
    }

    int gauss() {
        int d = 0;
        int c, r;
        for (c = 0, r = 0; c < m; c++) {
            int t = r;
            for (int i = r; i < n; i++) {
                // 贪心的选价格低的
                if (Math.abs(a[i][c]) > eps && a[i][m] < a[t][m]) {
                    t = i;
                }
            }
            // 若果当前列为0，说明可以跳过，从r+1处继续
            if (Math.abs(a[t][c]) < eps) {
                r++;
                continue;
            }
            for (int i = c; i <= m; i++) {
                double tmp = a[t][i];
                a[t][i] = a[r][i];
                a[r][i] = tmp;
            }
            // 将当前上的首位变成1
            for (int i = m - 1; i >= c; i--) {
                a[r][i] /= a[r][c];
            }
            // 用当前行将下面所有的列消成0
            for (int i = r + 1; i < n; i++) {
                if (Math.abs(a[i][c]) > eps) {
                    for (int j = m - 1; j >= c; j--) {
                        a[i][j] -= a[r][j] * a[i][c];
                    }
                }
            }
            res += a[r][m];
            d++;
            r++;
        }
        return d;
    }
}
