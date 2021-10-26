package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Sightseeing {
    int N = 1010;
    int M = 10010;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[][] dist = new int[N][2];
    int[][] cnt = new int[N][2];
    boolean[][] vis = new boolean[N][2];
    int inf = 0x3f3f3f3f;
    int s, t;
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
        }
        s = in.nextInt();
        t = in.nextInt();
        out.println(dijkstra());
    }


    int dijkstra() {
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dist[i], inf);
            Arrays.fill(vis[i], false);
            Arrays.fill(cnt[i], 0);
        }

        dist[s][0] = 0;
        cnt[s][0] = 1;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.d));
        priorityQueue.add(new Node(s, 0, 0));
        while (!priorityQueue.isEmpty()) {
            Node poll = priorityQueue.poll();
            int d = poll.d;
            int v = poll.id;
            int type = poll.type;
            int count = cnt[v][type];

            if (vis[v][type]) {
                continue;
            }
            vis[v][type] = true;
            for (int i = h[v]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j][0] > d + w[i]) {
                    dist[j][1] = dist[j][0];
                    cnt[j][1] = cnt[j][0];
                    priorityQueue.add(new Node(j, 1, dist[j][1]));
                    dist[j][0] = d + w[i];
                    cnt[j][0] = count;
                    priorityQueue.add(new Node(j, 0, dist[j][0]));
                } else if (dist[j][0] == d + w[i]) {
                    cnt[j][0] += count;
                } else if (dist[j][1] > d + w[i]) {
                    dist[j][1] = d + w[i];
                    cnt[j][1] = count;
                    priorityQueue.add(new Node(j, 1, dist[j][1]));
                } else if (dist[j][1] == d + w[i]) {
                    cnt[j][1] += count;
                }
            }
        }
        int res = cnt[t][0];
        if (dist[t][0] + 1 == dist[t][1]) {
            res += cnt[t][1];
        }
        return res;
    }

    void add(int a, int b, int v) {
        e[idx] = b;
        w[idx] = v;
        ne[idx] = h[a];
        h[a] = idx++;
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
