package ox37;

import common.io.InputReader;
import common.io.OutputWriter;

public class DevuAndFlowers {
    int N = 25;
    long[] infact = new long[N];
    int mod = (int) (1e9 + 7);

    void init() {
        infact[0] = 1;
        for (int i = 1; i < N; i++) {
            infact[i] = infact[i - 1] * qmi(i, mod - 2, mod) % mod;
        }
    }

    long comb(long a, int b) {
        if (a < 0 || b < 0 || a < b) {
            return 0;
        }
        a %= mod;
        if (a == 0 || b == 0) {
            return 1;
        }
        long res = 1;
        for (int i = 1, j = (int) a; i <= b; i++, j--) {
            res = res * j % mod;
        }
        res = res * infact[b] % mod;
        return res;
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        init();
        int n = in.nextInt();
        long m = in.nextLong();
        long[] a = in.nextLongArray(n);
        long res = 0;
        for (int x = 0; x < 1 << n; x++) {
            if (x == 0) {
                res = (res + comb(n + m - 1, n - 1)) % mod;
            } else {
                long t = n + m;
                int p = 0;
                for (int i = 0; i < n; i++) {
                    if ((x >> i & 1) == 1) {
                        p++;
                        t -= a[i];
                    }
                }
                t -= p + 1;
                if ((p & 1) == 1) {
                    res = (res - comb(t, n - 1)) % mod;
                } else {
                    res = (res + comb(t, n - 1)) % mod;
                }
            }
        }
        out.println((res + mod) % mod);
    }

    int qmi(int m, int k, int p) {
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
