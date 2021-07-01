package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class PostOffice {
    int n, p;
    int N = 3010;
    int P = 310;
    int[] x = new int[N];
    int[][] f = new int[N][P];
    int[][] w = new int[N][P];
    int[][] idx = new int[N][P];
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        p = in.nextInt();
        for (int i = 1; i <= n; i++) {
            x[i] = in.nextInt();
        }
        Arrays.sort(x, 1, n + 1);
        for (int i = 0; i <= n; i++) {
            Arrays.fill(f[i], inf);
        }
        f[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            w[i][i] = 0;
            for (int j = i + 1; j <= n; j++) {
                w[i][j] = w[i][j - 1] + x[j] - x[j + i >> 1];
            }
        }

        for (int j = 1; j <= p; j++) {
            idx[n + 1][j] = n;
            for (int i = n; i > 0; i--) {
                int minV = inf;
                int minId = 0;
                for (int k = idx[i][j - 1]; k <= idx[i + 1][j]; k++) {
                    if (f[k][j - 1] + w[k + 1][i] < minV) {
                        minV = f[k][j - 1] + w[k + 1][i];
                        minId = k;
                    }
                }
                f[i][j] = minV;
                idx[i][j] = minId;
            }
        }
        out.println(f[n][p]);
    }
}
