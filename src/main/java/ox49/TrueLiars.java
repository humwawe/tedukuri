package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

public class TrueLiars {
    int N = 1205;
    int n, m;
    int p1, p2;
    int[] p = new int[N];
    int[] size = new int[N];
    int[] vis = new int[N];
    int[] a = new int[N / 2];
    int[] b = new int[N / 2];
    int tot;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        m = in.nextInt();
        p1 = in.nextInt();
        p2 = in.nextInt();
        n = p1 + p2;
        if (m == 0 && n == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            p[i] = i;
            p[i + n] = i + n;
            vis[i] = 0;
            vis[i + n] = 0;
            size[i] = 1;
            size[i + n] = 0;
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            String s = in.nextString();
            if (s.equals("yes")) {
                union(a, b);
                union(a + n, b + n);
            } else {
                union(a, b + n);
                union(a + n, b);
            }
        }
        tot = 0;
        for (int i = 1; i <= n; i++) {
            int pi = find(i);
            if (pi != i) {
                continue;
            }
            vis[pi] = vis[pi + n] = ++tot;
            a[tot] = size[pi];
            b[tot] = size[pi + n];
        }
        int[][] dp = new int[N / 2][N / 2];
        dp[0][0] = 1;
        for (int i = 1; i <= tot; i++) {
            for (int j = Math.min(a[i], b[i]); j <= p1; ++j) {
                if (j >= a[i]) {
                    dp[i][j] += dp[i - 1][j - a[i]];
                }
                if (j >= b[i]) {
                    dp[i][j] += dp[i - 1][j - b[i]];
                }
            }
        }
        if (dp[tot][p1] != 1) {
            out.println("no");
            return;
        }
        for (int i = tot; i > 0; i--) {
            if (p1 - a[i] >= 0 && dp[i - 1][p1 - a[i]] != 0) {
                p1 -= a[i];
                a[i] = -1;
            } else if (p1 - b[i] >= 0 && dp[i - 1][p1 - b[i]] != 0) {
                p1 -= b[i];
                a[i] = -2;
            }
        }

        for (int i = 1; i <= n; i++) {
            int pi = find(i);
            if (vis[pi] != 0) {
                if (pi > n && a[vis[pi]] == -2) {
                    out.println(i);
                } else if (pi <= n && a[vis[pi]] == -1) {
                    out.println(i);
                }
            }
        }
        out.println("end");

    }

    void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) {
            return;
        }
        p[b] = a;
        size[a] += size[b];
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
