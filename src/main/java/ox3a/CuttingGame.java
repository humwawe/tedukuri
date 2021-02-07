package ox3a;

import common.io.InputReader;
import common.io.OutputWriter;

public class CuttingGame {
    int n;
    int m;
    int N = 205;
    Integer[][] memo = new Integer[N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        memo[2][2] = 0;
        memo[2][3] = 0;
        memo[3][2] = 0;
        int res = sg(n, m);
        if (res == 0) {
            out.println("LOSE");
        } else {
            out.println("WIN");
        }
    }

    private int sg(int n, int m) {
        if (memo[n][m] != null) {
            return memo[n][m];
        }
        boolean[] vis = new boolean[N];
        for (int i = 2; i <= n - 2; i++) {
            vis[sg(i, m) ^ sg(n - i, m)] = true;
        }
        for (int i = 2; i <= m - 2; i++) {
            vis[sg(n, i) ^ sg(n, m - i)] = true;
        }
        int p = 0;
        while (vis[p]) {
            p++;
        }
        return memo[n][m] = p;
    }
}
