package ox32;

import common.io.InputReader;
import common.io.OutputWriter;

public class SumOfRemainders {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int r = 0;
        long res = (long) n * k;
        for (int l = 1; l <= n; l = r + 1) {
            if (l > k) {
                break;
            }
            r = Math.min(k / (k / l), n);
            res -= (long) (k / l) * (l + r) * (r - l + 1) / 2;
        }
        out.println(res);
    }
}
