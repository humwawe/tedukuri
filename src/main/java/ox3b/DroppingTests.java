package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class DroppingTests {
    int n, k;
    double eps = 1e-6;
    int[] a;
    int[] b;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        if (n == 0 && k == 0) {
            return;
        }
        a = in.nextIntArray(n);
        b = in.nextIntArray(n);
        double l = 0;
        double r = 1e9;
        while (r - l > eps) {
            double mid = (l + r) / 2;
            if (check(mid)) {
                l = mid;
            } else {
                r = mid;
            }
        }
        out.println(Math.round(l * 100));
    }

    private boolean check(double x) {
        double[] c = new double[n];
        for (int i = 0; i < n; i++) {
            c[i] = a[i] - x * b[i];
        }
        Arrays.sort(c);
        double res = 0;
        for (int i = k; i < n; i++) {
            res += c[i];
        }
        return res >= 0;
    }
}
