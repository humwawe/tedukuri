package ox53;

import common.io.InputReader;
import common.io.OutputWriter;

public class pyramid {
    int N = 310;
    int mod = (int) 1e9;
    Long[][] memo = new Long[N][N];
    String s;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        s = in.nextString();
        out.println(helper(0, s.length() - 1));
    }

    private long helper(int i, int j) {
        if (i == j) {
            return 1;
        }
        if (i > j) {
            return 0;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        long res = 0;
        for (int k = i + 2; k <= j; k += 2) {
            if (s.charAt(i) == s.charAt(j)) {
                res = (res + helper(i + 1, k - 1) * helper(k, j)) % mod;
            }
        }
        return memo[i][j] = res;
    }
}
