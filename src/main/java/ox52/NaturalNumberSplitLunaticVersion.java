package ox52;

import common.io.InputReader;
import common.io.OutputWriter;

public class NaturalNumberSplitLunaticVersion {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        long[] dp = new long[n + 1];
        long mod = 2147483648L;
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                dp[j] = (dp[j] + dp[j - i]) % mod;
            }
        }
        out.println(dp[n] - 1);
    }
}
