package ox34;

import common.io.InputReader;
import common.io.OutputWriter;

public class StoneGame {
    int N = 70;
    long[] f = new long[N];
    long[][] d = new long[N][N];
    long[][][] e = new long[N][N][N];
    char[] s = new char[100];
    int n, m, t, act;
    int[][] a = new int[10][10];
    String[] b = new String[10];
    int[][] c = new int[10][10];
    int p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        t = in.nextInt();
        act = in.nextInt();
        for (int i = 1; i <= n; i++) {
            String tmp = in.nextString();
            for (int j = 1; j <= m; j++) {
                a[i][j] = tmp.charAt(j - 1) - '0';
            }
        }
        for (int i = 0; i < act; i++) {
            b[i] = in.nextString();
        }
        p = n * m + 1;
        for (int k = 1; k <= 60; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    int x = a[i][j];
                    int y = c[i][j];
                    if (b[x].charAt(y) >= '0' && b[x].charAt(y) <= '9') {
                        e[k][p][num(i, j)] = b[x].charAt(y) - '0';
                        e[k][num(i, j)][num(i, j)] = 1;
                    } else if (b[x].charAt(y) == 'N' && i > 1) {
                        e[k][num(i, j)][num(i - 1, j)] = 1;
                    } else if (b[x].charAt(y) == 'W' && j > 1) {
                        e[k][num(i, j)][num(i, j - 1)] = 1;
                    } else if (b[x].charAt(y) == 'S' && i < n) {
                        e[k][num(i, j)][num(i + 1, j)] = 1;
                    } else if (b[x].charAt(y) == 'E' && j < m) {
                        e[k][num(i, j)][num(i, j + 1)] = 1;
                    }
                    c[i][j] = (y + 1) % b[x].length();
                }
            }
            e[k][p][p] = 1;
        }
        for (int i = 0; i < N; i++) {
            System.arraycopy(e[1][i], 0, d[i], 0, N);
        }
        for (int i = 2; i <= 60; i++) {
            d = mulself(d, e[i]);
        }
        long res = 0;
        f[p] = 1;
        int w = t / 60;
        while (w > 0) {
            if ((w & 1) == 1) {
                f = mul(f, d);
            }
            d = mulself(d, d);
            w >>= 1;
        }
        w = t % 60;
        for (int i = 1; i <= w; i++) {
            f = mul(f, e[i]);
        }
        for (int i = 1; i < p; i++) {
            res = Math.max(res, f[i]);
        }
        out.println(res);
    }

    private long[] mul(long[] a, long[][] b) {
        long[] w = new long[70];
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= p; j++) {
                w[i] += a[j] * b[j][i];
            }
        }
        return w;
    }

    private long[][] mulself(long[][] a, long[][] b) {
        long[][] w = new long[N][N];
        for (int i = 1; i <= p; i++) {
            for (int k = 1; k <= p; k++) {
                if (a[i][k] != 0) {
                    for (int j = 1; j <= p; j++) {
                        w[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
        return w;
    }

    int num(int i, int j) {
        return (i - 1) * m + j;
    }
}
