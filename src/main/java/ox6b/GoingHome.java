package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class GoingHome {
    int N = 105;
    int[][] w = new int[N][N];
    int[] la = new int[N];
    int[] lb = new int[N];
    int[] upd = new int[N];
    boolean[] va = new boolean[N];
    boolean[] vb = new boolean[N];
    int inf = 0x3f3f3f3f;
    int n, m;
    int[][] a = new int[N][2];
    int[][] b = new int[N][2];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        Arrays.fill(match, 0);
        int t1 = 0;
        int t2 = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                char t = in.nextCharacter();
                if (t == 'm') {
                    a[++t1][0] = i;
                    a[t1][1] = j;
                } else if (t == 'H') {
                    b[++t2][0] = i;
                    b[t2][1] = j;
                }
            }
        }
        n = t1;
        for (int i = 1; i <= t1; i++) {
            for (int j = 1; j <= t1; j++) {
                w[i][j] = -(Math.abs(a[i][0] - b[j][0]) + Math.abs(a[i][1] - b[j][1]));
            }
        }
        int res = km2();
        out.println(-res);
    }

    int[] last = new int[N];
    int[] match = new int[N];

    int km2() {
        for (int i = 1; i <= n; i++) {
            la[i] = -inf;
            lb[i] = 0;
            for (int j = 1; j <= n; j++) {
                la[i] = Math.max(la[i], w[i][j]);
            }
        }
        for (int i = 1; i <= n; i++) {
            Arrays.fill(va, false);
            Arrays.fill(vb, false);
            Arrays.fill(upd, inf);
            int start = 0;
            match[0] = i;
            while (match[start] != 0) {
                if (dfs2(match[start], start)) {
                    break;
                }
                int delta = inf;
                for (int j = 1; j <= n; j++) {
                    if (!vb[j] && delta > upd[j]) {
                        delta = upd[j];
                        start = j;
                    }
                }
                // 修改顶标
                for (int j = 1; j <= n; j++) {
                    if (va[j]) {
                        la[j] -= delta;
                    }
                    if (vb[j]) {
                        lb[j] += delta;
                    } else {
                        upd[j] -= delta;
                    }
                }
                vb[start] = true;
            }
            while (start != 0) {
                match[start] = match[last[start]];
                start = last[start];
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += w[match[i]][i];
        }
        return res;
    }

    boolean dfs2(int x, int fa) {
        va[x] = true;
        for (int y = 1; y <= n; y++) {
            if (!vb[y]) {
                // 相等子图
                if (la[x] + lb[y] - w[x][y] == 0) {
                    vb[y] = true;
                    last[y] = fa;
                    if (match[y] == 0 || dfs2(match[y], y)) {
                        match[y] = x;
                        return true;
                    }
                } else {
                    if (upd[y] > la[x] + lb[y] - w[x][y]) {
                        upd[y] = la[x] + lb[y] - w[x][y];
                        last[y] = fa;
                    }
                }
            }
        }
        return false;
    }
}
