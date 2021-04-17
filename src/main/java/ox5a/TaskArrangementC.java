package ox5a;

import common.io.InputReader;
import common.io.OutputWriter;

public class TaskArrangementC {
    int N = (int) (3e5 + 10);
    long[] t = new long[N];
    long[] c = new long[N];
    long[] f = new long[N];
    int n, s;
    int[] q = new int[N];
    int hh = 0;
    int tt = -1;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int s = in.nextInt();
        for (int i = 1; i <= n; i++) {
            t[i] = in.nextLong();
            c[i] = in.nextLong();
            t[i] += t[i - 1];
            c[i] += c[i - 1];
        }
        q[++tt] = 0;
        for (int i = 1; i <= n; i++) {
            int idx = binarySearch(s + t[i]);
            f[i] = f[idx] + t[i] * c[i] + s * c[n] - c[idx] * (t[i] + s);
            while (hh < tt && (double) (f[q[tt]] - f[q[tt - 1]]) * (c[i] - c[q[tt - 1]]) >= (double) (f[i] - f[q[tt - 1]]) * (c[q[tt]] - c[q[tt - 1]])) {
                tt--;
            }
            q[++tt] = i;
        }
        out.println(f[n]);
    }

    private int binarySearch(long tar) {
        int l = hh;
        int r = tt;
        while (l < r) {
            int mid = l + r >> 1;
            if (f[q[mid + 1]] - f[q[mid]] <= tar * (c[q[mid + 1]] - c[q[mid]])) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return q[l];
    }

}
