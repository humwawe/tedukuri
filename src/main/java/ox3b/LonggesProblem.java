package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class LonggesProblem {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        long res = 0;
        for (int i = 1; (long) i * i <= n; i++) {
            if (n % i == 0) {
                res += (n / i) * phi(i);
                if (i * i != n) {
                    res += i * phi(n / i);
                }
            }
        }
        out.println(res);
    }

    int phi(int x) {
        int res = x;
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
}
