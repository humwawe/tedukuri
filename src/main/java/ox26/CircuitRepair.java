package ox26;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class CircuitRepair {
    int[] dx = new int[]{-1, -1, 1, 1};
    int[] dy = new int[]{-1, 1, 1, -1};
    int[] ix = new int[]{-1, -1, 0, 0};
    int[] iy = new int[]{-1, 0, 0, -1};
    char[] cs = new char[]{'\\', '/', '\\', '/'};
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++) {
            map[i] = in.nextString().toCharArray();
        }
        int[][] dist = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dist[i], inf);
        }

        Deque<int[]> queue = new LinkedList<>();
        queue.addLast(new int[]{0, 0});
        dist[0][0] = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.pollFirst();
            if (cur[0] == n && cur[1] == m) {
                out.println(dist[cur[0]][cur[1]]);
                return;
            }
            for (int i = 0; i < 4; i++) {
                int x = cur[0] + dx[i];
                int y = cur[1] + dy[i];
                if (x >= 0 && x <= n && y >= 0 && y <= m) {
                    int w = 0;
                    int a = cur[0] + ix[i];
                    int b = cur[1] + iy[i];
                    if (a >= 0 && a < n && b >= 0 && b < m) {
                        if (map[a][b] != cs[i]) {
                            w = 1;
                        }
                    }
                    if (dist[x][y] > dist[cur[0]][cur[1]] + w) {
                        dist[x][y] = dist[cur[0]][cur[1]] + w;
                        if (w == 0) {
                            queue.addFirst(new int[]{x, y});
                        } else {
                            queue.addLast(new int[]{x, y});
                        }
                    }


                }
            }
        }
        out.println("NO SOLUTION");
    }
}
