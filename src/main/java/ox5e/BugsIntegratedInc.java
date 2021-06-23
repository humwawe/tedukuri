package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class BugsIntegratedInc {
    int n, m;
    int[][] f = new int[2][60000];
    boolean[][] a;
    int[] p = new int[12];

    {
        p[0] = 1;
        for (int i = 1; i < 12; i++) {
            p[i] = p[i - 1] * 3;
        }
    }

    int res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = new boolean[n + 1][m];
        int k = in.nextInt();
        Arrays.fill(f[0], -1);
        Arrays.fill(f[1], -1);
        res = 0;
        for (int i = 0; i < k; i++) {
            int x = in.nextInt();
            int y = in.nextInt() - 1;
            a[x][y] = true;
        }
        f[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < p[m]; j++) {
                if (f[(i - 1) & 1][j] >= 0) {
                    dfs(i, j, 0, 0, 0);
                    // 滚动
                    f[(i - 1) & 1][j] = -1;
                }
            }
        }
        out.println(res);
    }

    private void dfs(int i, int state, int pos, int now, int num) {
        if (pos == m) {
            f[i & 1][now] = Math.max(f[i & 1][now], f[(i - 1) & 1][state] + num);
            res = Math.max(res, f[i & 1][now]);
            return;
        }
        int curPosNum = (state / p[pos]) % 3;
        if (curPosNum >= 1) {
            dfs(i, state, pos + 1, now + p[pos] * (curPosNum - 1), num);
            return;
        }
        // (i, pos) (i, pos + 1) (i + 1, pos) (i + 1, pos + 1) 四格
        if (i < n && pos < m - 1 && !a[i][pos] && !a[i][pos + 1] && !a[i + 1][pos] && !a[i + 1][pos + 1] && (state / p[pos + 1]) % 3 == 0) {
            if (pos < m - 2 && !a[i][pos + 2] && !a[i + 1][pos + 2] && (state / p[pos + 2]) % 3 == 0 && !a[i + 1][pos + 2]) {
                dfs(i, state, pos + 3, now + p[pos] + p[pos + 1] + p[pos + 2], num + 1);
            }
            if (i < n - 1 && !a[i + 2][pos] && !a[i + 2][pos + 1]) {
                dfs(i, state, pos + 2, now + 2 * p[pos] + 2 * p[pos + 1], num + 1);
            }
        }
        dfs(i, state, pos + 1, now, num);
    }
}
