package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class FlowerShopWindow {
    int f, v;
    private int inf = (int) 1e8;


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        f = in.nextInt();
        v = in.nextInt();
        int[][] a = new int[f][v];
        for (int i = 0; i < f; i++) {
            for (int j = 0; j < v; j++) {
                a[i][j] = in.nextInt();
            }
        }

        int[][] dp = new int[v + 1][f + 1];
        for (int[] d : dp) {
            Arrays.fill(d, -inf);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= v; i++) {
            for (int j = 0; j <= f; j++) {
                if (j > i) {
                    break;
                }
                dp[i][j] = dp[i - 1][j];
                if (j > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + a[j - 1][i - 1]);
                }
            }
        }
        int i = v;
        int j = f;
        int tmp = dp[i][j];
        int[] res = new int[f];
        while (j > 0) {
            while (i > 0 && dp[i][j] == tmp) {
                i--;
            }
            res[--j] = i + 1;
            tmp = dp[i][j];
        }
        out.println(dp[v][f]);
        out.println(res);
    }
}
