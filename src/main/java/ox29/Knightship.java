package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class Knightship {
    char[][] a = new char[5][5];
    int[][] fs = new int[][]{{1, 2}, {-1, -2}, {-1, 2}, {1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
    char[][] end = new char[][]{
            {'1', '1', '1', '1', '1'},
            {'0', '1', '1', '1', '1'},
            {'0', '0', '*', '1', '1'},
            {'0', '0', '0', '0', '1'},
            {'0', '0', '0', '0', '0'}};


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        for (int i = 0; i < 5; i++) {
            a[i] = in.nextString().toCharArray();
        }
        int[] start = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (a[i][j] == '*') {
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
        int depth = 0;
        while (depth <= 15 && !idaStar(start[0], start[1], depth)) {
            depth++;
        }
        if (depth <= 15) {
            out.println(depth);
        } else {
            out.println(-1);
        }
    }

    private int f() {
        int res = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (a[i][j] != '*' && a[i][j] != end[i][j]) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean idaStar(int i, int j, int dep) {
        int val = f();
        if (val == 0) {
            return true;
        }
        if (val > dep) {
            return false;
        }
        for (int[] f : fs) {
            int x = i + f[0];
            int y = j + f[1];
            if (x >= 0 && x < 5 && y >= 0 && y < 5) {
                char t = a[x][y];
                a[x][y] = a[i][j];
                a[i][j] = t;
                if (idaStar(x, y, dep - 1)) {
                    return true;
                }
                t = a[x][y];
                a[x][y] = a[i][j];
                a[i][j] = t;
            }
        }
        return false;
    }
}
