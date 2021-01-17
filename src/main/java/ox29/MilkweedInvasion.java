package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class MilkweedInvasion {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int col = in.nextInt();
        int row = in.nextInt();
        int y = in.nextInt() - 1;
        int x = row - in.nextInt();
        char[][] g = new char[row][col];
        for (int i = 0; i < row; i++) {
            g[i] = in.nextString().toCharArray();
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{x, y});
        g[x][y] = '*';
        int[][] fs = new int[][]{{-1, 0}, {-1, 1}, {-1, -1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int res = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            res++;
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                for (int[] f : fs) {
                    int nx = cur[0] + f[0];
                    int ny = cur[1] + f[1];
                    if (nx >= 0 && nx < row && ny >= 0 && ny < col && g[nx][ny] != '*') {
                        queue.add(new int[]{nx, ny});
                        g[nx][ny] = '*';
                    }
                }
            }
        }
        out.println(res);
    }
}
