package ox28;

import common.io.InputReader;
import common.io.OutputWriter;

public class TheRotationGame {
    int N = 24;
    int[] q = new int[N];
    int[][] op = new int[][]{
            {0, 2, 6, 11, 15, 20, 22},
            {1, 3, 8, 12, 17, 21, 23},
            {10, 9, 8, 7, 6, 5, 4},
            {19, 18, 17, 16, 15, 14, 13},
            {23, 21, 17, 12, 8, 3, 1},
            {22, 20, 15, 11, 6, 2, 0},
            {13, 14, 15, 16, 17, 18, 19},
            {4, 5, 6, 7, 8, 9, 10}
    };
    int[] center = new int[]{6, 7, 8, 11, 12, 15, 16, 17};
    int[] opposite = new int[]{5, 4, 7, 6, 1, 0, 3, 2};
    int[] path = new int[100];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        q[0] = in.nextInt();
        if (q[0] == 0) {
            return;
        }
        for (int i = 1; i < N; i++) {
            q[i] = in.nextInt();
        }
        int depth = 0;
        while (!dfs(0, depth, -1)) {
            depth++;
        }
        if (depth == 0) {
            out.print("No moves needed");
        }
        for (int i = 0; i < depth; i++) {
            out.print((char) ('A' + path[i]));
        }
        out.println();
        out.println(q[6]);

    }

    boolean dfs(int depth, int maxDepth, int p) {
        if (depth + f() > maxDepth) {
            return false;
        }
        if (check()) {
            return true;
        }
        for (int i = 0; i < 8; i++) {
            if (opposite[i] == p) {
                continue;
            }
            operation(i);
            path[depth] = i;
            if (dfs(depth + 1, maxDepth, i)) {
                return true;
            }
            operation(opposite[i]);
        }

        return false;
    }

    boolean check() {
        for (int i = 1; i < 8; i++) {
            if (q[center[i]] != q[center[0]]) {
                return false;
            }
        }
        return true;
    }

    void operation(int x) {
        int t = q[op[x][0]];
        for (int i = 0; i < 6; i++) {
            q[op[x][i]] = q[op[x][i + 1]];
        }
        q[op[x][6]] = t;
    }

    int f() {
        int[] sum = new int[4];
        for (int i = 0; i < 8; i++) {
            sum[q[center[i]]]++;
        }
        int s = 0;
        for (int i = 1; i <= 3; i++) {
            s = Math.max(s, sum[i]);
        }
        return 8 - s;
    }
}
