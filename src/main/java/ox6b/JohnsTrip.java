package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JohnsTrip {
    int n, m;
    int N = 55;
    int M = 4010;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    boolean[] vis = new boolean[M];
    // 保存欧拉回路结果
    int[] res = new int[M];
    int t;
    int idx;
    List<int[]> list = new ArrayList<>();
    int[] deg = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        Arrays.fill(vis, false);
        t = 0;
        list.clear();
        Arrays.fill(deg, 0);
        int a = in.nextInt();
        int b = in.nextInt();
        if (a == 0 && b == 0) {
            return;
        }
        n = Math.max(n, Math.max(a, b));
        int c = in.nextInt();
        int start = Math.min(a, b);
        list.add(new int[]{a, b, c});
        deg[a]++;
        deg[b]++;
        while (true) {
            a = in.nextInt();
            b = in.nextInt();
            if (a == 0 && b == 0) {
                break;
            }
            n = Math.max(n, Math.max(a, b));
            deg[a]++;
            deg[b]++;
            c = in.nextInt();
            list.add(new int[]{a, b, c});
        }
        for (int i = 1; i <= n; i++) {
            if (deg[i] % 2 == 1) {
                out.println("Round trip does not exist.");
                return;
            }
        }

        list.sort((x, y) -> y[2] - x[2]);
        for (int[] edge : list) {
            a = edge[0];
            b = edge[1];
            c = edge[2];
            add(a, b, c);
            add(b, a, c);
        }

        dfs2(start, 0);
        for (int i = t - 2; i >= 0; i--) {
            out.print(res[i] + " ");
        }
        out.println();

    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    void dfs2(int u, int pre) {
        while (true) {
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = vis[i ^ 1] = true;
                int j = e[i];
                dfs2(j, w[i]);
            } else {
                break;
            }
        }
        res[t++] = pre;
    }
}
