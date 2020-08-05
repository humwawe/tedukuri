package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

public class ThePilotsBrothersRefrigerator {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[][] a = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char c = in.nextCharacter();
                if (c == '-') {
                    a[i][j] = 1;
                }
            }
        }
        int lim = 1 << 16;
        int res = 17;
        int idx = 0;
        for (int st = 1; st < lim; st++) {
            int cnt = count(st);
            if (cnt >= res) {
                continue;
            }
            int[][] b = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    b[i][j] = a[i][j];
                }
            }
            for (int i = 0; i < 16; i++) {
                if ((st >> i & 1) == 1) {
                    int x = i / 4;
                    int y = i % 4;
                    for (int j = 0; j < 4; j++) {
                        b[x][j] ^= 1;
                    }
                    for (int j = 0; j < 4; j++) {
                        if (j != x) {
                            b[j][y] ^= 1;
                        }
                    }
                }
            }
            boolean f = true;
            for (int i = 0; i < 4 & f; i++) {
                for (int j = 0; j < 4; j++) {
                    if (b[i][j] == 0) {
                        f = false;
                    }
                }
            }
            if (f) {
                res = cnt;
                idx = st;
            }
        }
        out.println(res);
        for (int i = 0; i < 16; i++) {
            if ((idx >> i & 1) == 1) {
                int x = i / 4;
                int y = i % 4;
                out.println(x + 1 + " " + (y + 1));
            }
        }
    }

    private int count(int st) {
        int res = 0;
        while (st != 0) {
            st = st & (st - 1);
            res++;
        }
        return res;
    }
}
