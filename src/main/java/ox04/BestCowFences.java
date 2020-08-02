package ox04;

import common.io.InputReader;
import common.io.OutputWriter;

public class BestCowFences {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int f = in.nextInt();
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        double eps = 1e-5;
        double l = 0;
        double r = 2005;
        double[] sum = new double[n + 1];
        while (l + eps < r) {
            double mid = (l + r) / 2;
            for (int i = 0; i < n; i++) {
                sum[i + 1] = sum[i] + (a[i] - mid);
            }
            double min = 1e10;
            double res = -1e10;
            for (int i = f; i <= n; i++) {
                min = Math.min(min, sum[i - f]);
                res = Math.max(res, sum[i] - min);
            }
            if (res >= 0) {
                l = mid;
            } else {
                r = mid;
            }
        }
        out.println((int) (r * 1000));
    }
}
