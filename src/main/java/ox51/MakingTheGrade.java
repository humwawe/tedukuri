package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MakingTheGrade {
    int n;
    int[] a1, a2, b;
    int[][] dp;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a1 = new int[n];
        a2 = new int[n];
        b = new int[n];
        dp = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            a1[i] = x;
            a2[n - 1 - i] = x;
            b[i] = x;
        }
        Arrays.sort(b);
        int res = Math.min(helper(a1), helper(a2));
        out.println(res);

    }

    private int helper(int[] a) {
        for (int i = 1; i <= n; i++) {
            int min = inf;
            for (int j = 1; j <= n; j++) {
                min = Math.min(min, dp[i - 1][j]);
                dp[i][j] = min + Math.abs(b[j - 1] - a[i - 1]);
            }
        }
        int res = inf;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res, dp[n][i]);
        }
        return res;
    }
}
