package ox52;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JuryCompromise {
    int N = 210, M = 21, base = 400;
    int[][][] dp = new int[N][M][810];
    int n, m;
    int[] p = new int[N];
    int[] d = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            p[i] = in.nextInt();
            d[i] = in.nextInt();
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Arrays.fill(dp[i][j], -0x3f3f3f3f);
            }
        }
        dp[0][0][base] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = -base; k <= base; k++) {
                    dp[i][j][k + base] = dp[i - 1][j][k + base];
                    int t = k + base - (p[i] - d[i]);
                    if (t < 0 || t > 800) {
                        continue;
                    }
                    if (j < 1) {
                        continue;
                    }
                    dp[i][j][k + base] = Math.max(dp[i][j][k + base], dp[i - 1][j - 1][t] + p[i] + d[i]);
                }
            }
        }
        int v = 0;
        while (dp[n][m][base - v] < 0 && dp[n][m][base + v] < 0) {
            v++;
        }
        v = (dp[n][m][base + v] > dp[n][m][base - v] ? base + v : base - v);
        List<Integer> res = new ArrayList<>();
        int i = n, j = m, k = v;
        while (j > 0) {
            if (dp[i][j][k] == dp[i - 1][j][k]) {
                i -= 1;
            } else {
                res.add(i);
                k -= (p[i] - d[i]);
                i -= 1;
                j -= 1;
            }
        }
        int sp = 0, sd = 0;
        for (Integer idx : res) {
            sp += p[idx];
            sd += d[idx];
        }
        Collections.sort(res);
        out.printf("Jury #%d\n", testNumber);
        out.printf("Best jury has value %d for prosecution and value %d for defence:\n", sp, sd);
        for (Integer idx : res) {
            out.print(" " + idx);
        }
        out.println();
        out.println();
    }
}
