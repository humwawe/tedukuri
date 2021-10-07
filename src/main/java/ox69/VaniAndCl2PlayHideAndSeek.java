package ox69;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class VaniAndCl2PlayHideAndSeek {
    int N = 222;
    // 邻接矩阵
    boolean[][] cl = new boolean[N][N];
    int[] match = new int[N];
    boolean[] vis = new boolean[N];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            cl[a][b] = true;
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    cl[i][j] |= cl[i][k] && cl[k][j];
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        out.println(n - res);
    }

    boolean find(int x) {
        for (int i = 1; i <= n; i++) {
            if (cl[x][i] && !vis[i]) {
                vis[i] = true;
                if (match[i] == 0 || find(match[i])) {
                    match[i] = x;
                    return true;
                }
            }
        }
        return false;
    }

}
