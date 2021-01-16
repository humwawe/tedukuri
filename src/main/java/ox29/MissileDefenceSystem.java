package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class MissileDefenceSystem {
    int N = 60;
    int n;
    int[] a;
    int[] up = new int[N];
    int[] down = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        a = in.nextIntArray(n);
        int depth = 0;
        while (!dfs(depth, 0, 0, 0)) {
            depth++;
        }
        out.println(depth);
    }

    private boolean dfs(int depth, int u, int cntU, int cntD) {
        if (cntU + cntD > depth) {
            return false;
        }
        if (u == n) {
            return true;
        }
        boolean f = false;
        for (int i = 0; i < cntU; i++) {
            if (up[i] < a[u]) {
                int t = up[i];
                up[i] = a[u];
                if (dfs(depth, u + 1, cntU, cntD)) {
                    return true;
                }
                up[i] = t;
                f = true;
                break;
            }
        }
        if (!f) {
            up[cntU] = a[u];
            if (dfs(depth, u + 1, cntU + 1, cntD)) {
                return true;
            }
            up[cntU] = 0;
        }
        f = false;
        for (int i = 0; i < cntD; i++) {
            if (down[i] > a[u]) {
                int t = down[i];
                down[i] = a[u];
                if (dfs(depth, u + 1, cntU, cntD)) {
                    return true;
                }
                down[i] = t;
                f = true;
                break;
            }
        }
        if (!f) {
            down[cntD] = a[u];
            if (dfs(depth, u + 1, cntU, cntD + 1)) {
                return true;
            }
            down[cntD] = 0;
        }
        return false;
    }
}
