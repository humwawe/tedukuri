package ox5a;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class CatsTransport {
    int N = (int) (1e5 + 5);
    int M = N;
    int P = 110;
    int[] d = new int[N];
    int[] q = new int[N];
    long[] a = new long[N];
    long[] s = new long[N];
    long[][] f = new long[P][N];
    int n, m, p;
    long inf = 0x3f3f3f3f3f3f3f3fL;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        p = in.nextInt();
        for (int i = 2; i <= n; i++) {
            d[i] = in.nextInt();
            d[i] += d[i - 1];
        }
        for (int i = 1; i <= m; i++) {
            int hi = in.nextInt();
            int ti = in.nextInt();
            a[i] = ti - d[hi];
        }
        Arrays.sort(a, 1, m + 1);
        for (int i = 1; i <= m; i++) {
            s[i] = s[i - 1] + a[i];
        }
        for (long[] ele : f) {
            Arrays.fill(ele, inf);
        }
        for (int i = 0; i <= p; i++) {
            f[i][0] = 0;
        }

        for (int i = 1; i <= p; i++) {
            int tt = 0, hh = 0;
            for (int j = 1; j <= m; j++) {
                while (tt > hh && getY(i, q[hh + 1]) - getY(i, q[hh]) <= a[j] * (q[hh + 1] - q[hh])) {
                    hh++;
                }
                int k = q[hh];
                f[i][j] = f[i - 1][k] + (j - k) * a[j] - (s[j] - s[k]);
                while (tt > hh && (getY(i, q[tt]) - getY(i, q[tt - 1])) * (j - q[tt]) >= (getY(i, j) - getY(i, q[tt])) * (q[tt] - q[tt - 1])) {
                    tt--;
                }
                q[++tt] = j;
            }
        }
        out.println(f[p][m]);
    }

    long getY(int i, int j) {
        return f[i - 1][j] + s[j];
    }
}
