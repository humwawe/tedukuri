package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;


public class SteadyCowAssignment {
    int N = 1005;
    int M = 30;
    int[][] a = new int[N][M];
    int[] v = new int[M];
    int[] cnt = new int[M];
    int[][] match = new int[M][N];
    int n, m;
    int st, ed;
    boolean[] vis = new boolean[M];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                a[i][j] = in.nextInt();
            }
        }
        for (int i = 1; i <= m; i++) {
            v[i] = in.nextInt();
        }
        int l = 0;
        int r = m;
        while (l < r) {
            int mid = l + r >> 1;
            if (check(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        out.println(l);
    }

    private boolean check(int mid) {
        for (st = 1; st <= m; st++) {
            ed = st + mid - 1;
            if (ed > m) {
                break;
            }
            if (match()) {
                return true;
            }
        }
        return false;
    }

    private boolean match() {
        for (int i = 1; i <= m; i++) {
            Arrays.fill(match[i], 0);
        }
        Arrays.fill(cnt, 0);
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            if (!find(i)) {
                return false;
            }
        }
        return true;
    }

    boolean find(int x) {
        for (int i = st; i <= ed; i++) {
            int j = a[x][i];
            if (!vis[j]) {
                vis[j] = true;
                if (cnt[j] < v[j]) {
                    match[j][++cnt[j]] = x;
                    return true;
                }
                for (int k = 1; k <= v[j]; k++) {
                    if (find(match[j][k])) {
                        match[j][k] = x;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
