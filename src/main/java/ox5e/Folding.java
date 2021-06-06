package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class Folding {
    String s;
    int N = 110;
    int[][] a = new int[N][N];
    int[][] p = new int[N][N];
    int[][] f = new int[N][N];
    int[] strLen = new int[N];
    int n;
    OutputWriter out;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        this.out = out;
        s = in.nextString();
        n = s.length();
        for (int i = 1; i < 10; i++) {
            strLen[i] = 1;
        }
        for (int i = 10; i < 100; i++) {
            strLen[i] = 2;
        }
        strLen[100] = 3;

        for (int i = 1; i <= n; ++i) {
            f[i][i] = 1;
        }

        for (int len = 2; len <= n; len++) {
            for (int l = 1; l + len - 1 <= n; l++) {
                int r = l + len - 1;
                f[l][r] = r - l + 1;
                for (int k = l; k < r; k++) {
                    f[l][r] = Math.min(f[l][r], f[l][k] + f[k + 1][r]);
                    if (substr(l, k, r)) {
                        f[l][r] = Math.min(f[l][r], f[l][k] + strLen[(r - l + 1) / (k - l + 1)] + 2);
                    }
                }
            }
        }
        dfs(1, n);
    }

    private void dfs(int l, int r) {
        if (f[l][r] == r - l + 1) {
            for (int i = l; i <= r; i++) {
                out.print(s.charAt(i - 1));
            }
            return;
        }
        for (int k = l; k < r; k++) {
            if (f[l][r] == f[l][k] + f[k + 1][r]) {
                dfs(l, k);
                dfs(k + 1, r);
                return;
            }
            if (substr(l, k, r) && f[l][r] == f[l][k] + strLen[(r - l + 1) / (k - l + 1)] + 2) {
                out.print((r - l + 1) / (k - l + 1) + "(");
                dfs(l, k);
                out.print(")");
                return;
            }
        }
    }

    private boolean substr(int l, int k, int r) {
        int len = k - l + 1;
        if ((r - l + 1) % len != 0) {
            return false;
        }
        for (int i = 0; i < r - l + 1; i++) {
            if (s.charAt(l - 1 + i) != s.charAt(l - 1 + i % len)) {
                return false;
            }
        }
        return true;
    }
}
