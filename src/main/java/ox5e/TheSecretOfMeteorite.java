package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class TheSecretOfMeteorite {
    int l1, l2, l3, dep;
    int N = 12;
    int mod = 11380;
    long[][][][] f = new long[N][N][N][39];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        l1 = in.nextInt();
        l2 = in.nextInt();
        l3 = in.nextInt();
        dep = in.nextInt();
        for (int i = 0; i <= dep; i++) {
            f[0][0][0][i] = 1;
        }
        for (int d = 1; d <= dep; d++) {
            for (int i = 0; i <= l1; i++) {
                for (int j = 0; j <= l2; j++) {
                    for (int k = 0; k <= l3; k++) {
                        if (i == 0 && j == 0 && k == 0) {
                            continue;
                        }
                        for (int a = 0; a < i; a++) {
                            for (int b = 0; b <= j; b++) {
                                for (int c = 0; c <= k; c++) {
                                    f[i][j][k][d] = (f[i][j][k][d] + f[i - a - 1][j - b][k - c][d] * f[a][b][c][d - 1]) % mod;
                                }
                            }
                        }

                        for (int a = 0; a < j; a++) {
                            for (int b = 0; b <= k; b++) {
                                f[i][j][k][d] = (f[i][j][k][d] + f[i][j - a - 1][k - b][d] * f[0][a][b][d - 1]) % mod;
                            }
                        }

                        for (int a = 0; a < k; a++) {
                            f[i][j][k][d] = (f[i][j][k][d] + f[i][j][k - a - 1][d] * f[0][0][a][d - 1]) % mod;
                        }
                    }
                }
            }
        }
        if (dep == 0) {
            if (l1 == 0 && l2 == 0 && l3 == 0) {
                out.println("1");
            } else {
                out.println("0");
            }
        } else if (l1 == 0 && l2 == 0 && l3 == 0) {
            out.println("0");
        } else {
            out.println((f[l1][l2][l3][dep] - f[l1][l2][l3][dep - 1] + mod) % mod);
        }
    }
}
