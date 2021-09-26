package ox66;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Watchcow {
    int N = 10005, M = 50005 * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;
    int n, m;
    int[] res = new int[M];
    boolean[] vis = new boolean[M];
    int t = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
            add(b, a);
        }
        euler();
        for (int i = t - 1; i >= 0; i--) {
            out.println(res[i]);
        }
    }

    int[] stack = new int[M];
    int top;

    void euler() {
        stack[++top] = 1;
        while (top > 0) {
            int u = stack[top];
            int i = h[u];
            while (i != -1 && vis[i]) {
                i = ne[i];
            }
            if (i != -1) {
                h[u] = ne[i];
                vis[i] = true;
                stack[++top] = e[i];
            } else {
                res[t++] = u;
                top--;

            }
        }
    }


    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
