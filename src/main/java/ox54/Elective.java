package ox54;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Elective {
    int N = 310, M = N * 2;
    int[] w = new int[N];
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    int idx = 0;
    int[][] f = new int[N][N];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Arrays.fill(h, -1);
        for (int i = 1; i <= n; i++) {
            int fa = in.nextInt();
            w[i] = in.nextInt();
            add(fa, i);
        }
        m++;
        dfs(0);
        out.println(f[0][m]);
    }

    private void dfs(int u) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            dfs(j);
            // 背包容量
            for (int k = m - 1; k >= 0; k--) {
                // 选几个（体积）
                for (int t = 1; t <= k; t++) {
                    f[u][k] = Math.max(f[u][k], f[j][t] + f[u][k - t]);
                }
            }
        }
        for (int i = m; i > 0; --i) {
            f[u][i] = f[u][i - 1] + w[u];
        }
    }


    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
