package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BribingFIPA {
    int N = 205;
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    int[] w = new int[N];
    boolean[] r = new boolean[N];
    int idx;
    int n, m;
    int start;
    int[][] f = new int[N][N];
    int[] size = new int[N];
    int inf = (int) 1e8;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        Map<String, Integer> map = new HashMap<>();
        Arrays.fill(h, -1);
        Arrays.fill(r, false);
        Arrays.fill(size, 0);
        for (int i = 0; i < N; i++) {
            Arrays.fill(f[i], inf);
        }
        start = 1;
        idx = 0;
        w[0] = inf;
        for (int i = 0; i < n; i++) {
            String s = in.readLine();
            String[] s1 = s.split(" ");
            if (!map.containsKey(s1[0])) {
                map.put(s1[0], start++);
            }
            int a = map.get(s1[0]);
            int v = Integer.parseInt(s1[1]);
            w[a] = v;
            for (int j = 2; j < s1.length; j++) {
                if (!map.containsKey(s1[j])) {
                    map.put(s1[j], start++);
                }
                int b = map.get(s1[j]);
                add(a, b);
                r[b] = true;
            }
        }
        for (int j = 1; j <= n; j++) {
            if (!r[j]) {
                add(0, j);
            }
        }
        dfs(0);
        out.println(f[0][m]);
    }

    private void dfs(int u) {
        f[u][0] = 0;
        size[u] = 1;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            dfs(j);
            size[u] += size[j];
        }
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            for (int k = size[u]; k >= 0; k--) {
                for (int l = size[j]; l >= 0; l--) {
                    if (k >= l) {
                        f[u][k] = Math.min(f[u][k], f[u][k - l] + f[j][l]);
                    }
                }
            }
        }
        if (u != 0) {
            for (int i = 0; i <= size[u]; i++) {
                f[u][i] = Math.min(f[u][i], w[u]);
            }
        }
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
