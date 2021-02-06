package ox38;

import common.io.InputReader;
import common.io.OutputWriter;

public class RainbowSignal {
    int[] a;
    double xor = 0, and = 0, or = 0;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = in.nextIntArray(n);
        for (int i = 0; i < 30; i++) {
            helper(i);
        }
        out.printf("%.3f %.3f %.3f", xor, and, or);
    }

    private void helper(int k) {
        int c1 = 0;
        int c2 = 0;
        int last0 = -1;
        int last1 = -1;
        double p = (double) (1 << k) / n / n;
        for (int i = 0; i < n; i++) {
            int v = ((a[i] >> k) & 1);
            if (v == 1) {
                xor += p;
                and += p;
                or += p;
                xor += p * c1 * 2;
                and += p * (i - 1 - last0) * 2;
                or += p * i * 2;
                last1 = i;
            } else {
                xor += p * c2 * 2;
                or += p * (last1 + 1) * 2;
                last0 = i;
            }
            ++c1;
            if (v == 1) {
                int t = c1;
                c1 = c2;
                c2 = t;
            }
        }
    }
}
