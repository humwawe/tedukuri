package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class ChangeClassroom {
    int inf = 0x3f3f3f3f;
    int N = 2010, M = 310;
    int[] a = new int[N];
    int[] b = new int[N];
    double[] p = new double[N];
    int[][] d = new int[M][M];
    double[][][] dp = new double[N][N][2];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int v = in.nextInt();
        int e = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] b = in.nextIntArray(n);
        double[] p = new double[n];
        for (int i = 0; i < n; i++) {
            p[i] = in.nextDouble();
        }
        int[][] d = new int[v + 1][v + 1];
        for (int i = 0; i < v + 1; i++) {
            Arrays.fill(d[i], inf);
        }
        for (int i = 0; i < v + 1; i++) {
            d[i][i] = 0;
        }
        for (int i = 0; i < e; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int w = in.nextInt();
            d[x][y] = d[y][x] = Math.min(d[x][y], w);
        }
        for (int k = 1; k <= v; k++) {
            for (int i = 1; i <= v; i++) {
                for (int j = 1; j <= v; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
        double[][][] dp = new double[n + 1][m + 2][2];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 2; j++) {
                Arrays.fill(dp[i][j], inf);
            }
        }
        dp[0][0][0] = dp[0][1][1] = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j][0] = Math.min(dp[i - 1][j][0] + d[a[i - 1]][a[i]], dp[i - 1][j][1] + d[a[i - 1]][a[i]] * (1 - p[i - 1]) + d[b[i - 1]][a[i]] * p[i - 1]);
                if (j != 0) {
                    dp[i][j][1] = Math.min(dp[i - 1][j - 1][0] + d[a[i - 1]][a[i]] * (1 - p[i]) + d[a[i - 1]][b[i]] * p[i],
                            dp[i - 1][j - 1][1] + d[a[i - 1]][a[i]] * (1 - p[i - 1]) * (1 - p[i])
                                    + d[b[i - 1]][a[i]] * p[i - 1] * (1 - p[i])
                                    + d[a[i - 1]][b[i]] * (1 - p[i - 1]) * p[i]
                                    + d[b[i - 1]][b[i]] * p[i - 1] * p[i]);
                }
            }
        }
        double res = inf;
        for (int i = 0; i <= m; i++) {
            res = Math.min(res, Math.min(dp[n - 1][i][0], dp[n - 1][i][1]));
        }
        out.printf("%.2f", res);
    }
}
