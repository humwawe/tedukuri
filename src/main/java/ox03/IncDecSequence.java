package ox03;

import common.io.InputReader;
import common.io.OutputWriter;

public class IncDecSequence {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        long p = 0;
        long q = 0;
        for (int i = 1; i < n; i++) {
            int x = a[i] - a[i - 1];
            if (x > 0) {
                p += x;
            } else if (x < 0) {
                q += -x;
            }
        }
        out.println(Math.max(p, q));
        out.println(Math.abs(p - q) + 1);
    }
}
