package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.LinkedList;

public class Haystack {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++) {
            a[n - i] = in.nextInt();
        }
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + a[i];
        }
        LinkedList<Integer> queue = new LinkedList<>();
        int[] f = new int[n + 1];
        int[] g = new int[n + 1];
        int head = 0;
        for (int i = 1; i <= n; i++) {
            while (!queue.isEmpty() && sum[queue.getFirst()] + g[queue.getFirst()] <= sum[i]) {
                head = queue.pollFirst();
            }
            f[i] = f[head] + 1;
            g[i] = sum[i] - sum[head];
            while (!queue.isEmpty() && sum[queue.getLast()] + g[queue.getLast()] >= sum[i] + g[i]) {
                queue.pollLast();
            }
            queue.addLast(i);
        }
        out.println(f[n]);
    }
}
