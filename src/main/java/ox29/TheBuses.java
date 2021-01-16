package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TheBuses {
    int N = 60 + 5;
    int MAXN = 3600 + 5;
    int n, cnt;
    int[] bus = new int[N];
    int[][] route = new int[MAXN][3];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            bus[t]++;
        }
        cnt = 0;
        for (int i = 0; i < 60; i++) {
            for (int j = i + 1; i + j < 60; j++) {
                if (check(i, j)) {
                    route[cnt][0] = i;
                    route[cnt][1] = j;
                    route[cnt++][2] = (59 - i) / j + 1;
                }
            }
        }
        Arrays.sort(route, (x, y) -> y[2] - x[2]);
        int depth = 0;
        while (!dfs(depth, 0, 0, 0)) {
            depth++;
        }
        out.println(depth);

    }

    private boolean dfs(int depth, int u, int start, int sum) {
        if (depth == u) {
            return sum == n;
        }
        if (route[start][2] * (depth - u) + sum < n) {
            return false;
        }
        for (int i = start; i < cnt; i++) {
            int a = route[i][0];
            int d = route[i][1];
            if (!check(a, d)) {
                continue;
            }
            for (int j = a; j < 60; j += d) {
                bus[j]--;
            }
            if (dfs(depth, u + 1, i, sum + route[i][2])) {
                return true;
            }
            for (int j = a; j < 60; j += d) {
                bus[j]++;
            }
        }
        return false;
    }

    private boolean check(int a, int d) {
        for (int i = a; i < 60; i += d) {
            if (bus[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
