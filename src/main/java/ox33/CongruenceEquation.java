package ox33;

import common.io.InputReader;
import common.io.OutputWriter;

public class CongruenceEquation {
    long x, y, a, b;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextLong();
        b = in.nextLong();
        exgcd(a, b);
        out.println((x % b + b) % b);
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
