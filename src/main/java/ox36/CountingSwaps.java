package ox36;

import common.io.InputReader;
import common.io.OutputWriter;

public class CountingSwaps {
    int n;
    int mod = (int) (1e9 + 9);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        combination();
        int t = in.nextInt();
        while (t-- > 0) {
            n = in.nextInt();
            int[] a = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = in.nextInt();
            }
            boolean[] vis = new boolean[n + 1];
            int k = 0;
            long res = 1;
            for (int i = 1; i <= n; i++) {
                if (!vis[i]) {
                    int len = 1;
                    for (int j = a[i]; j != i; j = a[j]) {
                        vis[j] = true;
                        len++;
                    }
                    k++;
                    res = ((res * f[len]) % mod * qp(jc[len - 1], mod - 2, mod)) % mod;
                }
            }
            res = res * jc[n - k] % mod;
            out.println(res);
        }
    }

    int qp(long m, long k, int p) {
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

    int N = (int) (1e5 + 10);
    long[] f = new long[N];
    long[] jc = new long[N];

    void combination() {
        f[0] = f[1] = 1;
        jc[0] = jc[1] = 1;
        for (int i = 2; i < N; i++) {
            jc[i] = jc[i - 1] * i % mod;
            f[i] = qp(i, i - 2, mod) % mod;
        }
    }
}
