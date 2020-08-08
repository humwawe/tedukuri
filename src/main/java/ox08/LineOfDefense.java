package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

public class LineOfDefense {
    int[][] a;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = new int[n][3];
        for (int i = 0; i < n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
            a[i][2] = in.nextInt();
        }
        long l = 0;
        long r = Integer.MAX_VALUE;
        int cnt = 0;
        while (l < r) {
            long mid = l + r >> 1;
            cnt = check(mid);
            if (cnt % 2 == 1) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        cnt = check(l) - check(l - 1);
        if (cnt % 2 == 0) {
            out.println("There's no weakness.");
        } else {
            out.println(l + " " + cnt);
        }

    }

    private int check(long x) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (a[i][0] <= x) {
                res += (Math.min(x, a[i][1]) - a[i][0]) / a[i][2] + 1;
            }
        }
        return res;
    }
}
