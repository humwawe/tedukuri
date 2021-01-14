package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class TargetSudoku {
    int N = 9;
    int M = 1 << N;

    int[] ones = new int[M];
    int[] map = new int[M];
    int[] row = new int[N];
    int[] col = new int[N];
    int[][] cell = new int[3][3];
    int[][] g = new int[N][N];

    int res = -1;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        for (int i = 0; i < N; i++) {
            map[1 << i] = i;
        }
        for (int i = 0; i < M; i++) {
            for (int j = i; j != 0; j -= lowbit(j)) {
                ones[i]++;
            }
        }
        for (int i = 0; i < 9; i++) {
            row[i] = col[i] = cell[i / 3][i % 3] = M - 1;
        }
        int cnt = 0;
        int score = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = in.nextInt();
                if (x != 0) {
                    draw(i, j, x);
                    score += getScore(i, j, x);
                } else {
                    cnt++;
                }
            }
        }
        dfs(cnt, score);
        out.println(res);

    }

    private void dfs(int cnt, int score) {
        if (cnt == 0) {
            res = Math.max(res, score);
            return;
        }
        int minv = 10;
        int x = 0, y = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (g[i][j] == 0) {
                    int t = ones[get(i, j)];
                    if (t < minv) {
                        minv = ones[get(i, j)];
                        x = i;
                        y = j;
                    }
                }
            }
        }

        for (int i = get(x, y); i != 0; i -= lowbit(i)) {
            int t = map[lowbit(i)] + 1;
            draw(x, y, t);
            dfs(cnt - 1, score + getScore(x, y, t));
            draw(x, y, -t);
        }

    }

    void draw(int x, int y, int t) {
        int s = 1;
        if (t > 0) {
            g[x][y] = t;
        } else {
            s = -1;
            t = -t;
            g[x][y] = 0;
        }

        t--;
        row[x] -= (1 << t) * s;
        col[y] -= (1 << t) * s;
        cell[x / 3][y / 3] -= (1 << t) * s;
    }

    int lowbit(int x) {
        return x & -x;
    }

    int getScore(int x, int y, int t) {
        return (Math.min(Math.min(x, 8 - x), Math.min(y, 8 - y)) + 6) * t;
    }

    int get(int x, int y) {
        return row[x] & col[y] & cell[x / 3][y / 3];
    }
}
