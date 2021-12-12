package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class PlaceTheRobots {
    int n, m;
    int N = 55;
    char[][] c = new char[N][N];
    int[][] x = new int[N][N];
    int[][] y = new int[N][N];

    int M = 2505;
    // 注意初始化图的时候，先将head数组置为-1，idx置位0
    int[] h = new int[M];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                c[i][j] = in.nextCharacter();
            }
        }
        for (int i = 1; i <= n; i++) {
            Arrays.fill(x[i], 0);
            Arrays.fill(y[i], 0);
        }
        int no = 1;
        for (int i = 1; i <= n; i++) {
            int j = 1;
            while (j <= m) {
                if (c[i][j] == '#') {
                    no++;
                } else {
                    x[i][j] = no;
                }
                j++;
            }
            no++;
        }
        n1 = no;
        no = 1;
        for (int i = 1; i <= m; i++) {
            int j = 1;
            while (j <= n) {
                if (c[j][i] == '#') {
                    no++;
                } else {
                    y[j][i] = no;
                }
                j++;
            }
            no++;
        }
        n2 = no;
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (c[i][j] == 'o') {
                    if (x[i][j] > 0 && y[i][j] > 0) {
                        add(x[i][j], y[i][j]);
                    }
                }
            }
        }
        Arrays.fill(match, 0);
        out.println("Case :" + testNumber);
        out.println(match());
    }

    int n1, n2;
    int[] match = new int[M];
    boolean[] vis = new boolean[M];

    int match() {
        int res = 0;
        for (int i = 1; i <= n1; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        return res;
    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }
            }
        }
        return false;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
