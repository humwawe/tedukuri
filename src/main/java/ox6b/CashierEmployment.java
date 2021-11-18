package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class CashierEmployment {
    int N = 25, M = 100;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] num = new int[N];
    int[] p = new int[N];
    int n;
    int[] dist = new int[N];
    int[] cnt = new int[N];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(num, 0);
        for (int i = 1; i <= 24; i++) {
            p[i] = in.nextInt();
        }
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            int k = in.nextInt() + 1;
            num[k]++;
        }
        int l = 0, r = n + 1;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (spfa(mid)) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        if (r == n + 1) {
            out.println("No Solution");
        } else {
            out.println(l);
        }
    }


    boolean spfa(int s24) {
        Arrays.fill(h, -1);
        idx = 0;
        Arrays.fill(dist, -0x3f3f3f3f);
        Arrays.fill(cnt, 0);
        Arrays.fill(vis, false);
        for (int i = 1; i <= 24; i++) {
            add(i - 1, i, 0);
            add(i, i - 1, -num[i]);
            if (i > 8) {
                add(i - 8, i, p[i]);
            } else {
                add(i + 16, i, -s24 + p[i]);
            }
        }
        add(0, 24, s24);
        add(24, 0, -s24);
        dist[0] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] < dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    cnt[j] = cnt[t] + 1;
                    if (cnt[j] >= 25) {
                        return true;
                    }
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }

        return false;
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
