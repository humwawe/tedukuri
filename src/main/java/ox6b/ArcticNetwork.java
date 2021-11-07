package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArcticNetwork {
    int N = 510;
    int[][] a;
    int s, p;
    int[] fa = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        s = in.nextInt();
        p = in.nextInt();
        for (int i = 0; i < p; i++) {
            fa[i] = i;
        }
        a = new int[p][2];
        for (int i = 0; i < p; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < p; j++) {
                if (i == j) {
                    continue;
                }
                list.add(new Edge(i, j, dist(i, j)));
            }
        }
        list.sort(Comparator.comparingDouble(x -> x.d));
        int cnt = 0;
        double res = 0;
        for (Edge edge : list) {
            if (cnt == p - s) {
                out.printf("%.2f", res);
                out.println();
                return;
            }
            int x = find(edge.x);
            int y = find(edge.y);
            if (x != y) {
                fa[x] = y;
                cnt++;
                res = Math.max(res, edge.d);
            }
        }
        out.printf("%.2f", res);
        out.println();
    }

    int find(int x) {
        if (fa[x] != x) {
            fa[x] = find(fa[x]);
        }
        return fa[x];
    }

    private double dist(int i, int j) {
        return Math.sqrt((a[i][0] - a[j][0]) * (a[i][0] - a[j][0]) + (a[i][1] - a[j][1]) * (a[i][1] - a[j][1]));
    }

    class Edge {
        int x;
        int y;
        double d;

        public Edge(int x, int y, double d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}
