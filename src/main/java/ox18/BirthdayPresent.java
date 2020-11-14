package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BirthdayPresent {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n + 5];
        int k = 1;
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            if ((long) a[k] * t < 0) {
                a[++k] = t;
            } else {
                a[k] += t;
            }
        }
        int[] l = new int[k + 5];
        int[] r = new int[k + 5];
        boolean[] vis = new boolean[k + 5];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> Math.abs(a[x])));
        int res = 0;
        int cnt = 0;
        for (int i = 1; i <= k; i++) {
            if (a[i] > 0) {
                res += a[i];
                cnt++;
            }
            priorityQueue.add(i);
            l[i] = i - 1;
            r[i] = i + 1;
        }

        while (cnt > m) {
            while (vis[priorityQueue.peek()]) {
                priorityQueue.poll();
            }
            int x = priorityQueue.poll();
            if (l[x] != 0 && r[x] <= k || a[x] > 0) {
                res -= Math.abs(a[x]);
                cnt--;
                vis[l[x]] = true;
                vis[r[x]] = true;
                a[x] += a[l[x]] + a[r[x]];
                priorityQueue.add(x);
                del(l, r, vis, l[x]);
                del(l, r, vis, r[x]);
            }
        }
        out.println(res);
    }

    void del(int[] l, int[] r, boolean[] vis, int x) {
        r[l[x]] = r[x];
        l[r[x]] = l[x];
        vis[x] = true;
    }
}
