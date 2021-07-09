package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class SightseeingTrip {
    int n, m;
    int N = 110;
    int inf = (int) 1e8;
    int[][] g = new int[N][N];
    int[][] d = new int[N][N];
    int[][] pos = new int[N][N];
    int[] path = new int[N];
    int cnt;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            Arrays.fill(g[i], inf);
            Arrays.fill(d[i], inf);
        }
        for (int i = 1; i <= n; i++) {
            g[i][i] = 0;
            d[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            g[a][b] = g[b][a] = Math.min(g[a][b], c);
            d[a][b] = d[b][a] = Math.min(d[a][b], c);
        }

        int res = inf;
        for (int k = 1; k <= n; k++) {

            for (int i = 1; i < k; i++) {
                for (int j = i + 1; j < k; j++) {
                    if (d[i][j] + g[j][k] + g[k][i] < res) {
                        res = d[i][j] + g[j][k] + g[k][i];
                        cnt = 0;
                        path[cnt++] = k;
                        path[cnt++] = i;
                        getPath(i, j);
                        path[cnt++] = j;
                    }
                }
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (d[i][j] > d[i][k] + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                        pos[i][j] = k;
                    }
                }
            }
        }
        if (res == inf) {
            out.println("No solution.");
        } else {
            for (int i = 0; i < cnt; i++) {
                out.print(path[i] + " ");
            }
            out.println();
        }
    }

    private void getPath(int i, int j) {
        if (pos[i][j] == 0) {
            return;
        }
        int k = pos[i][j];
        getPath(i, k);
        path[cnt++] = k;
        getPath(k, j);
    }
}
