package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class GuardiansChallenge {
    int[] a;
    double[] p;
    int n, l, k;
    int N;
    Double[][][] dp;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        N = n;
        l = in.nextInt();
        k = in.nextInt();
        p = new double[n];
        for (int i = 0; i < n; i++) {
            p[i] = in.nextDouble() / 100;
        }
        a = in.nextIntArray(n);
        dp = new Double[n + 1][n + 1][2 * n + 1];
//        dp[0][0][Math.min(n + k, n + n)] = 1;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j <= i; j++) {
//                for (int m = 0; m <= 2 * n; m++) {
//                    dp[i + 1][j][m] += dp[i][j][m] * (1 - p[i]);
//                    if (m + a[i] >= 0) {
//                        dp[i + 1][j + 1][Math.min(m + a[i], 2 * n)] += dp[i][j][m] * p[i];
//                    }
//                }
//            }
//        }
//        double res = 0;
//        for (int i = l; i <= n; i++) {
//            for (int j = n; j <= 2 * n; j++) {
//                res += dp[n][i][j];
//            }
//        }

        out.println(dfs(1, 0, 0));
        out.printf("%.6f", dfs(0, 0, Math.min(k, n)));
    }

    private double dfs(int i, int cnt, int m) {
        if (i == n) {
            if (cnt < l) {
                return 0;
            }
            if (m < 0) {
                return 0;
            }
            return 1;
        }
        if (dp[i][cnt][m + N] != null) {
            return dp[i][cnt][m + N];
        }
        double res = 0;
        res += dfs(i + 1, cnt + 1, Math.min(m + a[i], n)) * p[i];
        res += dfs(i + 1, cnt, m) * (1 - p[i]);
        return dp[i][cnt][m + N] = res;
    }
}
