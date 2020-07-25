package ox01;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Hamilton {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[][] w = new int[n][n];
        for (int i = 0; i < n; i++) {
            w[i] = in.nextIntArray(n);
        }
        int[][] dp = new int[1 << n][n];
        int inf = (int) 1e8;
        for (int i = 0; i < 1 << n; i++) {
            Arrays.fill(dp[i], inf);
        }
        dp[1][0] = 0;
        for (int i = 1; i < 1 << n; i++) {
            for (int j = 0; j < n; j++) {
                // 当前j位置为1
                if ((i >> j & 1) == 1) {
                    for (int k = 0; k < n; k++) {
                        // 上个j位置不为1，当前k位置为1
                        if (((i ^ 1 << j) >> k & 1) == 1) {
                            dp[i][j] = Math.min(dp[i][j], dp[i ^ 1 << j][k] + w[k][j]);
                        }
                    }
                }
            }
        }
        out.println(dp[(1 << n) - 1][n - 1]);
    }
}
