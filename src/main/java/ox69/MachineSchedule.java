package ox69;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MachineSchedule {
    int n, m, k;
    int N = 105;
    int M = 1005;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int[] match = new int[N];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        Arrays.fill(h, -1);
        Arrays.fill(match, 0);
        idx = 0;
        m = in.nextInt();
        k = in.nextInt();
        for (int i = 0; i < k; i++) {
            in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            if (a == 0 || b == 0) {
                continue;
            }
            add(a, b);
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        out.println(res);
    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }
            }
        }
        return false;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
