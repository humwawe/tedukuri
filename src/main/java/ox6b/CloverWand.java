package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class CloverWand {
    int N = 20;

    int n, m;
    int[] a = new int[N];
    int[] s = new int[1 << 16];
    int lim;
    List<Edge> list = new ArrayList<>();
    int[] c = new int[1 << 16];
    int[] f = new int[1 << 16];
    int inf = 0x3f3f3f3f;
    int[] fa = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        lim = 1 << n;
        for (int i = 0; i < lim; i++) {
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) {
                    s[i] += a[j];
                }
            }

        }
        for (int i = 0; i < m; i++) {
            list.add(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
        }
        list.sort(Comparator.comparingDouble(x -> x.d));

        for (int i = 0; i < lim; i++) {
            if (s[i] == 0) {
                c[i] = kruskal(i);
            }
        }

        Arrays.fill(f, inf);
        f[0] = 0;
        for (int i = 0; i < lim; i++) {
            if (s[i] != 0) {
                continue;
            }
            for (int j = 0; j < lim; j++) {
                if ((i & j) == 0) {
                    f[i | j] = Math.min(f[i | j], f[i] + c[j]);
                }
            }
        }
        if (f[lim - 1] == inf) {
            out.println("Impossible");
        } else {
            out.println(f[lim - 1]);
        }
    }

    private int kruskal(int st) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (((st >> i) & 1) == 1) {
                fa[i] = i;
            }
        }
        for (int i = 0; i < m; i++) {
            int p = list.get(i).x;
            int q = list.get(i).y;
            if (((st >> p) & 1) != 1 || ((st >> q) & 1) != 1) {
                continue;
            }
            int x = find(p);
            int y = find(q);
            if (x != y) {
                fa[x] = y;
                res += list.get(i).d;
            }
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (((st >> i) & 1) == 1) {
                set.add(find(fa[i]));
            }
        }
        if (set.size() > 1) {
            return inf;
        }
        return res;
    }

    int find(int x) {
        if (fa[x] != x) {
            fa[x] = find(fa[x]);
        }
        return fa[x];
    }

    class Edge {
        int x;
        int y;
        int d;

        public Edge(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}
