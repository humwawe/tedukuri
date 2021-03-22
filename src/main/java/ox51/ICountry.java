package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

public class ICountry {
    int N = 16, K = 226;
    int n, m, k;
    int[][][][][][] f = new int[2][2][N][N][N][K];
    int[][] a = new int[N][N];
    int[][] b = new int[N][N];
    int[][][][][][] cl = new int[2][2][N][N][N][K];
    int[][][][][][] cr = new int[2][2][N][N][N][K];
    int[][][][][][] dx = new int[2][2][N][N][N][K];
    int[][][][][][] dy = new int[2][2][N][N][N][K];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                a[i][j] = in.nextInt();
                b[i][j] = b[i][j - 1] + a[i][j];
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int l = 1; l <= m; l++) {
                    for (int r = l; r <= m; r++) {
                        if (r - l + 1 > j) {
                            break;
                        }
                        int w = b[i][r] - b[i][l - 1];
                        for (int x = 0; x < 2; x++) {
                            for (int y = 0; y < 2; y++) {
                                upd(i, j, l, r, x, y, w, m, 0, x, y);
                            }
                        }
                        for (int p = l; p <= r; p++) {
                            for (int q = p; q <= r; q++) {
                                upd(i, j, l, r, 1, 1, f[1][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 1);
                            }
                        }
                        for (int p = 1; p <= l; p++) {
                            for (int q = r; q <= m; q++) {
                                upd(i, j, l, r, 0, 0, f[0][0][i - 1][p][q][j - (r - l + 1)] + w, p, q, 0, 0);
                                upd(i, j, l, r, 0, 0, f[1][0][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 0);
                                upd(i, j, l, r, 0, 0, f[0][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 0, 1);
                                upd(i, j, l, r, 0, 0, f[1][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 1);
                            }
                        }
                        for (int p = l; p <= r; p++) {
                            for (int q = r; q <= m; q++) {
                                upd(i, j, l, r, 1, 0, f[1][0][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 0);
                                upd(i, j, l, r, 1, 0, f[1][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 1);
                            }
                        }
                        for (int p = 1; p <= l; p++) {
                            for (int q = l; q <= r; q++) {
                                upd(i, j, l, r, 0, 1, f[0][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 0, 1);
                                upd(i, j, l, r, 0, 1, f[1][1][i - 1][p][q][j - (r - l + 1)] + w, p, q, 1, 1);
                            }
                        }
                    }
                }
            }
        }
        int res = 0, ai = 0, al = 0, ar = 0, ax = 0, ay = 0;
        for (int i = 1; i <= n; i++) {
            for (int l = 1; l <= m; l++) {
                for (int r = l; r <= m; r++) {
                    for (int x = 0; x < 2; x++) {
                        for (int y = 0; y < 2; y++) {
                            if (res < f[x][y][i][l][r][k]) {
                                res = f[x][y][i][l][r][k];
                                ai = i;
                                al = l;
                                ar = r;
                                ax = x;
                                ay = y;
                            }
                        }
                    }
                }
            }
        }
        out.println("Oil : " + res);
        print(out, ai, k, al, ar, ax, ay);
    }

    void print(OutputWriter out, int i, int j, int l, int r, int x, int y) {
        if (j == 0) {
            return;
        }
        print(out, i - 1, j - (r - l + 1), cl[x][y][i][l][r][j], cr[x][y][i][l][r][j], dx[x][y][i][l][r][j], dy[x][y][i][l][r][j]);
        for (j = l; j <= r; j++) {
            out.printf("%d %d\n", i, j);
        }
    }

    void upd(int i, int j, int l, int r, int x, int y, int w, int L, int R, int X, int Y) {
        if (w < f[x][y][i][l][r][j]) {
            return;
        }
        f[x][y][i][l][r][j] = w;
        cl[x][y][i][l][r][j] = L;
        cr[x][y][i][l][r][j] = R;
        dx[x][y][i][l][r][j] = X;
        dy[x][y][i][l][r][j] = Y;
    }
}
