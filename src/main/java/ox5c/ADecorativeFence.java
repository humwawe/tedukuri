package ox5c;

import common.io.InputReader;
import common.io.OutputWriter;

public class ADecorativeFence {
    int N = 21;
    int n, k;
    long c;
    long[][][] f = new long[N][N][2];

    {
        f[1][1][1] = f[1][1][0] = 1;
        for (int i = 2; i < N; i++) {
            for (int j = 1; j <= i; j++) {
                for (int k = j; k < i; k++) {
                    f[i][j][0] += f[i - 1][k][1];
                }
                for (int k = 1; k < j; k++) {
                    f[i][j][1] += f[i - 1][k][0];
                }
            }
        }
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        c = in.nextLong();
        boolean[] used = new boolean[N];
        int last = 0, k = 0;
        for (int j = 1; j <= n; j++) {
            if (f[n][j][1] >= c) {
                last = j;
                k = 1;
                break;
            } else {
                c -= f[n][j][1];
            }
            if (f[n][j][0] >= c) {
                last = j;
                k = 0;
                break;
            } else {
                c -= f[n][j][0];
            }
        }
        used[last] = true;
        out.print(last);
        for (int i = 2; i <= n; i++) {
            k ^= 1;
            int rank = 0;
            for (int j = 1; j <= n; j++) {
                if (used[j]) {
                    continue;
                }
                rank++;
                if ((j < last && k == 0) || (j > last && k == 1)) {
                    if (f[n - i + 1][rank][k] >= c) {
                        last = j;
                        used[last] = true;
                        out.print(" " + last);
                        break;
                    } else {
                        c -= f[n - i + 1][rank][k];
                    }
                }
            }
        }
        out.println();
    }
}
