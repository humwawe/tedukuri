package ox53;

import common.io.InputReader;
import common.io.OutputWriter;

public class MergingOfStones {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n + 1];
        int[] s = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            s[i] = s[i - 1] + a[i];
        }
        int[][] dp = new int[n + 1][n + 1];
        int inf = (int) 1e8;
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                dp[i][j] = inf;
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + s[j] - s[i - 1]);
                }
            }
        }
        out.println(dp[1][n]);
    }
}
