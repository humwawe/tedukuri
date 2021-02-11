package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class Gcd {
    int n;
    int[] pri;
    int cnt = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        pri = new int[n];
        int[] sum = mobius(n + 1);
        long res = 0;
        for (int i = 0; i < cnt; i++) {
            int a = n / pri[i];
            for (int l = 1, r; l <= a; l = r + 1) {
                r = a / (a / l);
                res += (long) (sum[r] - sum[l - 1]) * (a / l) * (a / l);
            }
        }
        out.println(res);

    }

    int[] mobius(int n) {
        int[] mobius = new int[n];
        boolean[] vis = new boolean[n];

        mobius[1] = 1;
        for (int i = 2; i < n; ++i) {
            if (!vis[i]) {
                pri[cnt++] = i;
                mobius[i] = -1;
            }
            for (int j = 0; pri[j] * i < n; ++j) {
                int t = pri[j] * i;
                vis[t] = true;
                if (i % pri[j] == 0) {
                    mobius[t] = 0;
                    break;
                }
                mobius[t] = mobius[i] * -1;
            }
        }
        int[] sum = new int[n];
        for (int i = 1; i < n; ++i) {
            sum[i] = sum[i - 1] + mobius[i];
        }
        return sum;
    }
}
