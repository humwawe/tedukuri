package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

public class PassingNote {
    int n, m;
    int[][][] dp = new int[105][55][55];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int[][] a = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                a[i][j] = in.nextInt();
            }
        }
        for (int i = 1; i <= n + m - 1; i++) {
            for (int x1 = Math.max(1, i - m + 1); x1 <= n && x1 <= i; x1++) {
                int y1 = i + 1 - x1;
                for (int x2 = Math.max(1, i - m + 1); x2 <= n && x2 <= i; x2++) {
                    int y2 = i + 1 - x2;
                    int t = a[x1][y1];
                    if (x1 != x2) {
                        t += a[x2][y2];
                    }
                    dp[i][x1][x2] = Math.max(dp[i][x1][x2], dp[i - 1][x1 - 1][x2 - 1] + t);
                    dp[i][x1][x2] = Math.max(dp[i][x1][x2], dp[i - 1][x1][x2 - 1] + t);
                    dp[i][x1][x2] = Math.max(dp[i][x1][x2], dp[i - 1][x1 - 1][x2] + t);
                    dp[i][x1][x2] = Math.max(dp[i][x1][x2], dp[i - 1][x1][x2] + t);
                }
            }
        }
        out.println(dp[n + m - 1][n][n]);
    }
}
