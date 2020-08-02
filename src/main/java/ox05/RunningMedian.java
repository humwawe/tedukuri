package ox05;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.PriorityQueue;

public class RunningMedian {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int cas = in.nextInt();
        int m = in.nextInt();
        int cnt = (m + 1) / 2;
        PriorityQueue<Integer> p1 = new PriorityQueue<>();
        PriorityQueue<Integer> p2 = new PriorityQueue<>((a, b) -> b - a);
        out.println(testNumber + " " + cnt);
        for (int i = 1; i <= m; i++) {
            int x = in.nextInt();
            if (p2.isEmpty() || x < p2.peek()) {
                p2.add(x);
            } else {
                p1.add(x);
            }
            if (p2.size() - p1.size() >= 2) {
                p1.add(p2.poll());
            }
            if (p1.size() - p2.size() >= 1) {
                p2.add(p1.poll());
            }
            if (i % 2 == 1) {
                out.print(p2.peek() + " ");
            }
            if (i % 20 == 0) {
                out.println();
            }
        }
        out.println();
    }
}
