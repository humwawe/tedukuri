package ox56;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class ArtilleryPosition {
    int N = 110, M = 1 << 10;
    int[] w = new int[N];
    List<Integer> state = new ArrayList<>();
    int[] cnt = new int[M];
    int[][][] f = new int[2][M][M];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = in.nextCharacter();
                if (c == 'H') {
                    w[i] += (1 << j);
                }
            }
        }
        for (int i = 0; i < 1 << m; i++) {
            if (check(i)) {
                state.add(i);
                cnt[i] = count(i);
            }
        }
        for (int i = 0; i < n + 2; i++) {
            for (Integer a : state) {
                for (Integer b : state) {
                    for (Integer c : state) {
                        if ((a & b) != 0 || (a & c) != 0 || (b & c) != 0) {
                            continue;
                        }
                        if ((w[i] & a) != 0 || (i > 0) && (w[i - 1] & b) != 0) {
                            continue;
                        }
                        f[i & 1][a][b] = Math.max(f[i & 1][a][b], f[i - 1 & 1][b][c] + cnt[a]);
                    }
                }
            }
        }
        out.println(f[(n + 2) & 1][0][0]);
    }

    private int count(int num) {
        return Integer.bitCount(num);
    }

    private boolean check(int num) {
        for (int i = 0; i < m; i++) {
            if ((num >> i & 1) == 1 && ((num >> i + 1 & 1) == 1 || (num >> i + 2 & 1) == 1)) {
                return false;
            }
        }
        return true;
    }
}
