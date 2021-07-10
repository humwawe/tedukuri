package ox62;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PicnicPlanning {
    int N = 25;
    int M = N * N;
    int[] p = new int[N];
    int[] id = new int[N];
    boolean[] vis = new boolean[N];
    boolean[][] st = new boolean[N][N];
    int[][] dist = new int[N][N];
    Edge[] edges = new Edge[M];
    Edge[] mn = new Edge[M];
    Edge[] mx = new Edge[M];
    int m, k;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        m = in.nextInt();
        mapping("Park");
        for (int i = 0; i < m; i++) {
            int a = mapping(in.nextString());
            int b = mapping(in.nextString());
            int w = in.nextInt();
            dist[a][b] = dist[b][a] = w;
            edges[i] = new Edge(a, b, w);
        }
        k = in.nextInt();

        int t = 0;
        for (int i = 1; i < cnt; i++) {
            if (id[i] == 0) {
                dfs(i, ++t);
            }
        }
        int res = kruskal();


        for (int i = 1; i < cnt; i++) {
            mn[id[i]] = new Edge(0, i, inf);
        }
        for (int i = 1; i < cnt; i++) {
            if (dist[0][i] != 0 && dist[0][i] < mn[id[i]].w) {
                mn[id[i]] = new Edge(0, i, dist[0][i]);
            }
        }
        for (int i = 1; i < cnt; i++) {
            if (!vis[id[i]]) {
                st[0][mn[id[i]].b] = st[mn[id[i]].b][0] = true;
                res += mn[id[i]].w;
                vis[id[i]] = true;
                k--;
            }
        }
        while (k > 0) {
            for (int i = 1; i < cnt; i++) {
                mx[i] = new Edge(-1, -1, inf);
            }
            dfs2(0, 0, 0);
            int tmp = 0;
            int idx = 0;
            for (int i = 1; i < cnt; i++) {
                if (dist[0][i] != 0 && !st[0][i]) {
                    if (mx[i].w - dist[0][i] > tmp) {
                        tmp = mx[i].w - dist[0][i];
                        idx = i;
                    }
                }
            }
            if (idx == 0) {
                break;
            }
            st[0][idx] = st[idx][0] = true;
            st[mx[idx].a][mx[idx].b] = st[mx[idx].b][mx[idx].a] = false;
            res -= tmp;
            k--;
        }
        out.println("Total miles driven: " + res);

    }

    private void dfs2(int u, int fa, int d) {
        for (int i = 1; i < cnt; i++) {
            if (i == fa || !st[u][i]) {
                continue;
            }
            mx[i] = new Edge(u, i, Math.max(d, dist[u][i]));

            dfs2(i, u, Math.max(d, dist[u][i]));
        }
    }

    private int kruskal() {
        for (int i = 0; i < cnt; i++) {
            p[i] = i;
        }
        Arrays.sort(edges, 0, m, Comparator.comparingInt(a -> a.w));
        int res = 0;
        for (int i = 0; i < m; i++) {
            int a = edges[i].a;
            int b = edges[i].b;
            int w = edges[i].w;
            if (id[a] != id[b]) {
                continue;
            }
            int pa = find(a);
            int pb = find(b);
            if (pa != pb) {
                res += w;
                p[pa] = pb;
                st[a][b] = st[b][a] = true;
            }
        }
        return res;
    }

    void dfs(int u, int t) {
        id[u] = t;
        for (int i = 1; i < cnt; i++) {
            if (dist[u][i] != 0 && id[i] == 0) {
                dfs(i, t);
            }
        }
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    Map<String, Integer> map = new HashMap<>();
    int cnt = 0;

    int mapping(String x) {
        if (!map.containsKey(x)) {
            map.put(x, cnt++);
        }
        return map.get(x);
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

