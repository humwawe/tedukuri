package ox17;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Supermarket {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] p = new int[n];
        int[] d = new int[n];
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            p[i] = in.nextInt();
            d[i] = in.nextInt();
            idx[i] = i;
        }
        Arrays.sort(idx, Comparator.comparingInt(a -> d[a]));
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> p[a]));
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (d[idx[i]] > priorityQueue.size()) {
                priorityQueue.add(idx[i]);
                res += p[idx[i]];
            } else {
                priorityQueue.add(idx[i]);
                res += p[idx[i]];
                Integer poll = priorityQueue.poll();
                res -= p[poll];
            }
        }
        out.println(res);
    }
}
