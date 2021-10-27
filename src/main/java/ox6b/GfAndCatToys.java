package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

public class GfAndCatToys {
    int N = 105;
    int[][] dist = new int[N][N];
    int n, m;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        init();

        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            dist[a][b] = dist[b][a] = 1;
        }
        floyd();
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (dist[i][j] != inf) {
                    res = Math.max(res, dist[i][j]);
                }
            }
        }
        out.println(res);
    }

    void init() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = inf;
                }
            }
        }
    }

    void floyd() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
