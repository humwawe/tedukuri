package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class EnergyNecklace {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] a = new int[2 * n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            a[i + n] = a[i];
        }
        int[][] dp = new int[2 * n][2 * n];
        for (int len = 3; len <= n + 1; len++) {
            for (int i = 0; i + len - 1 < 2 * n; i++) {
                int j = i + len - 1;
                for (int k = i + 1; k <= j - 1; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + a[i] * a[k] * a[j]);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i][i + n]);
        }
        out.println(res);
    }
}
