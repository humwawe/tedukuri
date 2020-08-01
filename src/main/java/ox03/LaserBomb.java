package ox03;

import common.io.InputReader;
import common.io.OutputWriter;

public class LaserBomb {
    int N = (int) 5e3 + 5;
    int[][] a;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = new int[N][N];
        int n = in.nextInt();
        int r = in.nextInt();
        int x, y, v;
        for (int i = 0; i < n; i++) {
            x = in.nextInt();
            y = in.nextInt();
            v = in.nextInt();
            a[x + 1][y + 1] = v;
        }

        for (int i = 1; i < N; ++i) {
            for (int j = 1; j < N; ++j) {
                a[i][j] += a[i - 1][j] + a[i][j - 1] - a[i - 1][j - 1];
            }
        }
        int res = 0;
        for (int i = r; i < N; ++i) {
            for (int j = r; j < N; ++j) {
                res = Math.max(res, a[i][j] - a[i - r][j] - a[i][j - r] + a[i - r][j - r]);
            }
        }
        out.println(res);
    }
}
