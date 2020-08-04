package ox07;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class RadarInstallation {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int d = in.nextInt();
        double[][] a = new double[n][2];
        boolean f = false;
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            double dist = Math.sqrt(d * d - y * y);
            if (d * d - y * y < 0) {
                f = true;
            }
            a[i][0] = x - dist;
            a[i][1] = x + dist;

        }
        if (f) {
            out.println(-1);
            return;
        }
        Arrays.sort(a, Comparator.comparingDouble(x -> x[0]));
        double pos = -Double.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (a[i][0] > pos) {
                res++;
                pos = a[i][1];
            } else {
                pos = Math.min(pos, a[i][1]);
            }
        }
        out.println(res);
    }
}
