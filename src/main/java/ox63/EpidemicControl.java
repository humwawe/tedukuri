package ox63;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class EpidemicControl {
    int N = (int) (5e4 + 5);
    int M = N * 2;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int[] w = new int[M];
    int idx;
    int[] d = new int[N];
    int T = 18;
    int[][] f = new int[N][T];

    int[] army = new int[N];
    long[] dist = new long[N];

    boolean[] has = new boolean[N];
    boolean[] cover = new boolean[N];
    boolean[] used = new boolean[N];
    int n, m;
    long l, r;
    List<Node> a = new ArrayList<>();
    List<Integer> son = new ArrayList<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            add(a, b, c);
            add(b, a, c);
            r += c;
        }
        bfs();
        m = in.nextInt();
        for (int i = 1; i <= m; i++) {
            army[i] = in.nextInt();
        }
        while (l < r) {
            long mid = l + r >> 1;
            if (check(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        out.println(l);
    }

    private boolean check(long mid) {
        Arrays.fill(has, false);
        Arrays.fill(cover, false);
        Arrays.fill(used, false);
        a = new ArrayList<>();
        son = new ArrayList<>();

        for (int i = 1; i <= m; i++) {
            Node node = go(army[i], mid);
            long rest = node.rest;
            int pos = node.point;
            // 一类
            if (rest <= dist[pos]) {
                has[pos] = true;
            } else {
                // 二类军队（减去到根的时间）
                a.add(new Node(rest - dist[pos], pos));
            }
        }
        dfs(1);
        a.sort(Comparator.comparingLong(x -> x.rest));
        for (int i = 0; i < a.size(); i++) {
            long rest = a.get(i).rest;
            int s = a.get(i).point;
            if (!cover[s] && rest < dist[s]) {
                cover[s] = true;
                used[i] = true;
            }
        }

        son.sort(Comparator.comparingLong(x -> dist[x]));

        for (int i = 0, j = 0; i < son.size(); i++) {
            int s = son.get(i);
            if (cover[s]) {
                continue;
            }
            while (j < a.size() && (used[j] || a.get(j).rest < dist[s])) {
                j++;
            }
            if (j == a.size()) {
                return false;
            }
            j++;
        }
        return true;
    }

    private void dfs(int u) {
        boolean allChildCovered = true;
        boolean isLeaf = true;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (d[j] <= d[u]) {
                continue;
            }
            dfs(j);
            allChildCovered &= cover[j];
            isLeaf = false;
            if (u == 1 && !cover[j]) {
                son.add(j);
            }
        }
        cover[u] = has[u] || (!isLeaf && allChildCovered);

    }

    private Node go(int x, long mid) {
        for (int i = T - 1; i >= 0; i--) {
            if (f[x][i] > 1 && dist[x] - dist[f[x][i]] <= mid) {
                mid -= dist[x] - dist[f[x][i]];
                x = f[x][i];
            }
        }
        return new Node(mid, x);
    }

    class Node {
        long rest = 0;
        int point;

        public Node(long rest, int point) {
            this.rest = rest;
            this.point = point;
        }
    }

    void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            for (int i = h[u]; i != -1; i = ne[i]) {
                int j = e[i];
                if (d[j] == 0) {
                    d[j] = d[u] + 1;
                    dist[j] = dist[u] + w[i];
                    f[j][0] = u;
                    for (int k = 1; k < T; k++) {
                        f[j][k] = f[f[j][k - 1]][k - 1];
                    }
                    queue.add(j);
                }
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
