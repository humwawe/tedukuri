package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MobileService {
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        m = in.nextInt();
        n = in.nextInt();
        int[][] w = new int[m + 1][m + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++) {
                w[i][j] = in.nextInt();
            }
        }
        int[] p = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            p[i] = in.nextInt();
        }
        int inf = 0x3f3f3f3f;
        int[][][] dp = new int[n + 1][m + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                Arrays.fill(dp[i][j], inf);
            }
        }
        p[0] = 3;
        dp[0][1][2] = 0;
        for (int i = 0; i < n; i++) {
            for (int x = 1; x <= m; x++) {
                for (int y = 1; y <= m; y++) {
                    int z = p[i];
                    int u = p[i + 1];
                    if (x == y || x == z || y == z) {
                        continue;
                    }
                    if (dp[i][x][y] == inf) {
                        continue;
                    }
                    dp[i + 1][x][y] = Math.min(dp[i + 1][x][y], dp[i][x][y] + w[z][u]);
                    dp[i + 1][y][z] = Math.min(dp[i + 1][y][z], dp[i][x][y] + w[x][u]);
                    dp[i + 1][x][z] = Math.min(dp[i + 1][x][z], dp[i][x][y] + w[y][u]);
                }
            }
        }
        int res = inf;
        for (int x = 0; x <= m; ++x) {
            for (int y = 0; y <= m; ++y) {
                int z = p[n];
                if (x == y || x == z || y == z) {
                    continue;
                }
                res = Math.min(res, dp[n][x][y]);
            }
        }
        out.println(res);
    }
}
