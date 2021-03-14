package ox51;

import common.io.InputReader;
import common.io.OutputWriter;

public class MrYoungsPicturePermutations {
    int N = 31;
    int n;
    long[][][][][] dp = new long[N][N][N][N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        int[] s = new int[5];
        for (int i = 0; i < n; i++) {
            s[i] = in.nextInt();
        }

        for (int a = 0; a <= s[0]; a++) {
            for (int b = 0; b <= Math.min(a, s[1]); b++) {
                for (int c = 0; c <= Math.min(b, s[2]); c++) {
                    for (int d = 0; d <= Math.min(c, s[3]); d++) {
                        for (int e = 0; e <= Math.min(d, s[4]); e++) {
                            if (a == 0 && b == 0 && c == 0 && d == 0 && e == 0) {
                                dp[0][0][0][0][0] = 1;
                            } else {
                                dp[a][b][c][d][e] = 0;
                            }
                            if (a > 0 && a - 1 >= b) {
                                dp[a][b][c][d][e] += dp[a - 1][b][c][d][e];
                            }
                            if (b > 0 && b - 1 >= c) {
                                dp[a][b][c][d][e] += dp[a][b - 1][c][d][e];
                            }
                            if (c > 0 && c - 1 >= d) {
                                dp[a][b][c][d][e] += dp[a][b][c - 1][d][e];
                            }
                            if (d > 0 && d - 1 >= e) {
                                dp[a][b][c][d][e] += dp[a][b][c][d - 1][e];
                            }
                            if (e > 0) {
                                dp[a][b][c][d][e] += dp[a][b][c][d][e - 1];
                            }
                        }
                    }
                }
            }
        }
        out.println(dp[s[0]][s[1]][s[2]][s[3]][s[4]]);
    }
}
