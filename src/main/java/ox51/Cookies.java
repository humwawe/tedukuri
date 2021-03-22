package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Cookies {
    int N = 31, M = 5010;
    int[][] g = new int[N][2];
    int[] sum = new int[N];
    int[][] f = new int[N][M];
    int[] res = new int[N];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            g[i][0] = in.nextInt();
            g[i][1] = i;
        }
        Arrays.sort(g, 1, n + 1, (x, y) -> y[0] - x[0]);
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + g[i][0];
        }

        for (int i = 0; i < N; i++) {
            Arrays.fill(f[i], 0x3f3f3f3f);
        }
        f[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (j < i) {
                    continue;
                }
                f[i][j] = f[i][j - i];
                for (int k = 1; k <= i; k++) {
                    f[i][j] = Math.min(f[i][j], f[i - k][j - k] + (i - k) * (sum[i] - sum[i - k]));
                }
            }
        }
        out.println(f[n][m]);

        int i = n;
        int j = m;
        int h = 0;
        while (i != 0) {
            if (f[i][j] == f[i][j - i]) {
                h++;
                j -= i;
            } else {
                for (int k = 1; k <= i; k++) {
                    if (f[i][j] == f[i - k][j - k] + (i - k) * (sum[i] - sum[i - k])) {
                        for (int l = i - k + 1; l <= i; l++) {
                            res[g[l][1]] = 1 + h;
                        }
                        i -= k;
                        j -= k;
                        break;
                    }
                }
            }
        }
        for (int k = 1; k <= n; k++) {
            out.print(res[k] + " ");
        }
    }
}
