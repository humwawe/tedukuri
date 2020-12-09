package ox21;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.BitSet;

public class AccessibilityStatistics {
    int N = 30005;
    int[] h = new int[N];
    int[] e = new int[N];
    int[] ne = new int[N];
    int[] deg = new int[N];
    int idx;
    BitSet[] set = new BitSet[N];
    boolean[] vis = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(h, -1);
        idx = 0;
        int n = in.nextInt();
        int m = in.nextInt();
//        for (int i = 0; i < m; i++) {
//            int a = in.nextInt();
//            int b = in.nextInt();
//            add(b, a);
//        }
//
//        BitSet[] bitSet = new BitSet[n + 1];
//        for (int i = 0; i <= n; i++) {
//            bitSet[i] = new BitSet();
//        }
//        Queue<Integer> queue = new ArrayDeque<>();
//        for (int i = 1; i <= n; i++) {
//            if (deg[i] == 0) {
//                queue.add(i);
//            }
//            bitSet[i].set(i);
//        }
//        while (!queue.isEmpty()) {
//            Integer cur = queue.poll();
//            for (int i = h[cur]; i != -1; i = ne[i]) {
//                int j = e[i];
//                bitSet[j].or(bitSet[cur]);
//                deg[j]--;
//                if (deg[j] == 0) {
//                    queue.add(j);
//                }
//            }
//        }
//        for (int i = 1; i <= n; i++) {
//            out.println(bitSet[i].cardinality());
//        }


        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            add(a, b);
        }
        for (int i = 0; i <= n; i++) {
            set[i] = new BitSet();
        }
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                dfs(i);
            }
        }
        for (int i = 1; i <= n; i++) {
            out.println(set[i].cardinality());
        }

    }

    private void dfs(int cur) {
        set[cur].set(cur);
        vis[cur] = true;
        for (int i = h[cur]; i != -1; i = ne[i]) {
            int j = e[i];
            if (vis[j]) {
                set[cur].or(set[j]);
                continue;
            }
            dfs(j);
            set[cur].or(set[j]);
        }

    }


    private void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
        deg[b]++;
    }
}
