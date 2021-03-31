package ox55;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Deque;

public class LoopTransportation {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n * 2];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            a[i + n] = a[i];
        }
        Deque<Integer> queue = new ArrayDeque<>();
        int res = 0;
        int len = n / 2;
        queue.addLast(0);
        for (int i = 1; i < 2 * n; i++) {
            while (!queue.isEmpty() && i - queue.peekFirst() > len) {
                queue.pollFirst();
            }
            Integer first = queue.peekFirst();
            res = Math.max(res, i - first + a[first] + a[i]);
            while (!queue.isEmpty() && a[queue.peekLast()] - queue.peekLast() <= a[i] - i) {
                queue.pollLast();
            }
            queue.addLast(i);
        }
        out.println(res);
    }
}
