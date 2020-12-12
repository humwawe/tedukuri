package ox23;

import common.io.InputReader;
import common.io.OutputWriter;

public class Sudoku {
    int N = 16;
    int[] map = new int[1 << N];
    int[] ones = new int[1 << N];
    int[][] state = new int[N][N];
    char[][] str = new char[N][N];

    int[][][] bstate1 = new int[N * N + 1][N][N];
    int[][][] bstate2 = new int[N * N + 1][N][N];
    char[][][] bstr = new char[N * N + 1][N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        for (int i = 0; i < N; i++) {
            map[1 << i] = i;
        }
        for (int i = 0; i < 1 << N; i++) {
            for (int j = i; j > 0; j -= lowbit(j)) {
                ones[i]++;
            }
        }
        str[0] = in.nextString().toCharArray();
        if (str[0].length > 0) {
            for (int i = 1; i < N; i++) {
                str[i] = in.nextString().toCharArray();
            }
        } else {
            return;
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                state[i][j] = (1 << N) - 1;
            }
        }

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (str[i][j] != '-') {
                    draw(i, j, str[i][j] - 'A');
                } else {
                    cnt++;
                }
            }
        }

        dfs(cnt);

        for (int i = 0; i < N; i++) {
            out.println(str[i]);
        }
        out.println();
    }

    private boolean dfs(int cnt) {
        if (cnt == 0) {
            return true;
        }

        int kcnt = cnt;
        copy(bstate1[kcnt], state);
        copy(bstr[kcnt], str);

        // 每个空格，如果不能填则返回false；如果只有一个选项，则直接填上
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (str[i][j] == '-') {
                    if (state[i][j] == 0) {
                        copy(state, bstate1[kcnt]);
                        copy(str, bstr[kcnt]);
                        return false;
                    }

                    if (ones[state[i][j]] == 1) {
                        draw(i, j, map[state[i][j]]);
                        cnt--;
                    }
                }
            }
        }

        // 每一行，如果某个字母不能填，则返回false；如果某个字母只有一种填法，则直接填
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1 << N) - 1;
            int drawn = 0;
            for (int j = 0; j < N; j++) {
                int s = state[i][j];
                sand &= ~(sor & s);
                sor |= s;
                if (str[i][j] != '-') {
                    drawn |= state[i][j];
                }
            }

            if (sor != (1 << N) - 1) {
                copy(state, bstate1[kcnt]);
                copy(str, bstr[kcnt]);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        if ((state[i][k] & t) > 0) {
                            draw(i, k, map[t]);
                            cnt--;
                            break;
                        }
                    }
                }
            }
        }

        // 每一列，如果某个字母不能填，则返回false；如果某个字母只有一种填法，则直接填
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1 << N) - 1;
            int drawn = 0;
            for (int j = 0; j < N; j++) {
                int s = state[j][i];
                sand &= ~(sor & s);
                sor |= s;

                if (str[j][i] != '-') {
                    drawn |= state[j][i];
                }
            }

            if (sor != (1 << N) - 1) {
                copy(state, bstate1[kcnt]);
                copy(str, bstr[kcnt]);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        if ((state[k][i] & t) > 0) {
                            draw(k, i, map[t]);
                            cnt--;
                            break;
                        }
                    }
                }
            }
        }

        // 每个16宫格，如果某个字母不能填，则返回false；如果某个字母只有一种填法，则直接填
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1 << N) - 1;
            int drawn = 0;
            for (int j = 0; j < N; j++) {
                int sx = i / 4 * 4, sy = i % 4 * 4;
                int dx = j / 4, dy = j % 4;
                int s = state[sx + dx][sy + dy];
                sand &= ~(sor & s);
                sor |= s;
                if (str[sx + dx][sy + dy] != '-') {
                    drawn |= state[sx + dx][sy + dy];
                }
            }

            if (sor != (1 << N) - 1) {
                copy(state, bstate1[kcnt]);
                copy(str, bstr[kcnt]);
                return false;
            }
            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        int sx = i / 4 * 4, sy = i % 4 * 4;
                        int dx = k / 4, dy = k % 4;
                        if ((state[sx + dx][sy + dy] & t) > 0) {
                            draw(sx + dx, sy + dy, map[t]);
                            cnt--;
                            break;
                        }
                    }
                }
            }
        }

        if (cnt == 0) {
            return true;
        }

        int x = 0, y = 0, s = 100;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (str[i][j] == '-' && ones[state[i][j]] < s) {
                    s = ones[state[i][j]];
                    x = i;
                    y = j;
                }
            }
        }

        copy(bstate2[kcnt], state);
        for (int i = state[x][y]; i > 0; i -= lowbit(i)) {
            copy(state, bstate2[kcnt]);
            draw(x, y, map[lowbit(i)]);
            if (dfs(cnt - 1)) {
                return true;
            }
        }

        copy(state, bstate1[kcnt]);
        copy(str, bstr[kcnt]);
        return false;

    }

    private void copy(char[][] bak, char[][] source) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, bak[i], 0, source[i].length);
        }
    }

    private void copy(int[][] bak, int[][] source) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, bak[i], 0, source[i].length);
        }

    }

    private int lowbit(int x) {
        return x & -x;
    }

    private void draw(int x, int y, int c) {
        str[x][y] = (char) ('A' + c);
        for (int i = 0; i < N; i++) {
            state[x][i] &= ~(1 << c);
            state[i][y] &= ~(1 << c);
        }
        int sx = x / 4 * 4, sy = y / 4 * 4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[sx + i][sy + j] &= ~(1 << c);
            }
        }
        state[x][y] = 1 << c;
    }
}
