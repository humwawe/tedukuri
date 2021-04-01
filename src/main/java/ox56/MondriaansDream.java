package ox56;

import common.io.InputReader;
import common.io.OutputWriter;

public class MondriaansDream {
    int n, m;
    int N = 12;
    int M = 1 << N;
    long[][] f = new long[N][M];
    boolean[] st = new boolean[M];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 & m == 0) {
            return;
        }
        int lim = 1 << m;
        for (int i = 0; i < lim; i++) {
            int cnt = 0;
            boolean valid = true;
            for (int j = 0; j < m; j++) {
                if ((i >> j & 1) == 1) {
                    if ((cnt & 1) == 1) {
                        valid = false;
                        break;
                    }
                    cnt = 0;
                } else {
                    cnt++;
                }

            }
            if ((cnt & 1) == 1) {
                valid = false;
            }
            st[i] = valid;
        }

        f[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < lim; j++) {
                f[i][j] = 0;
                for (int k = 0; k < lim; k++) {
                    if ((j & k) == 0 && st[k | j]) {
                        f[i][j] += f[i - 1][k];
                    }
                }
            }
        }
        out.println(f[n][0]);
    }
}
