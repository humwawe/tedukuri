package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class CheckerboardDivision {
    int n;
    int N = 9;
    int[][] s = new int[N][N];
    double inf = 1e9;
    double avg;
    Double[][][][][] memo = new Double[N][N][N][N][15];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                s[i][j] = in.nextInt();
                s[i][j] += s[i - 1][j] + s[i][j - 1] - s[i - 1][j - 1];
            }
        }
        avg = (double) s[8][8] / n;

        out.printf("%.3f\n", Math.sqrt(dp(1, 1, 8, 8, n) / n));
    }

    private double dp(int x1, int y1, int x2, int y2, int k) {
        if (k == 1) {
            return get(x1, y1, x2, y2);
        }
        if (memo[x1][x2][y1][y2][k] != null) {
            return memo[x1][x2][y1][y2][k];
        }
        double t = inf;
        for (int i = x1; i < x2; ++i) {
            t = Math.min(t, dp(x1, y1, i, y2, k - 1) + get(i + 1, y1, x2, y2));
            t = Math.min(t, dp(i + 1, y1, x2, y2, k - 1) + get(x1, y1, i, y2));
        }
        for (int i = y1; i < y2; ++i) {
            t = Math.min(t, dp(x1, y1, x2, i, k - 1) + get(x1, i + 1, x2, y2));
            t = Math.min(t, dp(x1, i + 1, x2, y2, k - 1) + get(x1, y1, x2, i));
        }
        return memo[x1][x2][y1][y2][k] = t;
    }

    double get(int x1, int y1, int x2, int y2) {
        double sum = s[x2][y2] - s[x2][y1 - 1] - s[x1 - 1][y2] + s[x1 - 1][y1 - 1] - avg;
        return sum * sum;
    }
}
