package ox12;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSubsequenceSum {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + a[i];
        }
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(0);
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (!deque.isEmpty() && i - deque.getFirst() > m) {
                deque.removeFirst();
            }
            // 之前加了0，一直不会为空，可以直接getFirst
            res = Math.max(res, sum[i] - sum[deque.getFirst()]);
            while (!deque.isEmpty() && sum[deque.getLast()] >= sum[i]) {
                deque.removeLast();
            }
            deque.add(i);
        }
        out.println(res);
    }
}
