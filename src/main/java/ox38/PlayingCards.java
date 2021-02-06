package ox38;

import common.io.InputReader;
import common.io.OutputWriter;

public class PlayingCards {
    int a, b, c, d;
    int n = 15;
    double inf = 1e20;
    Double[][][][][][] memo = new Double[n][n][n][n][5][5];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();
        d = in.nextInt();
        double t = dfs(0, 0, 0, 0, 4, 4);
        if (t > inf / 2) {
            t = -1;
        }
        out.printf("%.3f", t);
    }

    private double dfs(int aa, int bb, int cc, int dd, int x, int y) {
        if (memo[aa][bb][cc][dd][x][y] != null) {
            return memo[aa][bb][cc][dd][x][y];
        }
        int as = aa + (x == 0 ? 1 : 0) + (y == 0 ? 1 : 0);
        int bs = bb + (x == 1 ? 1 : 0) + (y == 1 ? 1 : 0);
        int cs = cc + (x == 2 ? 1 : 0) + (y == 2 ? 1 : 0);
        int ds = dd + (x == 3 ? 1 : 0) + (y == 3 ? 1 : 0);
        if (as >= a && bs >= b && cs >= c && ds >= d) {
            return 0;
        }
        int sum = aa + bb + cc + dd;
        if (x != 4) {
            sum++;
        }
        if (y != 4) {
            sum++;
        }
        sum = 54 - sum;
        if (sum <= 0) {
            return inf;
        }
        double res = 1;
        if (aa < 13) {
            res += (13.0 - aa) / sum * dfs(aa + 1, bb, cc, dd, x, y);
        }
        if (bb < 13) {
            res += (13.0 - bb) / sum * dfs(aa, bb + 1, cc, dd, x, y);
        }
        if (cc < 13) {
            res += (13.0 - cc) / sum * dfs(aa, bb, cc + 1, dd, x, y);
        }
        if (dd < 13) {
            res += (13.0 - dd) / sum * dfs(aa, bb, cc, dd + 1, x, y);
        }
        if (x == 4) {
            double tmp = inf;
            for (int i = 0; i < 4; i++) {
                tmp = Math.min(tmp, 1.0 / sum * dfs(aa, bb, cc, dd, i, y));
            }
            res += tmp;
        }
        if (y == 4) {
            double tmp = inf;
            for (int i = 0; i < 4; i++) {
                tmp = Math.min(tmp, 1.0 / sum * dfs(aa, bb, cc, dd, x, i));
            }
            res += tmp;
        }
        return memo[aa][bb][cc][dd][x][y] = res;

    }
}
