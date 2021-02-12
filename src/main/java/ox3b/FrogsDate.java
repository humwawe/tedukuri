package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class FrogsDate {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int x = in.nextInt();
        int y = in.nextInt();
        int m = in.nextInt();
        int n = in.nextInt();
        int l = in.nextInt();
        int d = exgcd(m - n, l);
        int c = y - x;
        if (c % d != 0) {
            out.println("Impossible");
        } else {
            t1 = t1 * (c / d);
            int t = Math.abs(l / d);
            out.println((t1 % t + t) % t);
        }
    }

    long t1, t2;

    int exgcd(int a, int b) {
        if (b == 0) {
            t1 = 1;
            t2 = 0;
            return a;
        }
        int d = exgcd(b, a % b);
        long tmp = t1;
        t1 = t2;
        t2 = tmp - a / b * t2;
        return d;
    }
}
