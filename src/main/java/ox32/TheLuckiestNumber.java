package ox32;

import common.io.InputReader;
import common.io.OutputWriter;

public class TheLuckiestNumber {
    long n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextLong();
        if (n == 0) {
            return;
        }
        long p = n / gcd(8, n) * 9;
        long res = Long.MAX_VALUE;
        if (gcd(10, p) == 1) {
            long e = phi(p);
            for (long i = 1; i * i <= e; i++) {
                if (e % i != 0) {
                    continue;
                }
                if (qp(10, i, p) == 1) {
                    res = Math.min(res, i);
                }
                if (qp(10, e / i, p) == 1) {
                    res = Math.min(res, e / i);
                }
            }
        }
        out.printf("Case %d: %d\n", testNumber++, res == Long.MAX_VALUE ? 0 : res);
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    long phi(long x) {
        long res = x;
        for (int i = 2; i <= x / i; i++) {
            if (x % i == 0) {
                res = res / i * (i - 1);
                while (x % i == 0) {
                    x /= i;
                }
            }
        }
        if (x > 1) {
            res = res / x * (x - 1);
        }
        return res;
    }

    long qp(long m, long k, long p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = qm(res, t, p);
            }
            t = qm(t, t, p);
            k >>= 1;
        }
        return (int) res % p;
    }

    long qm(long a, long b, long p) {
        long ans = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans + a) % p;
            }
            a = (a << 1) % p;
            b = b >> 1;
        }
        return ans;
    }
}
