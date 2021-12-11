package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamThemUp {
    int n;
    int N = 105;
    boolean[][] map = new boolean[N][N];
    int[] color = new int[N];
    int[] id = new int[N];
    int cnt = 0;
    int[][] scc = new int[N][2];
    boolean[][] f = new boolean[N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i <= n; i++) {
            Arrays.fill(map[i], true);
        }

        for (int i = 1; i <= n; i++) {
            map[i][i] = false;
            while (true) {
                int j = in.nextInt();
                if (j == 0) {
                    break;
                }
                map[i][j] = false;
            }
        }

        if (!check()) {
            out.println("No solution");
            return;
        }
        for (int i = 1; i <= n; i++) {
            scc[id[i]][color[i]]++;
        }

        f[0][0] = true;

        for (int i = 1; i <= cnt; i++) {
            for (int j = 0; j <= n; j++) {
                if (f[i - 1][j]) {
                    for (int k = 0; k < 2; k++) {
                        f[i][j + scc[i][k]] = true;
                    }
                }
            }
        }
        int res = 0;
        for (int i = n / 2; i <= n - 1; i++) {
            if (f[cnt][i]) {
                res = i;
                break;
            }
        }

        int idx = cnt;
        List<Integer> list = new ArrayList<>();
        while (idx > 0) {
            if (res - scc[idx][0] >= 0 && f[idx - 1][res - scc[idx][0]]) {
                list.add(0, 0);
                res -= scc[idx][0];
            } else {
                list.add(0, 1);
                res -= scc[idx][1];
            }
            idx--;
        }

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (list.get(id[i] - 1) == color[i]) {
                list1.add(i);
            } else {
                list2.add(i);
            }
        }
        out.print(list1.size());
        for (Integer integer : list1) {
            out.print(" " + integer);
        }
        out.println();
        out.print(list2.size());
        for (Integer integer : list2) {
            out.print(" " + integer);
        }
        out.println();
    }

    boolean check() {
        Arrays.fill(color, -1);
        boolean flag = true;
        for (int i = 1; i <= n; i++) {
            if (color[i] == -1) {
                cnt++;
                if (!dfs(i, 0)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    boolean dfs(int u, int c) {
        color[u] = c;
        id[u] = cnt;
        for (int j = 1; j <= n; j++) {
            if (map[u][j] || map[j][u]) {
                if (color[j] == -1) {
                    if (!dfs(j, 1 - c)) {
                        return false;
                    }
                } else if (color[j] == c) {
                    return false;
                }
            }
        }
        return true;
    }

}
