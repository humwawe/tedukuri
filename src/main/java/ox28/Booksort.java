package ox28;

import common.io.InputReader;
import common.io.OutputWriter;

public class Booksort {
    int n;
    int[] q;
    int[][] w;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        q = in.nextIntArray(n);
        w = new int[5][n];
        int depth = 0;
        while (depth < 5 && !dfs(0, depth)) {
            depth++;
        }
        if (depth >= 5) {
            out.println("5 or more");
        } else {
            out.println(depth);
        }
    }

    private boolean dfs(int depth, int max_depth) {
        if (depth + f() > max_depth) {
            return false;
        }
        if (check()) {
            return true;
        }

        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                for (int k = r + 1; k < n; k++) {
                    System.arraycopy(q, 0, w[depth], 0, n);
                    int x, y;
                    for (x = r + 1, y = l; x <= k; x++, y++) {
                        q[y] = w[depth][x];
                    }
                    for (x = l; x <= r; x++, y++) {
                        q[y] = w[depth][x];
                    }
                    if (dfs(depth + 1, max_depth)) {
                        return true;
                    }
                    System.arraycopy(w[depth], 0, q, 0, n);
                }
            }
        }
        return false;
    }

    private boolean check() {
        for (int i = 0; i < n; i++) {
            if (q[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    private int f() {
        int res = 0;
        for (int i = 0; i + 1 < n; i++) {
            if (q[i + 1] != q[i] + 1) {
                res++;
            }
        }
        // 向上取整
        return (res + 2) / 3;
    }

}
