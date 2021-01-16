package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class SamuraiStyleCow {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int c = in.nextInt();
        int r = in.nextInt();
        char[][] g = new char[r][c];
        for (int i = 0; i < r; i++) {
            g[i] = in.nextString().toCharArray();
        }
        int[] start = new int[2];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (g[i][j] == 'K') {
                    start[0] = i;
                    start[1] = j;
                }
            }
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        g[start[0]][start[1]] = '*';
        int[][] d = new int[r][c];
        int[][] fs = new int[][]{{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] f : fs) {
                int x = cur[0] + f[0];
                int y = cur[1] + f[1];
                if (x >= 0 && x < r && y >= 0 && y < c && g[x][y] != '*') {
                    if (g[x][y] == 'H') {
                        out.println(d[cur[0]][cur[1]] + 1);
                        return;
                    }
                    g[x][y] = '*';
                    d[x][y] = d[cur[0]][cur[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }

    }
}
