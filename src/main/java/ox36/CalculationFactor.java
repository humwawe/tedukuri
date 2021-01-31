package ox36;

import common.io.InputReader;
import common.io.OutputWriter;

public class CalculationFactor {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int k = in.nextInt();
        int n = in.nextInt();
        int m = in.nextInt();
        int mod = 10007;
        int res = qp(a, n, mod) * qp(b, m, mod) % mod;
        for (int i = 1, j = k; i <= n; i++, j--) {
            res = res * j % mod;
            res = res * qp(i, mod - 2, mod) % mod;
        }
        out.println(res);
    }

    int qp(int m, int k, int p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % p;
            }
            t = t * t % p;
            k >>= 1;
        }
        return (int) res % p;
    }
}
