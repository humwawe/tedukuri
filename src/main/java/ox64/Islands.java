package ox64;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Islands {
    int N = (int) (1e6 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] dfn = new int[N];
    int[] fa = new int[N];
    int[] s = new int[M];
    boolean[] v = new boolean[N];
    int n, p, num;
    long[] dist = new long[N];
    long[] sum = new long[M];
    long res, resAll = 0;

    int[] q = new int[M];


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        for (int i = 1; i <= n; i++) {
            int b = in.nextInt();
            int c = in.nextInt();
            add(i, b, c);
            add(b, i, c);
        }
        for (int u = 1; u <= n; u++) {
            if (dfn[u] == 0) {
                p = 0;
                res = 0;
                dfs(u);
                for (int i = 1; i <= p; i++) {
                    dp(s[i]);
                }

//                Deque<Integer> queue = new ArrayDeque<>();
//                for (int i = 1; i <= 2 * p; i++) {
//                    while (!queue.isEmpty() && queue.getFirst() <= i - p) {
//                        queue.pollFirst();
//                    }
//                    if (!queue.isEmpty()) {
//                        res = Math.max(res, dist[s[i]] + dist[s[queue.getFirst()]] + sum[i] - sum[queue.getFirst()]);
//                    }
//                    while (!queue.isEmpty() && dist[s[i]] - sum[i] >= dist[s[queue.getLast()]] - sum[queue.getLast()]) {
//                        queue.pollLast();
//                    }
//                    queue.addLast(i);
//                }
                int l = 1, r = 0;
                for (int i = 1; i <= 2 * p; i++) {
                    while (l <= r && q[l] <= i - p) {
                        l++;
                    }
                    if (l <= r) {
                        res = Math.max(res, dist[s[i]] + dist[s[q[l]]] + sum[i] - sum[q[l]]);
                    }
                    while (l <= r && dist[s[q[r]]] - sum[q[r]] <= dist[s[i]] - sum[i]) {
                        r--;
                    }
                    q[++r] = i;
                }
                resAll += res;
            }
        }
        out.println(resAll);
    }

    void dp(int u) {
        v[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (v[j]) {
                continue;
            }
            dp(j);
            res = Math.max(res, dist[u] + dist[j] + w[i]);
            dist[u] = Math.max(dist[u], dist[j] + w[i]);
        }
    }

    private void dfs(int u) {
        dfn[u] = ++num;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                fa[j] = i;
                dfs(j);
            } else if ((i ^ 1) != fa[u] & dfn[j] > dfn[u]) {
                getCycle(u, j, w[i]);
            }
        }
    }

    // s1, s2, ..., sp即为环上点
    private void getCycle(int u, int j, int z) {
        sum[1] = z;
        while (j != u) {
            s[++p] = j;
            sum[p + 1] = w[fa[j]];
            j = e[fa[j] ^ 1];
        }
        s[++p] = u;
        for (int i = 1; i <= p; i++) {
            v[s[i]] = true;
            s[p + i] = s[i];
            sum[p + i] = sum[i];
        }
        for (int i = 1; i <= 2 * p; i++) {
            sum[i] += sum[i - 1];
        }
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
