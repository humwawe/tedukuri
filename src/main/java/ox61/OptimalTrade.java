package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class OptimalTrade {
    int N = 100010, M = 2000010;
    int[] h = new int[N];
    int[] rh = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] price = new int[M];
    int idx;
    int[] dmin = new int[N];
    int[] dmax = new int[N];
    boolean[] vis = new boolean[N];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        Arrays.fill(rh, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= n; i++) {
            price[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(h, a, b);
            add(rh, b, a);
            if (c == 2) {
                add(h, b, a);
                add(rh, a, b);
            }
        }
        spfaMin(1);
        spfaMax(n);
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dmax[i] - dmin[i]);
        }
        out.println(res);
    }

    void spfaMin(int start) {
        Arrays.fill(vis, false);
        Arrays.fill(dmin, 0x3f3f3f3f);
        dmin[start] = price[start];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        vis[start] = true;
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dmin[j] > Math.min(dmin[t], price[j])) {
                    dmin[j] = Math.min(dmin[t], price[j]);
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }
    }

    void spfaMax(int start) {
        Arrays.fill(vis, false);
        dmax[start] = price[start];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        vis[start] = true;
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = rh[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dmax[j] < Math.max(dmax[t], price[j])) {
                    dmax[j] = Math.max(dmax[t], price[j]);
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }
    }

    void add(int[] h, int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
