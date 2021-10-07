package ox69;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class KnightPlacement {
    int N = (int) (1e4 + 5);
    int M = 8 * N;
    int[] h = new int[M];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int[] match = new int[M];
    boolean[] vis = new boolean[M];
    boolean[][] ban = new boolean[105][105];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            ban[a][b] = true;
        }
        int[][] fs = new int[][]{{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (!ban[i][j] && (i + j) % 2 == 0) {
                    for (int[] f : fs) {
                        int x = i + f[0];
                        int y = j + f[1];
                        if (x >= 1 && x <= n && y >= 1 && y <= m && !ban[x][y]) {
                            add((i - 1) * m + j, (x - 1) * m + y);
                        }
                    }
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (!ban[i][j] && (i + j) % 2 == 0) {
                    Arrays.fill(vis, false);
                    if (find((i - 1) * m + j)) {
                        res++;
                    }
                }
            }
        }
        out.println(n * m - t - res);
    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }
            }
        }
        return false;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

}
