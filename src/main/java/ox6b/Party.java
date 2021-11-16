package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Party {
    int N = 500005;
    int[] h = new int[N];
    int[] e = new int[N * 2];
    int[] ne = new int[N * 2];
    int idx;
    int[] d = new int[N];
    int T = 19;
    int[][] f = new int[N][T];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        bfs();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int x = lca(a, b);
            int y = lca(a, c);
            int z = lca(b, c);

            if (d[y] >= d[x] && d[y] >= d[z]) {
                x = y;
                y = z;
            } else if (d[z] >= d[x] && d[z] >= d[y]) {
                x = z;
            }
            int sum = d[a] + d[b] + d[c] - 3 * d[y] - (d[x] - d[y]);
            out.println(x + " " + sum);
        }

    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
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
}
