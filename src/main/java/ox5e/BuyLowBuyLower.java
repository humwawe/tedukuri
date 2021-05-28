package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class BuyLowBuyLower {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] dp = new int[n + 1];
        int[] f = new int[n + 1];
        int max = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = 1;
            for (int j = 1; j < i; j++) {
                if (a[i - 1] < a[j - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
            if (dp[i] == 1) {
                f[i] = 1;
            }
            for (int j = 1; j < i; j++) {
                if (dp[j] == dp[i] && a[i - 1] == a[j - 1]) {
                    f[j] = 0;
                }
                if (dp[i] == dp[j] + 1 && a[i - 1] < a[j - 1]) {
                    f[i] += f[j];
                }
            }
        }
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (dp[i] == max) {
                count += f[i];
            }
        }
        out.println(max + " " + count);
    }
}
