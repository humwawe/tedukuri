package ox36;

import common.io.InputReader;
import common.io.OutputWriter;

public class AncientPigScript {
    int n, q;
    int mod = 999911659;
    int[] pri = new int[]{2, 3, 4679, 35617};
    long[] a = new long[4];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        q = in.nextInt();
        if (q % mod == 0) {
            out.println(0);
        } else {
            for (int i = 0; i < 4; i++) {
                int p = pri[i];
                combination2(p);
                for (int j = 1; j <= n / j; j++) {
                    if (n % j == 0) {
                        a[i] = ((a[i] + lucas(n, j, p)) % p);
                        if (j != n / j) {
                            a[i] = (a[i] + lucas(n, n / j, p)) % p;
                        }
                    }
                }
            }
            long x = crt();
            out.println(qmi(q, x, mod));
        }
    }

    private long crt() {
        long res = 0;
        long m = mod - 1;
        for (int i = 0; i < 4; i++) {
            long mi = m / pri[i];
            long t = qmi(mi, pri[i] - 2, pri[i]);
            res = ((res + a[i] * mi % m * t % m) % m);
        }
        return res;
    }

    long lucas(long a, long b, long p) {
        if (a < p && b < p) {
            return comb(a, b, p);
        }
        return comb(a % p, b % p, p) * lucas(a / p, b / p, p) % p;
    }

    int N = (int) 4e4;
    long[] fact = new long[N];
    long[] infact = new long[N];

    long comb(long a, long b, long p) {
        if (a < b) {
            return 0;
        }
        return fact[(int) a] * infact[(int) b] % p * infact[(int) (a - b)] % p;
    }

    // 从a个里中选b个的方案数：fact[a]*infact[b]%mod*infact[a-b]%mod
    void combination2(int p) {
        // 预处理阶乘的余数和阶乘逆元的余数
        fact[0] = infact[0] = 1;
        for (int i = 1; i <= p; i++) {
            fact[i] = fact[i - 1] * i % p;
            infact[i] = infact[i - 1] * qmi(i, p - 2, p) % p;
        }
    }

    long qmi(long m, long k, long p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % p;
            }
            t = t * t % p;
            k >>= 1;
        }
        return res % p;
    }
}

