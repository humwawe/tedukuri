package ox59;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class CutTheSequence {
    int N = 100006;
    long inf = (long) 1e15;
    long[] f = new long[N];
    int n;
    long m;
    long sum = 0;
    Deque<Integer> queue = new ArrayDeque<>();
    PriorityQueue<Long> priorityQueue = new PriorityQueue<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextLong();
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++) {
            a[i + 1] = in.nextInt();
        }

        f[0] = 0;
        int p = 1;
        for (int i = 1; i <= n; i++) {
            sum += a[i];
            while (sum > m) {
                sum -= a[p++];
            }
            while (!queue.isEmpty() && queue.getFirst() < p) {
                int t = queue.pollFirst();
                if (!queue.isEmpty()) {
                    priorityQueue.remove(f[t] + a[queue.getFirst()]);
                }
            }
            while (!queue.isEmpty() && a[queue.getLast()] <= a[i]) {
                int t = queue.pollLast();
                if (!queue.isEmpty()) {
                    priorityQueue.remove(f[queue.getLast()] + a[t]);
                }
            }
            if (!queue.isEmpty()) {
                priorityQueue.add(f[queue.getLast()] + a[i]);
            }
            queue.addLast(i);
            f[i] = f[p - 1] + a[queue.getFirst()];
            if (!priorityQueue.isEmpty()) {
                f[i] = Math.min(f[i], priorityQueue.peek());
            }
        }
        out.println(f[n]);
    }
}
