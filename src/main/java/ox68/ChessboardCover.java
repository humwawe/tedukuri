package ox68;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class ChessboardCover {
    int N = 105 * 105;
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m;
    boolean[][] ban = new boolean[105][105];
    int[] match = new int[N];
    int[][] fs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            ban[a][b] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!ban[i][j] && (i + j) % 2 == 0) {
                    for (int[] f : fs) {
                        int x = i + f[0];
                        int y = j + f[1];
                        if (x >= 1 && x <= n && y >= 1 && y <= n && !ban[x][y]) {
                            add((i - 1) * n + j, (x - 1) * n + y);
                        }
                    }
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!ban[i][j] && (i + j) % 2 == 0) {
                    Arrays.fill(vis, false);
                    if (find((i - 1) * n + j)) {
                        res++;
                    }
                }
            }
        }
        out.println(res);

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
