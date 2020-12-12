package ox24;

import common.io.InputReader;
import common.io.OutputWriter;

public class AdditionChains {
    int n;
    int[] path = new int[110];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        int depth = 1;
        path[0] = 1;
        while (!dfs(1, depth)) {
            depth++;
        }
        for (int i = 0; i < depth; i++) {
            out.print(path[i] + " ");
        }
        out.println();
    }

    private boolean dfs(int u, int dep) {
        if (u == dep) {
            return path[u - 1] == n;
        }
        boolean[] vis = new boolean[110];
        for (int i = u - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                int s = path[i] + path[j];
                if (s > n || vis[s]) {
                    continue;
                }
                if (s < path[u - 1]) {
                    continue;
                }
                vis[s] = true;
                path[u] = s;
                if (dfs(u + 1, dep)) {
                    return true;
                }
            }
        }
        return false;
    }
}
