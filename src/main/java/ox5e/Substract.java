package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class Substract {
    int N = 110;
    int M = 10010;
    int flag = 10000;
    int[][] f = new int[N][M * 2];
    int[] a = new int[N];
    int[] ans = new int[N];
    int n, t;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        t = in.nextInt();
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        f[1][a[1] + flag] = 1;
        f[2][a[1] - a[2] + flag] = -1;
        for (int i = 3; i <= n; i++) {
            for (int j = -10000 + flag; j <= 10000 + flag; ++j) {
                if (f[i - 1][j] != 0) {
                    f[i][j + a[i]] = 1;
                    f[i][j - a[i]] = -1;
                }
            }
        }
        int v = t + flag;
        int i = n;
        while (i > 1) {
            ans[i] = f[i][v];
            if (ans[i] == 1) {
                v -= a[i];
            } else {
                v += a[i];
            }
            i--;
        }
        int cnt = 0;
        for (int j = 2; j <= n; j++) {
            if (ans[j] == 1) {
                out.println(j - cnt - 1 + " ");
                cnt++;
            }
        }
        for (int j = 2; j <= n; ++j) {
            if (ans[j] == -1) {
                out.println("1 ");
            }
        }
    }
}
