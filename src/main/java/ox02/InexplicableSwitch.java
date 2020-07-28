package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

public class InexplicableSwitch {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[][] mat = new int[5][5];
        for (int i = 0; i < 5; i++) {
            String s = in.nextString();
            for (int j = 0; j < 5; j++) {
                mat[i][j] = s.charAt(j) - '0';
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 1 << 5; i++) {
            int tmp = helper(i, mat);
            if (tmp == -1) {
                continue;
            }
            res = Math.min(res, tmp);
        }
        if (res > 6) {
            out.println(-1);
        } else {
            out.println(res);
        }
    }

    private int helper(int st, int[][] mat) {
        int[][] b = new int[6][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                b[i][j] = mat[i][j];
            }
        }
        int res = 0;
        for (int i = 0; i < 5; i++) {
            if ((st >> i & 1) == 0) {
                res += 1;
                b[0][i] ^= 1;
                b[1][i] ^= 1;
                if (i > 0) {
                    b[0][i - 1] ^= 1;
                }
                if (i < 4) {
                    b[0][i + 1] ^= 1;
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (b[i - 1][j] == 0) {
                    res += 1;
                    b[i - 1][j] ^= 1;
                    b[i][j] ^= 1;
                    b[i + 1][j] ^= 1;
                    if (j > 0) {
                        b[i][j - 1] ^= 1;
                    }
                    if (j < 4) {
                        b[i][j + 1] ^= 1;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (b[4][i] == 0) {
                return -1;
            }
        }

        return res;
    }
}
