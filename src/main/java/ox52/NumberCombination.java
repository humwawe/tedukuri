package ox52;

import common.io.InputReader;
import common.io.OutputWriter;

public class NumberCombination {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] dp = new int[m + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = m; j >= a[i]; j--) {
                dp[j] += dp[j - a[i]];
            }
        }
        out.println(dp[m]);
    }
}
