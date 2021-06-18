package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Computer {
    int N = 10010;
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    int[] w = new int[N];
    int idx;
    int n;
    int[] res = new int[N];
    int[] l1 = new int[N];
    int[] l2 = new int[N];
    int[] len = new int[N];
    int[] fw = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        Arrays.fill(h, -1);
        idx = 0;
        Arrays.fill(res, 0);
        Arrays.fill(l1, 0);
        Arrays.fill(l2, 0);
        Arrays.fill(len, 0);
        Arrays.fill(fw, 0);
        for (int i = 2; i <= n; i++) {
            int a = in.nextInt();
            fw[i] = in.nextInt();
            add(a, i, fw[i]);
        }
        dfs1(1);
        dfs2(1, 0);
        for (int i = 1; i <= n; i++) {
            out.println(res[i]);
        }
    }

    private void dfs2(int u, int d) {
        res[u] = Math.max(d, len[u]);
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (l1[u] == j) {
                dfs2(j, Math.max(d + w[i], len[l2[u]] + fw[l2[u]] + w[i]));
            } else {
                dfs2(j, Math.max(d + w[i], len[u] + w[i]));
            }
        }
    }

    private void dfs1(int u) {
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            dfs1(j);
            if (len[j] + w[i] > tmp1) {
                tmp2 = tmp1;
                tmp1 = len[j] + w[i];
                l2[u] = l1[u];
                l1[u] = j;
            } else if (len[j] + w[i] > tmp2) {
                tmp2 = len[j] + w[i];
                l2[u] = j;
            }
        }
        len[u] = tmp1;
    }

    void add(int a, int b, int v) {
        e[idx] = b;
        w[idx] = v;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
