package ox5d;

import common.io.InputReader;
import common.io.OutputWriter;

public class Revelation {
    int N = 21;
    long[][] f = new long[N][4];
    long[] g = new long[N];

    {

        f[0][0] = 1;
        f[0][1] = f[0][2] = 0;
        g[0] = 0;
        for (int i = 1; i < N; i++) {
            f[i][0] = 9 * (f[i - 1][0] + f[i - 1][1] + f[i - 1][2]);
            f[i][1] = f[i - 1][0];
            f[i][2] = f[i - 1][1];
            g[i] = 10 * g[i - 1] + f[i - 1][2];
        }
    }

    int x;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        x = in.nextInt();
        int m = 3;
        while (g[m] < x) {
            m++;
        }
        for (int i = m, k = 0; i > 0; i--) {
            for (int j = 0; j <= 9; j++) {
                long cnt = g[i - 1];
                if (j == 6 || k == 3) {
                    for (int l = Math.max(3 - k - (j == 6 ? 1 : 0), 0); l < 3; l++) {
                        cnt += f[i - 1][l];
                    }
                }
                if (cnt >= x) {
                    out.print(j);
                    if (k < 3) {
                        if (j == 6) {
                            k++;
                        } else {
                            k = 0;
                        }
                    }
                    break;
                } else {
                    x -= cnt;
                }
            }
        }
        out.println();

    }
}
