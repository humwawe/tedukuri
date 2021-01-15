package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class MayanGame {
    int n;
    int[][] g = new int[5][7];
    // 对每一层进行备份
    int[][][] bg = new int[5][5][7];
    int[] cnt = new int[11];
    int[][] bCnt = new int[5][11];
    boolean[][] st = new boolean[5][7];
    int[][] path = new int[5][3];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < 5; i++) {
            int j = 0;
            while (true) {
                int t = in.nextInt();
                if (t == 0) {
                    break;
                }
                cnt[0]++;
                cnt[t]++;
                g[i][j++] = t;
            }
        }
        if (dfs(0)) {
            for (int i = 0; i < n; i++) {
                out.println(path[i][0] + " " + path[i][1] + " " + path[i][2]);
            }
        } else {
            out.println("-1");
        }
    }

    private boolean dfs(int u) {
        if (u == n) {
            return cnt[0] == 0;
        }
        for (int i = 0; i <= 10; i++) {
            if (cnt[i] == 1 || cnt[i] == 2) {
                return false;
            }
        }
        for (int i = 0; i < 5; i++) {
            System.arraycopy(g[i], 0, bg[u][i], 0, 7);
        }
        System.arraycopy(cnt, 0, bCnt[u], 0, 11);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (g[i][j] != 0) {
                    int x = i + 1;
                    if (x < 5) {
                        path[u][0] = i;
                        path[u][1] = j;
                        path[u][2] = 1;
                        move(i, j, x);
                        if (dfs(u + 1)) {
                            return true;
                        }
                        for (int k = 0; k < 5; k++) {
                            System.arraycopy(bg[u][k], 0, g[k], 0, 7);
                        }
                        System.arraycopy(bCnt[u], 0, cnt, 0, 11);
                    }
                    x = i - 1;
                    if (x >= 0 && g[x][j] == 0) {
                        path[u][0] = i;
                        path[u][1] = j;
                        path[u][2] = -1;
                        move(i, j, x);
                        if (dfs(u + 1)) {
                            return true;
                        }
                        for (int k = 0; k < 5; k++) {
                            System.arraycopy(bg[u][k], 0, g[k], 0, 7);
                        }
                        System.arraycopy(bCnt[u], 0, cnt, 0, 11);
                    }
                }
            }
        }
        return false;
    }

    void move(int a, int b, int c) {
        int tmp = g[a][b];
        g[a][b] = g[c][b];
        g[c][b] = tmp;
        while (true) {
            boolean f = true;
            for (int i = 0; i < 5; i++) {
                int z = 0;
                for (int j = 0; j < 7; j++) {
                    if (g[i][j] != 0) {
                        g[i][z++] = g[i][j];
                    }
                }
                while (z < 7) {
                    g[i][z++] = 0;
                }
            }

            st = new boolean[5][7];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    if (g[i][j] != 0) {
                        int l = i;
                        int r = i;
                        while (l - 1 >= 0 && g[l - 1][j] == g[i][j]) {
                            l--;
                        }
                        while (r + 1 < 5 && g[r + 1][j] == g[i][j]) {
                            r++;
                        }
                        if (r - l + 1 >= 3) {
                            st[i][j] = true;
                            f = false;
                        } else {
                            l = r = j;
                            while (l - 1 >= 0 && g[i][l - 1] == g[i][j]) {
                                l--;
                            }
                            while (r + 1 < 7 && g[i][r + 1] == g[i][j]) {
                                r++;
                            }
                            if (r - l + 1 >= 3) {
                                st[i][j] = true;
                                f = false;
                            }
                        }
                    }
                }
            }
            if (f) {
                break;
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    if (st[i][j]) {
                        cnt[0]--;
                        cnt[g[i][j]]--;
                        g[i][j] = 0;
                    }
                }
            }
        }
    }
}
