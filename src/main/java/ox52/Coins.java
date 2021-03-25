package ox52;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Coins {
    int C = 1010;
    int M = (int) (1e5 + 10);
    int N = 105;
    int[] a;
    int[] c;
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        a = in.nextIntArray(n);
        c = in.nextIntArray(n);

        boolean[] dp = new boolean[m + 1];
        dp[0] = true;
        int[] used = new int[m + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, 0);
            for (int j = a[i]; j <= m; j++) {
                if (!dp[j] && dp[j - a[i]] && used[j - a[i]] < c[i]) {
                    dp[j] = true;
                    used[j] = used[j - a[i]] + 1;
                }
            }
        }
        int res = 0;
        for (int j = 1; j <= m; j++) {
            if (dp[j]) {
                res++;
            }
        }
        out.println(res);
    }
}
