package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class CornFields {

    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int mod = (int) 1e8;

        int[][] a = new int[n + 1][m];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = in.nextInt();
            }
        }
        int lim = 1 << m;
        int[][] f = new int[n + 1][lim];
        boolean[] ok = new boolean[lim];
        for (int i = 0; i < lim; i++) {
            ok[i] = (i & (i << 1)) == 0;
        }
        f[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < lim; j++) {
                if (ok[j] && f[i - 1][j] != 0) {
                    int st = j ^ (lim - 1);
                    int cur = st;
                    do {
                        if (ok[cur] && check(a[i], cur)) {
                            f[i][cur] = (f[i][cur] + f[i - 1][j]) % mod;
                        }
                        cur = (cur - 1) & st;
                    } while (cur != st);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < lim; i++) {
            res = (res + f[n][i]) % mod;
        }
        out.println(res);

    }


    private boolean check(int[] nums, int cur) {
        for (int i = 0; i < m; i++) {
            if (((cur >> i) & 1) == 1 && nums[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
