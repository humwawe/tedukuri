package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class KAnonymousSequence {
    int n, k;
    int N = (int) (5e5 + 5);
    long[] a = new long[N];
    long[] sum = new long[N];
    long[] f = new long[N];
    int[] q = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();

        for (int i = 1; i <= n; i++) {
            a[i] = in.nextLong();
            sum[i] = sum[i - 1] + a[i];
        }
        int l = 1;
        int r = 1;
        q[1] = k;

        for (int i = 1; i <= n; i++) {
            if (i < k * 2) {
                f[i] = sum[i] - i * a[1];
                continue;
            }
            while (l < r && y(q[l + 1]) - y(q[l]) <= i * (x(q[l + 1]) - x(q[l]))) {
                l++;
            }
            int j = q[l];
            f[i] = f[j] + sum[i] - sum[j] - a[j + 1] * (i - j);
            int now = i - k + 1;
            while (l < r && (x(now) - x(q[r])) * (y(q[r]) - y(q[r - 1])) >= (x(q[r]) - x(q[r - 1])) * (y(now) - y(q[r]))) {
                r--;
            }
            q[++r] = now;
        }
        out.println(f[n]);
    }

    private long y(int i) {
        return f[i] + a[i + 1] * i - sum[i];
    }

    private long x(int i) {
        return a[i + 1];
    }
}
