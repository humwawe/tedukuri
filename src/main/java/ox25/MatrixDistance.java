package ox25;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MatrixDistance {
    int[][] fs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] s = new char[n][m];
        for (int i = 0; i < n; i++) {
            s[i] = in.nextString().toCharArray();
        }
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i][j] == '1') {
                    queue.add(new int[]{i, j});
                    dist[i][j] = 0;
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] f : fs) {
                int x = cur[0] + f[0];
                int y = cur[1] + f[1];
                if (x >= 0 && x < n && y >= 0 && y < m) {
                    if (dist[x][y] == -1) {
                        queue.add(new int[]{x, y});
                        dist[x][y] = dist[cur[0]][cur[1]] + 1;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out.print(dist[i][j] + " ");
            }
            out.println();
        }
    }
}
