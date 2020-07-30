package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

public class FractalStreets {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        long a = in.nextLong();
        long b = in.nextLong();
        long[] pos1 = helper(n, a - 1);
        long[] pos2 = helper(n, b - 1);
        double x = pos1[0] - pos2[0];
        double y = pos1[1] - pos2[1];
        out.println(String.format("%.0f", Math.sqrt(x * x + y * y) * 10));
    }

    private long[] helper(int n, long m) {
        if (n == 0) {
            return new long[]{0, 0};
        }
        long len = 1L << (n - 1);
        long cnt = 1L << (n * 2 - 2);
        long[] pos = helper(n - 1, m % cnt);
        long x = pos[0];
        long y = pos[1];
        long z = m / cnt;//处在城区的哪个角
        if (z == 0) {
            return new long[]{y, x};
        } else if (z == 1) {
            return new long[]{x, y + len};
        } else if (z == 2) {
            return new long[]{x + len, y + len};
        } else {//if (z == 3)
            return new long[]{2 * len - y - 1, len - x - 1};
        }
    }
}
