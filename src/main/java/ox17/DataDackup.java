package ox17;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DataDackup {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int[] d = new int[n + 1];
        int[] l = new int[n + 1];
        int[] r = new int[n + 1];
        int a = in.nextInt();
        d[0] = d[n] = 0x3f3f3f3f;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> d[x]));
        for (int i = 1; i <= n - 1; i++) {
            int b = in.nextInt();
            d[i] = b - a;
            a = b;
            priorityQueue.add(i);
            l[i] = i - 1;
            r[i] = i + 1;
        }
        boolean[] vis = new boolean[n + 1];
        long res = 0;
        while (k-- > 0) {
            while (vis[priorityQueue.peek()]) {
                priorityQueue.poll();
            }
            int x = priorityQueue.poll();
            res += d[x];
            vis[l[x]] = true;
            vis[r[x]] = true;
            d[x] = d[l[x]] + d[r[x]] - d[x];
            priorityQueue.add(x);
            r[l[l[x]]] = x;
            l[r[r[x]]] = x;
            l[x] = l[l[x]];
            r[x] = r[r[x]];
        }
        out.println(res);

    }
}
