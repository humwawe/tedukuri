package ox22;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Sudoku {
    int[] row = new int[9];
    int[] col = new int[9];
    int[] mat = new int[9];
    int[][] map = new int[81][3];
    int[] num = new int[1 << 9];
    int[] cnt = new int[1 << 9];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        for (int i = 0; i < 9; i++) {
            num[1 << i] = i;
        }
        for (int i = 0; i < 81; i++) {
            int r = i / 9;
            int c = i % 9;
            int m = r / 3 * 3 + c / 3;
            map[i][0] = r;
            map[i][1] = c;
            map[i][2] = m;
        }
        for (int i = 0; i < 1 << 9; i++) {
            for (int j = i; j != 0; j -= j & -j) {
                cnt[i]++;
            }
        }
        while (true) {
            Arrays.fill(row, 0b111111111);
            Arrays.fill(col, 0b111111111);
            Arrays.fill(mat, 0b111111111);
            String s = in.nextString();
            if (s.equals("end")) {
                return;
            }
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                char sc = s.charAt(i);
                if (sc != '.') {
                    row[map[i][0]] ^= 1 << sc - '1';
                    col[map[i][1]] ^= 1 << sc - '1';
                    mat[map[i][2]] ^= 1 << sc - '1';
                }else {
                    count++;
                }
            }
            StringBuilder res = new StringBuilder(s);
            if (dfs(count, res)) {
                out.println(res);
            }
        }
    }

    private boolean dfs(int count, StringBuilder s) {
        if (count == 0) {
            return true;
        }
        int tmp = 10;
        int st = 0;
        int p = 0;
        int i = 0, j = 0, m = 0;
        for (int pos = 0; pos < s.length(); pos++) {
            if (s.charAt(pos) == '.') {
                if (cnt[row[map[pos][0]] & col[map[pos][1]] & mat[map[pos][2]]] < tmp) {
                    i = map[pos][0];
                    j = map[pos][1];
                    m = map[pos][2];
                    st = row[i] & col[j] & mat[m];
                    p = pos;
                    tmp=cnt[st];
                }
            }
        }
        for (int lowbit; st != 0; st -= lowbit) {
            lowbit = st & -st;
            int k = num[lowbit];
            s.setCharAt(p, (char) (k + '1'));
            row[i] ^= 1 << k;
            col[j] ^= 1 << k;
            mat[m] ^= 1 << k;
            if (dfs(count - 1, s)) {
                return true;
            }
            s.setCharAt(p, '.');
            row[i] ^= 1 << k;
            col[j] ^= 1 << k;
            mat[m] ^= 1 << k;
        }
        return false;
    }
}
