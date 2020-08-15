package ox12;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class TeamQueue {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        if (n == 0) {
            return;
        }
        Queue<Integer>[] queues = new Queue[n + 1];
        for (int i = 0; i <= n; i++) {
            queues[i] = new ArrayDeque<>();
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int num = in.nextInt();
            for (int j = 0; j < num; j++) {
                map.put(in.nextInt(), i);
            }
        }
        out.println(String.format("Scenario #%d", testNumber));
        while (true) {
            String command = in.nextString();
            if ("STOP".equals(command)) {
                break;
            } else if ("ENQUEUE".equals(command)) {
                int num = in.nextInt();
                int t = map.get(num);
                if (queues[t].isEmpty()) {
                    queues[0].add(t);
                }
                queues[t].add(num);
            } else {
                int t = queues[0].peek();
                Integer poll = queues[t].poll();
                out.println(poll);
                if (queues[t].isEmpty()) {
                    queues[0].poll();
                }
            }
        }
        out.println();
    }
}
