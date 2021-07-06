package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class SortingItAllOut {
    int N = 26;
    int[][] d = new int[N][N];
    int[][] g = new int[N][N];
    int[] deg = new int[N];
    int n, m;
    int t;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], 0);
            Arrays.fill(g[i], 0);
        }
        Arrays.fill(deg, 0);
        if (n == 0 && m == 0) {
            return;
        }
        boolean find = false;
        boolean fail = false;
        for (int i = 0; i < m; i++) {
            String s = in.nextString();
            if (find || fail) {
                continue;
            }
            int u = s.charAt(0) - 'A';
            int v = s.charAt(2) - 'A';
            deg[v]++;
            g[u][v] = 1;
            d[u][v] = 1;
            floyd();
            find = true;
            for (int j = 0; j < n; j++) {
                // A < A
                if (g[j][j] > 0) {
                    fail = true;
                    break;
                }
                for (int k = 0; k < n; k++) {
                    if (j != k && (g[j][k] == 0 && g[k][j] == 0)) {
                        find = false;
                    }
                }
            }
        }
    }

    private void floyd() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (g[i][k] > 0 && g[k][j] > 0) {
                        g[i][j] = 1;
                    }
                }
            }
        }
    }
}
