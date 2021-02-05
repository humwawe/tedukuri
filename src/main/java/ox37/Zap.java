package ox37;

import common.io.InputReader;
import common.io.OutputWriter;

public class Zap {
    int N = (int) (5e5 + 5);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[] sum = Mobius(N);
        int t = in.nextInt();
        while (t-- > 0) {
            int a = in.nextInt();
            int b = in.nextInt();
            int d = in.nextInt();
            a /= d;
            b /= d;
            int n = Math.min(a, b);
            long res = 0;
            for (int l = 1, r; l <= n; l = r + 1) {
                r = Math.min(n, Math.min(a / (a / l), b / (b / l)));
                res += (long) (sum[r] - sum[l - 1]) * (a / l) * (b / l);
            }
            out.println(res);
        }
    }

    int[] Mobius(int n) {
        int[] mobius = new int[n];
        int[] pri = new int[n];
        boolean[] vis = new boolean[n];
        int cnt = 0;
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
