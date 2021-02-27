package ox45;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class Tree {
    int n, k;
    int[] h;
    int[] e;
    int[] ne;
    int[] w;
    int idx;
    int root;
    int[] size;
    int[] dp;
    boolean[] vis;
    int[] color;
    int sum;
    int res;
    int[] dis;
    Integer[] help;
    int cnt;
    int[] f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        if (n == 0 && k == 0) {
            return;
        }
        h = new int[n];
        Arrays.fill(h, -1);
        idx = 0;
        e = new int[2 * n];
        ne = new int[2 * n];
        w = new int[2 * n];
        size = new int[n];
        dp = new int[n];
        vis = new boolean[n];
        res = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int v = in.nextInt();
            add(a, b, v);
            add(b, a, v);
        }

        sum = n;
        root = -1;
        getRoot(0, 0);

        dis = new int[n];
        help = new Integer[n];
        f = new int[n];
        color = new int[n];
        dfs(root);
        out.println(res);
    }

    private void dfs(int u) {
        vis[u] = true;
        res += cal(u);
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            sum = size[j];
            root = -1;
            getRoot(j, u);
            dfs(j);
        }
    }

    private int cal(int u) {
        dis[u] = 0;
        cnt = 0;
        color[u] = u;
        f[color[u]] = 1;
        help[cnt++] = u;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            color[j] = j;
            dis[j] = w[i];
            f[color[j]] = 0;
            getDis(j, j);
        }
        Arrays.sort(help, 0, cnt, Comparator.comparingInt(x -> dis[x]));
        int res = 0;
        int j = cnt - 1;
        for (int i = 0; i < j; ) {
            if (dis[help[i]] + dis[help[j]] <= k) {
                res += j - i - f[color[help[i]]] + 1;
                f[color[help[i]]]--;
                i++;
            } else {
                f[color[help[j]]]--;
                j--;
            }
        }
        return res;
    }

    private void getDis(int u, int pre) {
        help[cnt++] = u;
        color[u] = color[pre];
        f[color[u]]++;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == pre || vis[j]) {
                continue;
            }
            dis[j] = dis[u] + w[i];
            getDis(j, u);
        }
    }

    private void getRoot(int u, int pre) {
        size[u] = 1;
        dp[u] = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == pre || vis[j]) {
                continue;
            }
            getRoot(j, u);
            size[u] += size[j];
            dp[u] = Math.max(dp[u], size[j]);
        }
        dp[u] = Math.max(dp[u], sum - size[u]);
        if (root == -1 || dp[u] < dp[root]) {
            root = u;
        }
    }

    void add(int a, int b, int v) {
        e[idx] = b;
        w[idx] = v;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
