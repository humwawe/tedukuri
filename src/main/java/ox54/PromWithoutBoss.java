package ox54;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class PromWithoutBoss {
    int N = 6010;
    int[] w = new int[N];
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    int idx = 0;
    int[][] f = new int[N][2];
    boolean[] hasFa = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }
        Arrays.fill(h, -1);
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(b, a);
            hasFa[a] = true;
        }
        int root = 1;
        while (hasFa[root]) {
            root++;
        }
        dfs(root);
        out.println(Math.max(f[root][0], f[root][1]));
    }

    private void dfs(int u) {
        f[u][0] = 0;
        f[u][1] = w[u];
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            dfs(j);
            f[u][1] += f[j][0];
            f[u][0] += Math.max(f[j][1], f[j][0]);
        }
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
