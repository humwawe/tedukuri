package ox38;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TheHomeOfMungBeanFrog {
    int N = (int) (1e5 + 10);
    int M = 2 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] cnt = new int[N];
    Double[] memo = new Double[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        int n = in.nextInt();
        int m = in.nextInt();
        while (m-- > 0) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            cnt[a]++;
        }
        out.printf("%.2f", dfs(1));

    }

    private double dfs(int u) {
        if (memo[u] != null) {
            return memo[u];
        }
        double res = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            res += (dfs(j) + w[i]) / cnt[u];
        }
        return memo[u] = res;
    }

    private void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
