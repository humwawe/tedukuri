package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class AJiuVsZhuZuiXue {
    int[] m = new int[20];
    int[] a = new int[20];
    long M = 1;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            m[i] = in.nextInt();
            M *= m[i];
            a[i] = in.nextInt();
        }
        out.println(crt());
    }

    private long crt() {
        long res = 0;
        for (int i = 0; i < n; i++) {
            long mi = M / m[i];
            exgcd(mi, m[i]);
            res = (res + a[i] * mi % M * t1) % M;
        }
        return (res + M) % M;
    }

    long t1, t2;

    long exgcd(long a, long b) {
        if (b == 0) {
            t1 = 1;
            t2 = 0;
            return a;
        }
        long d = exgcd(b, a % b);
        long tmp = t1;
        t1 = t2;
        t2 = tmp - a / b * t2;
        return d;
    }
}
