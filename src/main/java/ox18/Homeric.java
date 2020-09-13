package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.PriorityQueue;

public class Homeric {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(n, (x, y) -> {
            if (x.w == y.w) {
                return x.cnt - y.cnt;
            } else {
                if (x.w - y.w > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        for (int i = 0; i < n; i++) {
            priorityQueue.add(new Node(in.nextLong(), 1));
        }
        for (int i = 0; i < (k - 1 - (n - 1) % (k - 1)) % (k - 1); i++) {
            priorityQueue.add(new Node(0, 1));
        }
        long res = 0;
        while (priorityQueue.size() >= k) {
            int maxCnt = 0;
            long w = 0;
            for (int i = 0; i < k; i++) {
                Node poll = priorityQueue.poll();
                w += poll.w;
                maxCnt = Math.max(maxCnt, poll.cnt);
            }
            res += w;
            priorityQueue.add(new Node(w, maxCnt + 1));
        }
        out.println(res);
        out.println(priorityQueue.peek().cnt - 1);

    }

    private class Node {
        long w;
        int cnt;

        public Node(long w, int cnt) {
            this.w = w;
            this.cnt = cnt;
        }
    }
}
