package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class TelephoneLines {

    int N = 1005;
    int M = 10005 * 2;
    int n, m, k;
    int[] h = new int[N];
    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int inf = (int) 1e8;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int v = in.nextInt();
            add(a, b, v);
            add(b, a, v);
        }
        int l = 0;
        int r = (int) (1e6 + 1);
        while (l < r) {
            int mid = l + r >> 1;
            if (check(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (l > 1e6) {
            out.println(-1);
            return;
        }
        out.println(l);
    }

    private boolean check(int mid) {
        Arrays.fill(dist, inf);
        Arrays.fill(vis, false);
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        dist[1] = 0;

        while (!deque.isEmpty()) {
            Integer u = deque.pollFirst();
            if (vis[u]) {
                continue;
            }
            vis[u] = true;
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (vis[j]) {
                    continue;
                }
                boolean x = w[i] > mid;
                int tmp = x ? 1 : 0;
                if (dist[j] > dist[u] + tmp) {
                    dist[j] = dist[u] + tmp;
                    if (x) {
                        deque.addLast(j);
                    } else {
                        deque.addFirst(j);
                    }
                }
            }
        }
        return dist[n] <= k;
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

}
