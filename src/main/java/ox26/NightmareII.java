package ox26;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class NightmareII {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] map = new char[n][m];
        int[] man = new int[2];
        int[] girl = new int[2];
        int[][] z = new int[2][2];
        int[][] st = new int[n][m];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = in.nextCharacter();
                map[i][j] = c;
                if (c == 'M') {
                    man[0] = i;
                    man[1] = j;
                }
                if (c == 'G') {
                    girl[0] = i;
                    girl[1] = j;
                }
                if (c == 'Z') {
                    z[cnt][0] = i;
                    z[cnt++][1] = j;
                }
            }
        }
        Queue<int[]> qm = new ArrayDeque<>();
        qm.add(man);
        Queue<int[]> qg = new ArrayDeque<>();
        qg.add(girl);

        int step = 0;
        int[][] fs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!qm.isEmpty() || !qg.isEmpty()) {
            step++;
            for (int k = 0; k < 3; k++) {
                int size = qm.size();
                for (int i = 0; i < size; i++) {
                    int[] cur = qm.poll();
                    if (!check(cur[0], cur[1], z, step)) {
                        continue;
                    }
                    for (int[] f : fs) {
                        int x = cur[0] + f[0];
                        int y = cur[1] + f[1];
                        if (!check(x, y, z, step)) {
                            continue;
                        }
                        if (x >= 0 && x < n && y >= 0 && y < m && map[x][y] != 'X') {
                            if (st[x][y] == 2) {
                                out.println(step);
                                return;
                            }
                            if (st[x][y] == 0) {
                                st[x][y] = 1;
                                qm.add(new int[]{x, y});
                            }
                        }
                    }
                }
            }
            int size = qg.size();
            for (int i = 0; i < size; i++) {
                int[] cur = qg.poll();
                if (!check(cur[0], cur[1], z, step)) {
                    continue;
                }
                for (int[] f : fs) {
                    int x = cur[0] + f[0];
                    int y = cur[1] + f[1];
                    if (!check(x, y, z, step)) {
                        continue;
                    }
                    if (x >= 0 && x < n && y >= 0 && y < m && map[x][y] != 'X') {
                        if (st[x][y] == 1) {
                            out.println(step);
                            return;
                        }
                        if (st[x][y] == 0) {
                            st[x][y] = 2;
                            qg.add(new int[]{x, y});
                        }
                    }
                }
            }
        }
        out.println(-1);

    }

    private boolean check(int x, int y, int[][] z, int step) {
        for (int[] g : z) {
            if (Math.abs(x - g[0]) + Math.abs(y - g[1]) <= step * 2) {
                return false;
            }
        }
        return true;
    }
}
