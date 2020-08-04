package ox07;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class StallReservations {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] nos = new int[n];
        int no = 0;
        int res = 0;
        int[][] a = new int[n][3];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            a[i][0] = x;
            a[i][1] = y;
            a[i][2] = i;
        }
        Arrays.sort(a, Comparator.comparingInt(x -> x[0]));
        for (int i = 0; i < n; i++) {
            if (priorityQueue.isEmpty()) {
                no = 1;
                nos[a[i][2]] = no;
            } else {
                if (a[i][0] > priorityQueue.peek()[0]) {
                    nos[a[i][2]] = priorityQueue.peek()[1];
                    priorityQueue.poll();
                } else {
                    ++no;
                    nos[a[i][2]] = no;
                }
            }
            res = Math.max(res, no);
            priorityQueue.add(new int[]{a[i][1], nos[a[i][2]]});
        }
        out.println(res);
        for (int i = 0; i < n; i++) {
            out.println(nos[i]);
        }
    }
}
