package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class TurtleChess {
    int n, m;
    int[] cnt = new int[4];
    int N = 41;
    int[][][][] dp = new int[N][N][N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int[] a = in.nextIntArray(n);
        for (int i = 0; i < m; i++) {
            cnt[in.nextInt() - 1]++;
        }

        for (int i = 0; i <= cnt[0]; i++) {
            for (int j = 0; j <= cnt[1]; j++) {
                for (int k = 0; k <= cnt[2]; k++) {
                    for (int l = 0; l <= cnt[3]; l++) {
                        int cur = i * 1 + j * 2 + k * 3 + l * 4;
                        dp[i][j][k][l] = a[cur];
                        if (i != 0) {
                            dp[i][j][k][l] = Math.max(dp[i][j][k][l], dp[i - 1][j][k][l] + a[cur]);
                        }
                        if (j != 0) {
                            dp[i][j][k][l] = Math.max(dp[i][j][k][l], dp[i][j - 1][k][l] + a[cur]);
                        }
                        if (k != 0) {
                            dp[i][j][k][l] = Math.max(dp[i][j][k][l], dp[i][j][k - 1][l] + a[cur]);
                        }
                        if (l != 0) {
                            dp[i][j][k][l] = Math.max(dp[i][j][k][l], dp[i][j][k][l - 1] + a[cur]);
                        }
                    }
                }
            }
        }
        out.println(dp[cnt[0]][cnt[1]][cnt[2]][cnt[3]]);

    }
}
