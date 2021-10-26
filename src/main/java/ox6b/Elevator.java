package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {
    int N = 1010;
    int M = 25;
    int[][] dist = new int[N][M];
    boolean[][] vis = new boolean[N][M];
    int inf = 0x3f3f3f3f;
    int n, m;
    int[] c = new int[M];
    int s;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 1; i <= m; i++) {
            c[i] = in.nextInt();
            if (c[i] == 0) {
                s = i;
            }
        }

        dijkstra();
        int res = inf;
        for (int i = 1; i <= m; i++) {
            res = Math.min(res, dist[n][i]);
        }
        if (res == inf) {
            res = -1;
        }
        out.println(res);

    }

    void dijkstra() {
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dist[i], inf);
        }
        dist[1][s] = 0;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.d));
        priorityQueue.add(new Node(1, s, 0));
        while (!priorityQueue.isEmpty()) {
            Node poll = priorityQueue.poll();
            int d = poll.d;
            int v = poll.id;
            int type = poll.type;

            if (vis[v][type]) {
                continue;
            }
            vis[v][type] = true;
            for (int i = 1; i <= m; i++) {
                int nx = v + c[i];
                int w = Math.abs(i - type) + 2 * Math.abs(c[i]);
                if (nx < 1 || nx > n) {
                    continue;
                }
                if (dist[nx][i] > d + w) {
                    dist[nx][i] = d + w;
                    priorityQueue.add(new Node(nx, i, dist[nx][i]));
                }
            }
        }
    }


    class Node {
        int id, type, d;

        public Node(int id, int type, int d) {
            this.id = id;
            this.type = type;
            this.d = d;
        }
    }
}
