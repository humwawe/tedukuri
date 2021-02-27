package ox44;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;

public class MagneticBlock {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        long x0 = in.nextLong();
        long y0 = in.nextLong();
        long m = 0;
        long p = in.nextLong();
        long r = in.nextLong();
        int n = in.nextInt();
        Node[] a = new Node[n + 1];
        a[0] = new Node(0, r * r, m, p);
        for (int i = 1; i <= n; i++) {
            long x = in.nextLong();
            long y = in.nextLong();
            m = in.nextLong();
            p = in.nextLong();
            r = in.nextLong();
            a[i] = new Node((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y), r * r, m, p);
        }

        Arrays.sort(a, 1, n + 1, Comparator.comparingLong(x -> x.d));
        int size = (int) Math.sqrt(n);
        int[] left = new int[n];
        int[] right = new int[n];
        long[] d = new long[n];
        boolean[] vis = new boolean[n + 1];
        int cnt = 0;
        for (int i = 1; i <= n; i += size) {
            left[++cnt] = i;
            right[cnt] = Math.min(n, i + size - 1);
            d[cnt] = a[right[cnt]].d;
            Arrays.sort(a, i, Math.min(i + size, n + 1), Comparator.comparingLong(x -> x.m));
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(a[0]);
        int res = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (int i = 1; i <= cnt; i++) {
                if (d[i] <= cur.r) {
                    for (int j = left[i]; j <= right[i]; j++) {
                        if (a[j].m <= cur.p) {
                            if (!vis[j]) {
                                queue.add(a[j]);
                                res++;
                            }
                            left[i]++;
                        } else {
                            break;
                        }
                    }
                } else {
                    for (int j = left[i]; j <= right[i]; j++) {
                        if (!vis[j] && a[j].d <= cur.r && a[j].m <= cur.p) {
                            queue.add(a[j]);
                            vis[j] = true;
                            res++;
                        }
                    }
                    break;
                }
            }
        }
        out.println(res);
    }

    class Node {
        long d, r;
        long m, p;

        public Node(long d, long r, long m, long p) {
            this.d = d;
            this.r = r;
            this.m = m;
            this.p = p;
        }
    }
}
