package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class StrategicGame {
    int N = 1505;
    int[] h = new int[N];
    int[] e = new int[N * 2];
    int[] ne = new int[N * 2];
    int idx;
    int n;
    int[] g, f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        g = new int[n];
        f = new int[n];
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 0; i < n; i++) {
            String s = in.nextString();
            int j = s.indexOf(":");
            int a = Integer.parseInt(s.substring(0, j));
            int k = s.indexOf(")");
            int m = Integer.parseInt(s.substring(j + 2, k));
            for (int l = 0; l < m; l++) {
                int b = in.nextInt();
                add(a, b);
                add(b, a);
            }
        }
        dfs(0, -1);
        out.println(Math.min(g[0], f[0]));
    }

    private void dfs(int u, int p) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == p) {
                continue;
            }
            dfs(j, u);
            f[u] += g[j];
            g[u] += Math.min(g[j], f[j]);
        }
        g[u] += 1;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
