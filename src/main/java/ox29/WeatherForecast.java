package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class WeatherForecast {
    int N = 366;
    int n;
    boolean[][][][][][][] vis;
    int[][][] g;
    int[][] fs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {0, 0}};

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        g = new int[n + 1][4][4];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    g[i][j][k] = in.nextInt();
                }
            }
        }
        out.println(bfs());
    }

    private int bfs() {
        vis = new boolean[n + 1][3][3][7][7][7][7];
        if (g[1][1][1] == 1 || g[1][1][2] == 1 || g[1][2][1] == 1 || g[1][2][2] == 1) {
            return 0;
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{1, 1, 1, 1, 1, 1, 1});
        vis[1][1][1][1][1][1][1] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[0] == n) {
                return 1;
            }
            for (int[] f : fs) {
                for (int i = 1; i <= 2; i++) {
                    int x = cur[1] + f[0] * i;
                    int y = cur[2] + f[1] * i;
                    if (x < 0 || x >= 3 || y < 0 || y >= 3) {
                        continue;
                    }
                    if (g[cur[0] + 1][x][y] == 1 || g[cur[0] + 1][x + 1][y] == 1 || g[cur[0] + 1][x][y + 1] == 1 || g[cur[0] + 1][x + 1][y + 1] == 1) {
                        continue;
                    }
                    int s0 = cur[3];
                    int s1 = cur[4];
                    int s2 = cur[5];
                    int s3 = cur[6];
                    if (x == 0 && y == 0) {
                        s0 = 0;
                    } else if (++s0 == 7) {
                        continue;
                    }
                    if (x == 0 && y == 2) {
                        s1 = 0;
                    } else if (++s1 == 7) {
                        continue;
                    }
                    if (x == 2 && y == 0) {
                        s2 = 0;
                    } else if (++s2 == 7) {
                        continue;
                    }
                    if (x == 2 && y == 2) {
                        s3 = 0;
                    } else if (++s3 == 7) {
                        continue;
                    }
                    if (vis[cur[0] + 1][x][y][s0][s1][s2][s3]) {
                        continue;
                    }
                    vis[cur[0] + 1][x][y][s0][s1][s2][s3] = true;
                    queue.add(new int[]{cur[0] + 1, x, y, s0, s1, s2, s3});
                }
            }
        }
        return 0;
    }
}
