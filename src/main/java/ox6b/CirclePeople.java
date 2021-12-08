package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class CirclePeople {
    int n;
    int N = 1100;
    int M = 4500;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    boolean[] vis = new boolean[M];
    int[] res = new int[M];
    int t = 0;
    int idx;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        int lim = 1 << (n - 1);
        for (int i = 0; i < lim; i++) {
            add(i, ((i << 1) & (lim - 1)) | 1);
            add(i, (i << 1) & (lim - 1));
        }
        dfs2(0);

        out.print((1 << n) + " ");
        for (int i = t - 1; i > 0; i--) {
            out.print(res[i] >> (n - 2));
        }
        out.println();

    }


    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }

    void dfs2(int u) {
        while (true) {
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = true;
                int j = e[i];
                dfs2(j);
            } else {
                break;
            }
        }
        res[t++] = u;
    }
}
