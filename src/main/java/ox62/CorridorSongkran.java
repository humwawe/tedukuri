package ox62;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class CorridorSongkran {
    int N = (int) (1e4 + 100);
    int[] p = new int[N];
    long[] size = new long[N];
    int n;
    Edge[] edges = new Edge[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            edges[i] = new Edge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        Arrays.sort(edges, 0, n - 1, Comparator.comparingInt(a -> a.w));
        for (int i = 1; i <= n; i++) {
            p[i] = i;
            size[i] = 1;
        }

        int res = 0;
        for (int i = 0; i < n - 1; i++) {
            int a = edges[i].a;
            int b = edges[i].b;
            int w = edges[i].w;
            a = find(a);
            b = find(b);
            if (a != b) {
                p[a] = b;
                res += (size[a] * size[b] - 1) * (w + 1);
                size[b] += size[a];
            }
        }
        out.println(res);
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    class Edge {
        int a;
        int b;
        int w;

        public Edge(int a, int b, int w) {
            this.a = a;
            this.b = b;
            this.w = w;
        }
    }
}


