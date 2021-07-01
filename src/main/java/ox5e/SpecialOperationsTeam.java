package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class SpecialOperationsTeam {
    int n;
    int a, b, c;
    int N = (int) (1e6 + 5);
    int[] w = new int[N];
    long[] sum = new long[N];
    int[] q = new int[N];
    long[] f = new long[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();

        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
            sum[i] = sum[i - 1] + w[i];
        }
        int l = 1;
        int r = 1;
        q[1] = 0;

        for (int i = 1; i <= n; i++) {
            while (l < r && y(q[l + 1]) - y(q[l]) >= 2 * a * sum[i] * (x(q[l + 1]) - x(q[l]))) {
                l++;
            }
            int j = q[l];
            f[i] = f[j] + a * sum[j] * sum[j] - b * sum[j] + a * sum[i] * sum[i] + b * sum[i] + c - 2 * a * sum[i] * sum[j];

            while (l < r && (y(q[r]) - y(q[r - 1])) * (x(i) - x(q[r])) <= (y(i) - y(q[r])) * (x(q[r]) - x(q[r - 1]))) {
                r--;
            }
            q[++r] = i;
        }
        out.println(f[n]);
    }

    long x(int num) {
        return sum[num];
    }

    long y(int num) {
        return f[num] + a * sum[num] * sum[num] - b * sum[num];
    }
}
