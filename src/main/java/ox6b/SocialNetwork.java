package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

public class SocialNetwork {
    int N = 105;
    int[][] dist = new int[N][N];
    long[][] cnt = new long[N][N];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        init();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            dist[a][b] = dist[b][a] = c;
            cnt[a][b] = cnt[b][a] = 1;
        }
        floyd();
        for (int u = 1; u <= n; u++) {
            double res = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (i != j && j != u && u != i && cnt[i][j] > 0) {
                        if (dist[i][u] + dist[u][j] == dist[i][j]) {
                            res += cnt[i][u] * cnt[u][j] * 1.0 / cnt[i][j];
                        }
                    }
                }
            }
            out.printf("%.3f\n", res);
        }
    }

    void init() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = 0x3f3f3f3f;
                }
            }
        }
    }

    void floyd() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][k] + dist[k][j] == dist[i][j]) {
                        cnt[i][j] += cnt[i][k] * cnt[k][j];
                    } else if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        cnt[i][j] = cnt[i][k] * cnt[k][j];
                    }
                }
            }
        }
    }
}
