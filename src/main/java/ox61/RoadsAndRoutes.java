package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class RoadsAndRoutes {
    int N = 25010, M = 150010, inf = 0x3f3f3f3f;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] dist = new int[N];
    boolean[] vis = new boolean[N];
    int[] id = new int[N];
    List<Integer>[] lists = new List[N];
    int idCnt = 0;
    int[] deg = new int[N];
    int n, mr, mp, s;
    Queue<Integer> queue = new ArrayDeque<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        mr = in.nextInt();
        mp = in.nextInt();
        s = in.nextInt();

        for (int i = 0; i < mr; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
        }
        for (int i = 1; i <= n; i++) {
            if (id[i] == 0) {
                ++idCnt;
                dfs(i);
            }
        }

        for (int i = 0; i < mp; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            deg[id[b]]++;
        }

        topoSort();

        for (int i = 1; i <= n; i++) {
            if (dist[i] > inf / 2) {
                out.println("NO PATH");
            } else {
                out.println(dist[i]);
            }
        }
    }

    private void topoSort() {
        Arrays.fill(dist, inf);
        dist[s] = 0;

        for (int i = 1; i <= idCnt; i++) {
            if (deg[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer block = queue.poll();
            dijkstra(block);
        }

    }

    private void dijkstra(Integer block) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (Integer i : lists[block]) {
            priorityQueue.add(new int[]{dist[i], i});
        }
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            int d = poll[0];
            int v = poll[1];
            if (vis[v]) {
                continue;
            }
            vis[v] = true;
            for (int i = h[v]; i != -1; i = ne[i]) {
                int j = e[i];
                if (dist[j] > d + w[i]) {
                    dist[j] = d + w[i];
                    if (id[j] == id[v]) {
                        priorityQueue.add(new int[]{dist[j], j});
                    }
                }
                if (id[j] != id[v]) {
                    if (--deg[id[j]] == 0) {
                        queue.add(id[j]);
                    }
                }
            }
        }
    }

    private void dfs(int u) {
        if (lists[idCnt] == null) {
            lists[idCnt] = new ArrayList<>();
        }
        lists[idCnt].add(u);
        id[u] = idCnt;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (id[j] == 0) {
                dfs(j);
            }
        }

    }

    void add(int a, int b, int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
