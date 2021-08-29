package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class VisionStone {
    int N = (int) (5e5 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    long[] dist = new long[N];
    int[] d = new int[N];
    int T = 18;
    int[][] f = new int[N][T];
    int[] dfn = new int[N];
    int n, m, num;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }
        dfs(1);
        bfs();
        TreeSet<Integer> treeSet = new TreeSet<>(Comparator.comparingInt(x -> dfn[x]));
        long res = 0;
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            char op = in.nextCharacter();
            if (op == '+') {
                int x = in.nextInt();
                if (treeSet.isEmpty()) {
                    res = 0;
                } else if (treeSet.size() == 1) {
                    res = 2 * distPoint(x, treeSet.first());
                } else {
                    Integer lower = treeSet.lower(x);
                    if (lower == null) {
                        lower = treeSet.last();
                    }
                    Integer higher = treeSet.higher(x);
                    if (higher == null) {
                        higher = treeSet.first();
                    }
                    res += distPoint(lower, x) + distPoint(x, higher) - distPoint(lower, higher);
                }
                treeSet.add(x);
            } else if (op == '-') {
                int x = in.nextInt();
                treeSet.remove(x);
                if (treeSet.size() <= 1) {
                    res = 0;
                } else {
                    Integer lower = treeSet.lower(x);
                    if (lower == null) {
                        lower = treeSet.last();
                    }
                    Integer higher = treeSet.higher(x);
                    if (higher == null) {
                        higher = treeSet.first();
                    }
                    res -= distPoint(lower, x) + distPoint(x, higher) - distPoint(lower, higher);
                }
            } else {
                out.println(res / 2);
            }
        }

    }

    private long distPoint(int x, int y) {
        return dist[x] + dist[y] - 2 * dist[lca(x, y)];
    }

    private int lca(int a, int b) {
        if (d[a] < d[b]) {
            int t = a;
            a = b;
            b = t;
        }
        // 走到统一层
        for (int i = T - 1; i >= 0; i--) {
            if (d[f[a][i]] >= d[b]) {
                a = f[a][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = T - 1; i >= 0; i--) {
            if (f[a][i] != f[b][i]) {
                a = f[a][i];
                b = f[b][i];
            }
        }
        return f[a][0];
    }

    void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (d[j] == 0) {
                    d[j] = d[u] + 1;
                    f[j][0] = u;
                    for (int k = 1; k < T; k++) {
                        f[j][k] = f[f[j][k - 1]][k - 1];
                    }
                    queue.add(j);
                }
            }
        }
    }

    private void dfs(int u) {
        dfn[u] = ++num;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (dfn[j] == 0) {
                dist[j] = dist[u] + w[i];
                dfs(j);
            }
        }
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
