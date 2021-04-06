package ox56;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Treasure {
    int N = 12, M = 1 << 12;
    int inf = 0x3f3f3f3f;

    int[][] d = new int[N][N];
    int[][] f = new int[M][N];
    int[] g = new int[M];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < N; i++) {
            Arrays.fill(d[i], inf);
            d[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            int c = in.nextInt();
            d[a][b] = d[b][a] = Math.min(d[a][b], c);
        }
        for (int i = 1; i < 1 << n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) == 1) {
                    for (int k = 0; k < n; k++) {
                        if (d[j][k] != inf) {
                            g[i] |= 1 << k;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < M; i++) {
            Arrays.fill(f[i], inf);
        }
        for (int i = 0; i < n; i++) {
            f[1 << i][0] = 0;
        }
        for (int i = 1; i < 1 << n; i++) {
            for (int j = (i - 1) & i; j > 0; j = (j - 1) & i) {
                if ((g[j] & i) == i) {
                    int remain = i ^ j;
                    int cost = 0;
                    for (int k = 0; k < n; k++) {
                        if ((remain >> k & 1) == 1) {
                            int t = inf;
                            for (int l = 0; l < n; l++) {
                                if ((j >> l & 1) == 1) {
                                    t = Math.min(t, d[l][k]);
                                }
                            }
                            cost += t;
                        }
                    }
                    for (int k = 1; k < n; k++) {
                        f[i][k] = Math.min(f[i][k], f[j][k - 1] + cost * k);
                    }
                }
            }
        }
        int res = inf;
        for (int i = 0; i < n; i++) {
            res = Math.min(res, f[(1 << n) - 1][i]);
        }
        out.println(res);

    }
}
