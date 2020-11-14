package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.PriorityQueue;

public class BlackBox {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        PriorityQueue<Integer> max = new PriorityQueue<>((x, y) -> y - x);
        PriorityQueue<Integer> min = new PriorityQueue<>();
        int cnt = 0;
        int[] a = in.nextIntArray(n);
        for (int i = 0; i < m; i++) {
            int t = in.nextInt();
            while (cnt < t) {
                int x = a[cnt++];
                if (max.isEmpty() || x >= max.peek()) {
                    min.add(x);
                } else {
                    max.add(x);
                    min.add(max.poll());
                }
            }
            out.println(min.peek());
            max.add(min.poll());
        }
    }
}
