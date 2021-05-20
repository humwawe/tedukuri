package ox5c;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class GeraldAndGiantChess {
    int N = 2005;
    int h, w, n;
    int mod = (int) (1e9 + 7);
    int[][] a = new int[N][2];
    int[] f = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        h = in.nextInt();
        w = in.nextInt();
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        Arrays.sort(a, (x, y) -> {
            if (x[1] == y[1]) {
                return x[0] - y[0];
            }
            return x[1] - y[1];
        });
        a[n][0] = h;
        a[n][1] = w;

        for (int i = 0; i <= n; i++) {
            f[i] = c(a[i][0] - a[i][1] - 2, a[i][0] - 1);
            for (int j = 0; j < i; j++) {
                if (a[j][0] > a[i][0] || a[j][1] > a[j][1]) {
                    continue;
                }
                f[i] = (int) (f[i] - (long) f[j] * c(a[i][0] + a[i][1] - a[j][0] - a[j][1], a[i][0] - a[j][0]) % mod);
            }
        }
        out.println((f[n] + mod) % mod);
    }

    int c(int a, int b) {
        return (int) (fact[a] * infact[b] % mod * infact[a - b] % mod);
    }

    long[] fact = new long[N];
    long[] infact = new long[N];

    void fact() {
        fact[0] = infact[0] = 1;
        for (int i = 1; i < N; i++) {
            fact[i] = fact[i - 1] * i % mod;
            infact[i] = infact[i - 1] * qmi(i, mod - 2, mod) % mod;
        }
    }

    long qmi(int m, int k, int p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % p;
            }
            t = t * t % p;
            k >>= 1;
        }
        return res;
    }
}
