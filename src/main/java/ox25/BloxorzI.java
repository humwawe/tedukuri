package ox25;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BloxorzI {
    int[][] fs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
    int[][][] nextFs = new int[][][]{{{0, -2, 1}, {0, 1, 1}, {-2, 0, 2}, {1, 0, 2}},
            {{0, -1, 0}, {0, 2, 0}, {-1, 0, 1}, {1, 0, 1}},
            {{0, -1, 2}, {0, 1, 2}, {-1, 0, 0}, {2, 0, 0}}};

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        char[][] s = new char[n][m];
        for (int i = 0; i < n; i++) {
            s[i] = in.nextString().toCharArray();
        }
        int[] start = new int[3];
        int[] end = new int[3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i][j] == 'O') {
                    end[0] = i;
                    end[1] = j;
                    s[i][j] = '.';
                } else if (s[i][j] == 'X') {
                    for (int k = 0; k < 4; k++) {
                        int[] f = fs[k];
                        int x = i + f[0];
                        int y = j + f[1];
                        if (x >= 0 && x < n && y >= 0 && y < m && s[x][y] == 'X') {
                            start[0] = i;
                            start[1] = j;
                            if (k < 2) {
                                start[2] = 1;
                            } else {
                                start[2] = 2;
                            }
                            s[i][j] = '.';
                            s[x][y] = '.';
                            break;
                        }
                    }
                }
                if (s[i][j] == 'X') {
                    start[0] = i;
                    start[1] = j;
                    s[i][j] = '.';
                }
            }
        }
        int[][][] dist = new int[n][m][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dist[i][j], -1);
            }
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        dist[start[0]][start[1]][start[2]] = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int[] next = new int[3];
                next[0] = cur[0] + nextFs[cur[2]][i][0];
                next[1] = cur[1] + nextFs[cur[2]][i][1];
                next[2] = nextFs[cur[2]][i][2];
                if (!valid(s, next)) {
                    continue;
                }
                if (dist[next[0]][next[1]][next[2]] == -1) {
                    dist[next[0]][next[1]][next[2]] = dist[cur[0]][cur[1]][cur[2]] + 1;
                    queue.add(next);
                    if (next[0] == end[0] && next[1] == end[1] && end[2] == next[2]) {
                        out.println(dist[next[0]][next[1]][next[2]]);
                        return;
                    }
                }
            }
        }
        out.println("Impossible");
    }

    private boolean valid(char[][] s, int[] next) {
        int x = next[0];
        int y = next[1];
        int lie = next[2];
        if (x < 0 || x >= s.length || y < 0 || y > s[0].length) {
            return false;
        }
        if (s[x][y] == '#') {
            return false;
        }
        if (lie == 0 && s[x][y] != '.') {
            return false;
        }
        if (lie == 1 && s[x][y + 1] == '#') {
            return false;
        }
        if (lie == 2 && s[x + 1][y] == '#') {
            return false;
        }
        return true;
    }
}
