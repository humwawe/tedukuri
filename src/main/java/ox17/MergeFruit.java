package ox17;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.PriorityQueue;

public class MergeFruit {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(n);
        for (int i = 0; i < n; i++) {
            priorityQueue.add(in.nextInt());
        }
        int res = 0;
        while (priorityQueue.size() >= 2) {
            int a = priorityQueue.poll();
            int b = priorityQueue.poll();
            res += a + b;
            priorityQueue.add(a + b);
        }
        out.println(res);
    }
}
