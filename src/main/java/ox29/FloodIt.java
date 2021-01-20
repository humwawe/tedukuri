package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class FloodIt {
    int n;
    int[][] a;
    int[][] vis;
    int depth;
    int[][] fs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        a = new int[n][n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextIntArray(n);
        }
        vis = new int[n][n];
        depth = 0;
        vis[0][0] = 1;
        dfs(a[0][0], 0, 0);
        while (!idaStar(a[0][0], 0)) {
            depth++;
        }
        out.println(depth);
    }

    private void dfs(int c, int x, int y) {
        vis[x][y] = 1;
        for (int[] f : fs) {
            int nx = x + f[0];
            int ny = y + f[1];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                if (vis[nx][ny] != 0) {
                    continue;
                }
                vis[nx][ny] = 2;
                if (a[nx][ny] == c) {
                    dfs(c, nx, ny);
                }
            }
        }
    }

    private boolean idaStar(int c, int dep) {
        int cnt = f();
        if (cnt == 0) {
            return true;
        }
        if (dep + cnt > depth) {
            return false;
        }
        int[][] tmp = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(vis[i], 0, tmp[i], 0, n);
        }
        for (int i = 0; i < 6; i++) {
            if (i == c) {
                continue;
            }
            if (fill(i) && idaStar(i, dep + 1)) {
                return true;
            }
            for (int j = 0; j < n; j++) {
                System.arraycopy(tmp[j], 0, vis[j], 0, n);
            }
        }
        return false;

    }

    private boolean fill(int c) {
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == c && vis[i][j] == 2) {
                    vis[i][j] = 1;
                    dfs(c, i, j);
                    flag = true;
                }
            }
        }
        return flag;
    }

    private int f() {
        boolean[] v = new boolean[6];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (vis[i][j] != 1) {
                    v[a[i][j]] = true;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < 6; i++) {
            if (v[i]) {
                res++;
            }
        }
        return res;
    }


}
