package ox69;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MuddyFields {
    int N = 55;
    int M = N * N;
    int[] h = new int[M];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int[] match = new int[M];
    boolean[] vis = new boolean[M];
    int n, m;
    char[][] mat = new char[N][N];
    int[][][] a = new int[N][N][2];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = in.nextCharacter();
            }
        }
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == '*') {
                    a[i][j][0] = num;
                } else {
                    num++;
                }
            }
            num++;
        }
        int num2 = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[j][i] == '*') {
                    a[j][i][1] = num2;
                } else {
                    num2++;
                }
            }
            num2++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == '*') {
                    add(a[i][j][0], a[i][j][1]);
                }
            }
        }
        int res = 0;
        for (int i = 1; i <= num; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        out.println(res);
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
