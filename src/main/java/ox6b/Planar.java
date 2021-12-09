package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

public class Planar {
    int N = (int) (2e2 + 5);
    int M = (int) (1e4 + 5);
    int[] id = new int[N];
    int[] p = new int[M * 2];
    int[][] e = new int[M][2];

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    void union(int a, int b) {
        if (find(a) == find(b)) {
            return;
        }
        p[find(a)] = find(b);
    }

    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            e[i][0] = in.nextInt();
            e[i][1] = in.nextInt();
            p[i] = i;
            p[i + m] = i + m;
        }

        for (int i = 0; i < n; i++) {
            int k = in.nextInt();
            id[k] = i;
        }
        if (m > 3 * n - 6) {
            out.println("NO");
            return;
        }
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if (e[i][0] == e[j][0] || e[i][0] == e[j][1] || e[i][1] == e[j][0] || e[i][1] == e[j][1]) {
                    continue;
                }
                if (dir(e[i][0], e[i][1], e[j][0]) == dir(e[i][0], e[i][1], e[j][1])) {
                    continue;
                }
                union(i, j + m);
                union(j, i + m);
            }
        }
        for (int i = 0; i < m; i++) {
            if (find(i) == find(i + m)) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }

    private boolean dir(int a, int b, int c) {
        if (id[a] > id[b]) {
            int t = a;
            a = b;
            b = t;
        }
        return id[a] < id[c] && id[b] > id[c];
    }

}
