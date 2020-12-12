package ox23;

import common.io.InputReader;
import common.io.OutputWriter;

public class BirthdayCake {
    int M = 20;
    int[] R = new int[M + 5];
    int[] H = new int[M + 5];
    int inf = (int) 1e9;
    int res = inf;
    int[] minV = new int[M + 5];
    int[] minS = new int[M + 5];
    int n;
    int m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();

        for (int i = 1; i <= m; i++) {
            int minDepR = i;
            int minDepS = i;
            minV[i] = minV[i - 1] + minDepR * minDepR * minDepS;
            minS[i] = minS[i - 1] + 2 * minDepR * minDepS;
        }

        R[m + 1] = inf;
        H[m + 1] = inf;
        dfs(m, 0, 0);
        if (res == inf) {
            res = 0;
        }
        out.println(res);

    }

    private void dfs(int dep, int v, int s) {
        if (v + minV[dep] > n) {
            return;
        }
        if (s + minS[dep] >= res) {
            return;
        }
        if (s + 2 * (n - v) / R[dep + 1] >= res) {
            return;
        }
        if (dep == 0) {
            if (n == v) {
                res = s;
            }
            return;
        }
        for (int r = Math.min(R[dep + 1] - 1, (int) Math.sqrt(n - v)); r >= dep; r--) {
            for (int h = Math.min(H[dep + 1] - 1, (n - v) / (r * r)); h >= dep; h--) {
                int t = 0;
                if (dep == m) {
                    t = r * r;
                }
                R[dep] = r;
                H[dep] = h;
                dfs(dep - 1, v + r * r * h, s + 2 * r * h + t);
            }
        }
    }
}
