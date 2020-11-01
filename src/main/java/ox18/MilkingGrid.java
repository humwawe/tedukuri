package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MilkingGrid {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int r = in.nextInt();
        int m = in.nextInt();
        String[] s = new String[r];
        boolean[] st = new boolean[m + 1];
        Arrays.fill(st, true);
        for (int i = 0; i < r; i++) {
            s[i] = in.nextString();
            T:
            for (int len = 1; len <= m; len++) {
                if (st[len]) {
                    for (int k = len; k < m; k += len) {
                        for (int l = 0; l < len && l + k < m; l++) {
                            if (s[i].charAt(l) != s[i].charAt(l + k)) {
                                st[len] = false;
                                continue T;
                            }
                        }
                    }
                }
            }
        }
        int width = 0;
        for (int i = 1; i <= m; i++) {
            if (st[i]) {
                width = i;
                break;
            }
        }
        int[] next = new int[r + 1];
        int i, j;
        j = next[0] = -1;
        i = 0;
        while (i < r) {
            while (-1 != j && !s[i].substring(0, width).equals(s[j].substring(0, width))) {
                j = next[j];
            }
            next[++i] = ++j;
        }
        int height = r - next[r];
        out.println(height * width);
    }
}
