package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class TttMatrix {
    int p = 10000007;
    int n, m;
    int[] f;
    int[][] g;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        f = new int[n + 2];
        g = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            f[i] = in.nextInt();
        }
        f[0] = 23;
        f[n + 1] = 3;
        for (int j = 0; j <= n; j++) {
            g[0][j] = 10;
            for (int i = 1; i <= n; i++) {
                if (i <= j) {
                    g[i][j] = 1;
                }
            }
            g[n + 1][j] = 1;
        }
        g[n + 1][n + 1] = 1;
        while (m > 0) {
            if ((m & 1) == 1) {
                f = mulself(f, g, p);
            }
            g = mul(g, g, p);
            m >>= 1;
        }
        out.println(f[n]);
    }

    int[][] mul(int[][] a, int[][] b, int p) {
        int[][] c = new int[n + 2][n + 2];
        for (int i = 0; i <= n + 1; i++) {
            for (int j = 0; j <= n + 1; j++) {
                for (int k = 0; k <= n + 1; k++) {
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % p);
                }
            }
        }
        return c;
    }

    int[] mulself(int[] a, int[][] b, int p) {
        int[] c = new int[n + 2];
        for (int j = 0; j <= n + 1; j++) {
            for (int k = 0; k <= n + 1; k++) {
                c[j] = (int) ((c[j] + (long) a[k] * b[k][j]) % p);
            }
        }
        return c;
    }
}
