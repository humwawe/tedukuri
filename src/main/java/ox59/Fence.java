package ox59;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

public class Fence {
    int N = 105;
    int M = 16010;
    Rec[] a = new Rec[N];
    int[][] f = new int[N][M];
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        m = in.nextInt();
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            a[i] = new Rec(in.nextInt(), in.nextInt(), in.nextInt());
        }
        Arrays.sort(a, 1, n + 1, Comparator.comparingInt(x -> x.s));
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            queue.clear();
            for (int k = Math.max(0, a[i].s - a[i].l); k <= a[i].s - 1; k++) {
                while (!queue.isEmpty() && calc(i, queue.peekLast()) <= calc(i, k)) {
                    queue.pollLast();
                }
                queue.addLast(k);
            }
            for (int j = 1; j <= m; j++) {
                f[i][j] = Math.max(f[i - 1][j], f[i][j - 1]);
                if (j >= a[i].s) {
                    while (!queue.isEmpty() && queue.peekFirst() < j - a[i].l) {
                        queue.pollFirst();
                    }
                    if (!queue.isEmpty()) {
                        f[i][j] = Math.max(f[i][j], calc(i, queue.peekFirst()) + a[i].p * j);
                    }
                }
            }
        }
        out.println(f[n][m]);
    }

    int calc(int i, int k) {
        return f[i - 1][k] - a[i].p * k;
    }

    class Rec {
        int l, p, s;

        public Rec(int l, int p, int s) {
            this.l = l;
            this.p = p;
            this.s = s;
        }
    }
}
