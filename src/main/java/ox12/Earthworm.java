package ox12;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Earthworm {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int u = in.nextInt();
        int v = in.nextInt();
        int t = in.nextInt();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        Arrays.sort(a, (x, y) -> y - x);
        int idx = 0;
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        int delta = 0;
        for (int i = 1; i <= m; i++) {
            int x = (idx == a.length ? Integer.MIN_VALUE : a[idx]);
            int y = q1.isEmpty() ? Integer.MIN_VALUE : q1.peek();
            int z = q2.isEmpty() ? Integer.MIN_VALUE : q2.peek();
            long tmp;
            if (z >= x && z >= y) {
                q2.poll();
                tmp = z + delta;
            } else if (y >= x && y >= z) {
                q1.poll();
                tmp = y + delta;
            } else {
                idx++;
                tmp = x + delta;
            }
            q1.add((int) (tmp * u / v) - delta - q);
            q2.add((int) (tmp - tmp * u / v) - delta - q);
            if (i % t == 0) {
                out.print(tmp + " ");
            }
            delta += q;
        }
        out.println();
        for (int i = 1; i <= n + m; i++) {
            int x = (idx == a.length ? Integer.MIN_VALUE : a[idx]);
            int y = q1.isEmpty() ? Integer.MIN_VALUE : q1.peek();
            int z = q2.isEmpty() ? Integer.MIN_VALUE : q2.peek();
            int tmp;
            if (z >= x && z >= y) {
                q2.poll();
                tmp = z + delta;
            } else if (y >= x && y >= z) {
                q1.poll();
                tmp = y + delta;
            } else {
                idx++;
                tmp = x + delta;
            }
            if (i % t == 0) {
                out.print(tmp + " ");
            }
        }
    }
}
