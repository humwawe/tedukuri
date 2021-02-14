package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class SkyCode {
    int N = (int) (1e4 + 10);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[] mobius = mobius(N);
        while (true) {
            int[] sum = new int[N];
            int max = 0;
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                int x = in.nextInt();
                max = Math.max(max, x);
                for (int j = 1; j * j <= x; j++) {
                    if (x % j == 0) {
                        sum[j]++;
                        if (j * j != x) {
                            sum[x / j]++;
                        }
                    }
                }
            }
            long res = 0;
            for (int i = 1; i <= max; i++) {
                res += mobius[i] * comb(sum[i]);
            }
            out.println(res);
        }
    }

    private long comb(int x) {
        return (long) x * (x - 1) * (x - 2) * (x - 3) / 24;
    }

    int[] mobius(int n) {
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
        return mobius;
    }
}
