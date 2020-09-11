package ox16;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TheXorLongestPath {
    int N = (int) (1e5 + 5);
    int[] h = new int[N];
    int[] e = new int[N * 2];
    int[] ne = new int[N * 2];
    int[] w = new int[N * 2];
    boolean[] vis = new boolean[N];
    int idx = 0;
    int[] a = new int[N];
    int[][] son = new int[N * 32][2];
    int idx2 = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        int n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            add(x, y, z);
            add(y, x, z);
        }
        dfs(0);
        int res = 0;
        for (int i = 0; i < n; i++) {
            insert(a[i]);
            res = Math.max(res, query(a[i]));
        }
        out.println(res);
    }

    private void dfs(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                a[j] = a[u] ^ w[i];
                dfs(j);
            }
        }
    }

    private void add(int x, int y, int z) {
        e[idx] = y;
        w[idx] = z;
        ne[idx] = h[x];
        h[x] = idx++;
    }

    void insert(int num) {
        int p = 0;
        for (int i = 30; i >= 0; i--) {
            int t = (num >> i) & 1;
            if (son[p][t] == 0) {
                son[p][t] = ++idx2;
            }
            p = son[p][t];
        }
    }

    int query(int num) {
        int p = 0;
        int res = 0;
        for (int i = 30; i >= 0; i--) {
            int t = (num >> i) & 1;
            if (son[p][t ^ 1] != 0) {
                p = son[p][t ^ 1];
                res += 1 << i;
            } else {
                p = son[p][t];
            }
        }
        return res;
    }
}
