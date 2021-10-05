package ox67;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class PkuAcmTeamsExcursions {
    int N = 100005, M = 400005, mod = 1000000007, inf = 0x3f3f3f3f;

    int[] h = new int[N];
    int[] e = new int[M];
    int[] w = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m, s, t, q;
    int[][] deg = new int[2][N];
    int[][] f = new int[2][N];
    int[] d = new int[N];
    int[] pre = new int[N];
    boolean[] bridge = new boolean[M];
    int[] a = new int[N];
    boolean[] b = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        Arrays.fill(deg[0], 0);
        Arrays.fill(deg[1], 0);
        Arrays.fill(f[0], 0);
        Arrays.fill(f[1], 0);
        Arrays.fill(bridge, false);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        s = in.nextInt();
        t = in.nextInt();
        q = in.nextInt();

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
            deg[0][b]++;
            deg[1][a]++;
        }
        topsort(s, 0);
        if (f[0][t] == 0) {
            out.println(-1);
            return;
        }
        topsort(t, 1);

        for (int i = 0; i < idx; i += 2) {
            int x = e[i ^ 1];
            int y = e[i];
            if ((long) f[0][x] * f[1][y] % mod == f[0][t]) {
                bridge[i] = true;
            }
        }
        int cnt = 0;
        while (t != s) {
            a[++cnt] = w[pre[t]];
            b[cnt] = bridge[pre[t]];
            t = e[pre[t] ^ 1];
        }


        int[] sum = new int[cnt + 1];
        int[] sumBri = new int[cnt + 1];
        for (int i = 1; i <= cnt; i++) {
            sum[i] = sum[i - 1] + a[i];
            sumBri[i] = sumBri[i - 1] + (b[i] ? a[i] : 0);
        }
        int[] ds = new int[cnt + 1];
        int[] dsMin = new int[cnt + 1];
        dsMin[0] = inf;
        int j = 0;
        for (int i = 1; i <= cnt; i++) {
            // j+1~i乘车 j这条边可能部分乘车
            while (sum[i] - sum[j] > q) {
                j++;
            }
            // j 之前的桥都危险
            ds[i] = sumBri[j];
            if (j > 0 && b[j]) {
                ds[i] -= Math.min(a[j], q - (sum[i] - sum[j]));
            }
            dsMin[i] = Math.min(ds[i], dsMin[i - 1] + (b[i] ? a[i] : 0));
        }

        int[] dt = new int[cnt + 1];
        j = cnt + 1;
        for (int i = cnt; i > 0; i--) {
            while (sum[j - 1] - sum[i - 1] > q) {
                j--;
            }
            dt[i] = sumBri[cnt] - sumBri[j - 1];
            if (j <= cnt && b[j]) {
                dt[i] -= Math.min(a[j], q - (sum[j - 1] - sum[i - 1]));
            }
        }

        int res = 1 << 30;
        for (int i = 1; i <= cnt; i++) {
            res = Math.min(res, dt[i] + dsMin[i - 1]);
        }

        j = 0;
        for (int i = 1; i <= cnt; i++) {
            while (sum[i] - sum[j] > q * 2) {
                j++;
            }
            int tmp = sumBri[j];
            if (j > 0 && b[j]) {
                tmp -= Math.min(a[j], 2 * q - (sum[i] - sum[j]));
            }
            res = Math.min(res, tmp + sumBri[cnt] - sumBri[i]);
        }
        out.println(res);
    }

    void topsort(int s, int bit) {
        // 正图求最短路
        if (bit == 0) {
            Arrays.fill(d, inf);
            d[s] = 0;
        }
        f[bit][s] = 1;
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (deg[bit][i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                if ((i & 1) == bit) {
                    int j = e[i];
                    f[bit][j] = (f[bit][j] + f[bit][u]) % mod;
                    if (bit == 0 && d[j] > d[u] + w[i]) {
                        d[j] = d[u] + w[i];
                        pre[j] = i;
                    }
                    if (--deg[bit][j] == 0) {
                        queue.add(j);
                    }
                }
            }
        }
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
