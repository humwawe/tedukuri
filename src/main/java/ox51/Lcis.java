package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

public class Lcis {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] b = in.nextIntArray(n);
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            int max = 0;
            for (int j = 1; j <= n; j++) {
                if (a[i - 1] == b[j - 1]) {
                    dp[i][j] = max + 1;
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
                if (b[j - 1] < a[i - 1]) {
                    max = Math.max(max, dp[i - 1][j]);
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dp[n][i]);
        }
        out.println(res);
    }
}
