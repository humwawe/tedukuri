package ox57;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class TravelByCar {
    int inf = 0x3f3f3f3f;
    int N = 100005;
    int M = 20;
    int[] h = new int[N + 5];
    int[] ga = new int[N];
    int[] gb = new int[N];
    int[][][] f = new int[M][N][2];
    int[][][] da = new int[M][N][2];
    int[][][] db = new int[M][N][2];
    TreeSet<Integer> set = new TreeSet<>(Comparator.comparingInt(x -> h[x]));
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        h[N + 1] = inf;
        h[N + 2] = inf - 1;
        h[N + 3] = -inf;
        h[N + 4] = -inf + 1;
        set.add(N + 1);
        set.add(N + 2);
        set.add(N + 3);
        set.add(N + 4);
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            h[i] = in.nextInt();
        }
        for (int i = n; i > 0; i--) {
            Integer[][] tmp = new Integer[4][2];
            int t = i;
            tmp[0][0] = set.lower(t);
            tmp[0][1] = Math.abs(h[tmp[0][0]] - h[t]);
            tmp[1][0] = set.lower(tmp[0][0]);
            tmp[1][1] = Math.abs(h[tmp[1][0]] - h[t]);
            tmp[2][0] = set.higher(t);
            tmp[2][1] = Math.abs(h[tmp[2][0]] - h[t]);
            tmp[3][0] = set.higher(tmp[2][0]);
            tmp[3][1] = Math.abs(h[tmp[3][0]] - h[t]);
            set.add(t);
            Arrays.sort(tmp, Comparator.comparingInt(x -> x[1]));
            gb[i] = tmp[0][0] > N ? 0 : tmp[0][0];
            ga[i] = tmp[1][0] > N ? 0 : tmp[1][0];
        }
        for (int i = 1; i < n; i++) {
            f[0][i][0] = ga[i];
            f[0][i][1] = gb[i];
        }
        for (int i = 1; i <= 18; i++) {
            for (int j = 1; j <= n; j++) {
                if (j + (1 << i) <= n) {
                    for (int k = 0; k <= 1; k++) {
                        if (i == 1) {
                            f[1][j][k] = f[0][f[0][j][k]][1 - k];
                        } else {
                            f[i][j][k] = f[i - 1][f[i - 1][j][k]][k];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(da[i][j], inf);
                Arrays.fill(db[i][j], inf);
            }
        }
        for (int i = 1; i <= n; i++) {
            if (ga[i] != 0) {
                da[0][i][0] = Math.abs(h[ga[i]] - h[i]);
            }
            da[0][i][1] = 0;
        }
        for (int i = 1; i < M; i++) {
            for (int j = 1; j <= n; j++) {
                if (j + (1 << i) <= n) {
                    for (int k = 0; k <= 1; k++) {
                        if (i == 1) {
                            da[1][j][k] = da[0][j][k] + da[0][f[0][j][k]][1 - k];
                        } else {
                            da[i][j][k] = da[i - 1][j][k] + da[i - 1][f[i - 1][j][k]][k];
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            db[0][i][0] = 0;
            if (gb[i] != 0) {
                db[0][i][1] = Math.abs(h[gb[i]] - h[i]);
            }
        }
        for (int i = 1; i < M; i++) {
            for (int j = 1; j <= n; j++) {
                if (j + (1 << i) <= n) {
                    for (int k = 0; k <= 1; k++) {
                        if (i == 1) {
                            db[1][j][k] = db[0][j][k] + db[0][f[0][j][k]][k ^ 1];
                        } else {
                            db[i][j][k] = db[i - 1][j][k] + db[i - 1][f[i - 1][j][k]][k];
                        }
                    }
                }
            }
        }
        int x0 = in.nextInt();
        int m = in.nextInt();
        double ratio = inf;
        int p = 0;
        for (int i = 1; i <= n; i++) {
            long[] tmp = calc(i, x0);
            if (tmp[1] == 0) {
                continue;
            }
            double ccf = (double) tmp[0] / tmp[1];
            if (ccf < ratio) {
                ratio = ccf;
                p = i;
            }
        }
        out.println(p);
        for (int i = 0; i < m; i++) {
            int si = in.nextInt();
            int xi = in.nextInt();
            long[] tmp = calc(si, xi);
            out.println(tmp[0], tmp[1]);
        }

    }

    private long[] calc(int p, int x) {
        long la = 0, lb = 0;
        for (int i = M - 1; i >= 0; i--) {
            if (f[i][p][0] != 0 && la + lb + da[i][p][0] + db[i][p][0] <= x) {
                la += da[i][p][0];
                lb += db[i][p][0];
                p = f[i][p][0];
            }
        }
        return new long[]{la, lb};
    }

}
