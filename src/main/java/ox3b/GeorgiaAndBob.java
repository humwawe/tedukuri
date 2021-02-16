package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class GeorgiaAndBob {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] a = in.nextIntArray(n);
        Arrays.sort(a);
        int res = 0;
        for (int i = n - 1; i >= 0; i -= 2) {
            if (i == 0) {
                res ^= a[0] - 1;
            } else {
                res ^= a[i] - a[i - 1] - 1;
            }
        }
        if (res == 0) {
            out.println("Bob will win");
        } else {
            out.println("Georgia will win");
        }
    }
}
