package ox65;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Intervals {
    int N = 50005;
    int M = 3 * N;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;

    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(num(a - 1), num(b), c);
        }
        for (int i = 0; i <= 50000; i++) {
            add(num(i - 1), num(i), 0);
            add(num(i), num(i - 1), -1);
        }
        spfa();
        out.println(dist[50000]);
    }

    void spfa() {
        Arrays.fill(dist, -0x3f3f3f3f);
        dist[num(-1)] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(num(-1));
        vis[num(-1)] = true;
        while (!queue.isEmpty()) {
            int t = queue.poll();
            vis[t] = false;
            for (int i = h[t]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] < dist[t] + w[i]) {
                    dist[j] = dist[t] + w[i];
                    if (!vis[j]) {
                        queue.add(j);
                        vis[j] = true;
                    }
                }
            }
        }
    }

    int num(int x) {
        return x == -1 ? 50001 : x;
    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
