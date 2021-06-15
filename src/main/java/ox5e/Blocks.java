package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class Blocks {
    int n;
    int[] a;
    int[][][] memo;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = in.nextIntArray(n);
        memo = new int[n][n][n];
        out.printf("Case %d: %d\n", testNumber, dfs(0, n - 1, 0));
    }

    private int dfs(int i, int j, int len) {
        if (i > j) {
            return 0;
        }
        if (memo[i][j][len] != 0) {
            return memo[i][j][len];
        }
        if (i == j) {
            return memo[i][j][len] = (1 + len) * (1 + len);
        }
        int k = j;
        while (k >= i && a[k] == a[j]) {
            k--;
        }
        k++;
        int res = dfs(i, k - 1, 0) + (j - k + 1 + len) * (j - k + 1 + len);
        for (int l = i; l < k; l++) {
            if (a[l] == a[j] && a[l] != a[l + 1]) {
                res = Math.max(res, dfs(l + 1, k - 1, 0) + dfs(i, l, j - k + 1 + len));
            }
        }
        return memo[i][j][len] = res;
    }
}
