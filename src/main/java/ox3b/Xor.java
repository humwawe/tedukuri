package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Xor {
    int N = 50000 + 10;
    int M = 200000 + 10;
    int n, m;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    long[] w = new long[M];
    boolean[] vis = new boolean[N];
    int idx = 0;
    long[] dis = new long[N];
    long[] a = new long[M];
    int cnt = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long c = in.nextLong();
            add(a, b, c);
            add(b, a, c);
        }
        dfs(1);
        long res = dis[n];
        gause();
        for (int i = 0; i < cnt; i++) {
            for (int j = 62; j >= 0; j--) {
                if ((a[i] >> j & 1) == 1) {
                    if ((res >> j & 1) == 0) {
                        res ^= a[i];
                    }
                    break;
                }
            }
        }
        out.println(res);
    }

    private void gause() {
        for (int i = 0; i < cnt; i++) {
            for (int j = i + 1; j < cnt; j++) {
                if (a[j] > a[i]) {
                    long tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
            if (a[i] == 0) {
                cnt = i;
                break;
            }

            for (int k = 62; k >= 0; k--) {
                if ((a[i] >> k & 1) == 1) {
                    for (int j = 0; j < cnt; j++) {
                        if (i != j && (a[j] >> k & 1) == 1) {
                            a[j] ^= a[i];
                        }
                    }
                    break;
                }
            }
        }
    }

    void dfs(int u) {
        vis[u] = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                dis[j] = dis[u] ^ w[i];
                dfs(j);
            } else {
                a[cnt++] = dis[u] ^ dis[j] ^ w[i];
            }
        }
    }

    void add(int a, int b, long c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
