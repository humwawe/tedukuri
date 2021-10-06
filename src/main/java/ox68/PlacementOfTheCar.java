package ox68;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class PlacementOfTheCar {
    int n, m, t;
    int N = 205;
    boolean[][] ban = new boolean[N][N];
    boolean[] vis = new boolean[N];
    int[] match = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        t = in.nextInt();
        for (int i = 1; i <= t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            ban[a][b] = true;
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        out.println(res);
    }

    boolean find(int i) {
        for (int j = 1; j <= m; j++) {
            if (!vis[j] && !ban[i][j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = i;
                    return true;
                }
            }
        }
        return false;
    }
}
