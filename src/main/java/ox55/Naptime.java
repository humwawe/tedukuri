package ox55;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Naptime {
    int N = 4000;
    int[][][] f = new int[2][N][2];
    int[] a;
    int n, m;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = in.nextIntArray(n);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(f[i][j], -inf);
            }
        }
        f[0][0][0] = f[0][1][1] = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                f[i & 1][j][0] = Math.max(f[i - 1 & 1][j][0], f[i - 1 & 1][j][1]);
                if (j != 0) {
                    f[i & 1][j][1] = Math.max(f[i - 1 & 1][j - 1][0], f[i - 1 & 1][j - 1][1] + a[i]);
                }
            }
        }
        int res = f[n - 1 & 1][m][0];
//        res = Math.max(res, f[n - 1 & 1][m][1]);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(f[i][j], -inf);
            }
        }
        f[0][1][1] = a[0];
        f[0][0][0] = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                f[i & 1][j][0] = Math.max(f[i - 1 & 1][j][0], f[i - 1 & 1][j][1]);
                if (j != 0) {
                    f[i & 1][j][1] = Math.max(f[i - 1 & 1][j - 1][0], f[i - 1 & 1][j - 1][1] + a[i]);
                }
            }
        }
        res = Math.max(res, f[n - 1 & 1][m][1]);
        out.println(res);
    }
}
