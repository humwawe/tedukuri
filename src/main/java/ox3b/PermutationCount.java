package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class PermutationCount {
    int N = 1000005;
    int mod = (int) (1e9 + 7);
    long[] d = new long[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        d[0] = 1;
        d[1] = 0;
        d[2] = 1;
        for (int i = 3; i < N; ++i) {
            d[i] = ((i - 1) * ((d[i - 1] + d[i - 2]) % mod)) % mod;
        }
        combination();
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            int m = in.nextInt();
            out.println(comb(n, m) * d[n - m] % mod);
        }

    }

    long[] fact = new long[N];
    long[] infact = new long[N];

    long comb(int a, int b) {
        return fact[a] * infact[b] % mod * infact[a - b] % mod;
//        return fact[a] * qmi(fact[b], mod - 2, mod) % mod * qmi(fact[a - b], mod - 2, mod) % mod;
    }

    void combination() {
        fact[0] = infact[0] = 1;
        for (int i = 1; i < N; i++) {
            fact[i] = fact[i - 1] * i % mod;
            infact[i] = infact[i - 1] * qmi(i, mod - 2, mod) % mod;
        }
    }

    int qmi(long m, int k, int p) {
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
