package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OptimalHighSpeedRailRing {
    int N = 50005;
    int M = N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    double[] w = new double[M];
    double[] dist = new double[N];
    boolean[] vis = new boolean[N];
    int[] cnt = new int[N];
    int idx;
    int n, m;
    Map<String, Integer> map = new HashMap<>();
    double eps = 1e-2;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            String s = in.nextString();
            String[] ps = s.split("-");
            double value = 0;
            for (int j = 0; j < ps.length; j++) {
                value += helper(ps[j]);
            }
            if (!map.containsKey(ps[0])) {
                map.put(ps[0], ++n);
            }
            if (!map.containsKey(ps[ps.length - 1])) {
                map.put(ps[ps.length - 1], ++n);
            }
            add(map.get(ps[0]), map.get(ps[ps.length - 1]), value);
        }
        double l = 0;
        double r = 2147483648f;
        while (r - l > eps) {
            double mid = (r + l) / 2;
            if (check(mid)) {
                l = mid;
            } else {
                r = mid;
            }
        }
        if (check(l)) {
            out.println(Math.round(l));
        } else {
            out.println(-1);
        }
    }

    boolean check(double mid) {
        Arrays.fill(vis, false);
        for (int i = 1; i <= n; i++) {
            if (dfs(i, mid)) {
                return true;
            }
        }
        return false;
    }

    boolean dfs(int u, double mid) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            double c = mid - w[i];
            if (dist[j] > dist[u] + c) {
                dist[j] = dist[u] + c;
                if (vis[j] || dfs(j, mid)) {
                    return true;
                }
            }
        }
        vis[u] = false;
        return false;
    }

    void add(int a, int b, double c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    private int helper(String p) {
        switch (p.charAt(0)) {
            case 'S':
                return 1000;
            case 'G':
                return 500;
            case 'D':
                return 300;
            case 'T':
                return 200;
            default:
                return 150;
        }
    }
}
