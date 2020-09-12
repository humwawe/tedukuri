package ox17;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Sequence {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int m = in.nextInt();
        int n = in.nextInt();
        int[] a = in.nextIntArray(n);
        Arrays.sort(a);
        for (int i = 1; i < m; i++) {
            int[] b = in.nextIntArray(n);
            Arrays.sort(b);
            int[] c = new int[n];
            int[] finalA = a;
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((x, y) -> finalA[x[0]] + b[x[1]] - finalA[y[0]] - b[y[1]]);
            Set<Integer> vis = new HashSet<>();
            int x = 0;
            int y = 0;
            priorityQueue.add(new int[]{x, y});
            vis.add(x * 1005 + y);
            for (int j = 0; j < n; j++) {
                int[] poll = priorityQueue.poll();
                c[j] = a[poll[0]] + b[poll[1]];
                if (poll[0] + 1 < n && vis.add((poll[0] + 1) * 1005 + poll[1])) {
                    priorityQueue.add(new int[]{poll[0] + 1, poll[1]});
                }
                if (poll[1] + 1 < n && vis.add(poll[0] * 1005 + poll[1] + 1)) {
                    priorityQueue.add(new int[]{poll[0], poll[1] + 1});
                }
            }
            a = c;
        }
        out.println(a);
    }
}
