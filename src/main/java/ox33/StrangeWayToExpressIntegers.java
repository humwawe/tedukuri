package ox33;

import common.io.InputReader;
import common.io.OutputWriter;

public class StrangeWayToExpressIntegers {
    long x, y;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        long a1 = in.nextLong();
        long m1 = in.nextLong();
        for (int i = 1; i < n; i++) {
            long a2 = in.nextLong();
            long m2 = in.nextLong();
            long d = exgcd(a1, -a2);
            if ((m2 - m1) % d != 0) {
                out.println("-1");
                return;
            }
            x = mod(x * (m2 - m1) / d, Math.abs(a2 / d));
            m1 = x * a1 + m1;
            a1 = Math.abs(a1 / d * a2);
        }
        out.println(m1);
    }

    long mod(long a, long b) {
        return ((a % b) + b) % b;
    }

    long exgcd(long a, long b) {
        if (b == 0) {
            x = 1;
            y = 0;
            return a;
        }
        long d = exgcd(b, a % b);
        long tmp = x;
        x = y;
        y = tmp - a / b * y;
        return d;
    }
}
