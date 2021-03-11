package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Race {
    int n, k;
    int[] h;
    int[] e;
    int[] ne;
    int[] w;
    int inf = 0x3f3f3f3f;
    int idx;
    int root;
    int[] size;
    int[] dp;
    int sum;
    boolean[] vis;
    int res;
    int[] dis;
    int[] dep;
    Map<Integer, Integer> map;
    Map<Integer, Integer> tmpMap;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        h = new int[n];
        Arrays.fill(h, -1);
        idx = 0;
        e = new int[2 * n];
        ne = new int[2 * n];
        w = new int[2 * n];
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int v = in.nextInt();
            add(a, b, v);
            add(b, a, v);
        }
        size = new int[n];
        dp = new int[n];
        vis = new boolean[n];
        sum = n;
        root = -1;
        getRoot(0, 0);
        res = inf;

        dis = new int[n];
        dep = new int[n];
        dfs(root);

        if (res == inf) {
            out.println(-1);
        } else {
            out.println(res);
        }
    }

    private void cal(int u) {
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            dep[j] = dep[u] + 1;
            dis[j] = dis[u] + w[i];
            tmpMap = new HashMap<>();
            getDis(j, j);
            for (Integer integer : tmpMap.keySet()) {
                map.put(integer, Math.min(tmpMap.get(integer), map.getOrDefault(integer, inf)));
            }
        }
    }

    private void getDis(int u, int pre) {
        if (dep[u] >= res) {
            return;
        }
        if (tmpMap.containsKey(dis[u])) {
            tmpMap.put(dis[u], Math.min(dep[u], tmpMap.get(dis[u])));
        } else {
            tmpMap.put(dis[u], dep[u]);
        }
        if (map.containsKey(k - dis[u])) {
            res = Math.min(res, dep[u] + map.get(k - dis[u]));
        }
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == pre || vis[j]) {
                continue;
            }
            dep[j] = dep[u] + 1;
            dis[j] = dis[u] + w[i];
            if (dis[j] <= k) {
                getDis(j, u);
            }
        }
    }

    private void dfs(int u) {
        vis[u] = true;
        dep[u] = 0;
        dis[u] = 0;
        map = new HashMap<>();
        map.put(0, 0);
        cal(u);
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                continue;
            }
            sum = size[j];
            root = -1;
            getRoot(j, u);
            dfs(root);
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
