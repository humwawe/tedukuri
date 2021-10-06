package ox68;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Ants {
    int n;
    int N = 105;
    double[][] w = new double[N][N];
    double[] la = new double[N];
    double[] lb = new double[N];
    boolean[] va = new boolean[N];
    boolean[] vb = new boolean[N];
    double[] upd = new double[N];
    //    int[] last = new int[N];
    int[] match = new int[N];
    Point[] a = new Point[N];
    Point[] b = new Point[N];
    double eps = 1e-10;
    double inf = 1e10;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 1; i <= n; i++) {
            b[i] = new Point(in.nextInt(), in.nextInt());
        }
        for (int i = 1; i <= n; i++) {
            a[i] = new Point(in.nextInt(), in.nextInt());
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                w[i][j] = -Math.sqrt((a[i].x - b[j].x) * (a[i].x - b[j].x) + (a[i].y - b[j].y) * (a[i].y - b[j].y));
            }
        }
        km();
        for (int i = 1; i <= n; i++) {
            out.println(match[i]);
        }
    }

    void km() {
        for (int i = 1; i <= n; i++) {
            la[i] = -inf;
            lb[i] = 0;
            for (int j = 1; j <= n; j++) {
                la[i] = Math.max(la[i], w[i][j]);
            }
        }
        for (int i = 1; i <= n; i++) {
            // 直到左部顶点找到匹配
            while (true) {
                Arrays.fill(va, false);
                Arrays.fill(vb, false);
                for (int j = 1; j <= n; j++) {
                    upd[j] = inf;
                }
                if (dfs(i)) {
                    break;
                }
                double delta = inf;
                for (int j = 1; j <= n; j++) {
                    if (!vb[j]) {
                        delta = Math.min(delta, upd[j]);
                    }
                }
                // 修改顶标
                for (int j = 1; j <= n; j++) {
                    if (va[j]) {
                        la[j] -= delta;
                    }
                    if (vb[j]) {
                        lb[j] += delta;
                    }
                }
            }
        }
    }

    boolean dfs(int x) {
        // 在交错树中
        va[x] = true;
        for (int y = 1; y <= n; y++) {
            if (!vb[y]) {
                // 相等子图
                if (Math.abs(la[x] + lb[y] - w[x][y]) < eps) {
                    // 在交错树中
                    vb[y] = true;
                    if (match[y] == 0 || dfs(match[y])) {
                        match[y] = x;
                        return true;
                    }
                } else {
                    upd[y] = Math.min(upd[y], la[x] + lb[y] - w[x][y]);
                }
            }
        }
        return false;
    }


//    void km2() {
//        for (int i = 1; i <= n; i++) {
//            la[i] = -inf;
//            lb[i] = 0;
//            for (int j = 1; j <= n; j++) {
//                la[i] = Math.max(la[i], w[i][j]);
//            }
//        }
//        for (int i = 1; i <= n; i++) {
//            Arrays.fill(va, false);
//            Arrays.fill(vb, false);
//            Arrays.fill(upd, inf);
//            int start = 0;
//            match[0] = i;
//            while (match[start] != 0) {
//                if (dfs2(match[start], start)) {
//                    break;
//                }
//                double delta = inf;
//                for (int j = 1; j <= n; j++) {
//                    if (!vb[j] && delta > upd[j]) {
//                        delta = upd[j];
//                        start = j;
//                    }
//                }
//                // 修改顶标
//                for (int j = 1; j <= n; j++) {
//                    if (va[j]) {
//                        la[j] -= delta;
//                    }
//                    if (vb[j]) {
//                        lb[j] += delta;
//                    } else {
//                        upd[j] -= delta;
//                    }
//                }
//                vb[start] = true;
//            }
//            while (start != 0) {
//                match[start] = match[last[start]];
//                start = last[start];
//            }
//        }
//    }
//
//    boolean dfs2(int x, int fa) {
//        va[x] = true;
//        for (int y = 1; y <= n; y++) {
//            if (!vb[y]) {
//                // 相等子图
//                if (Math.abs(la[x] + lb[y] - w[x][y]) < eps) {
//                    vb[y] = true;
//                    last[y] = fa;
//                    if (match[y] == 0 || dfs2(match[y], y)) {
//                        match[y] = x;
//                        return true;
//                    }
//                } else {
//                    if (upd[y] > la[x] + lb[y] - w[x][y] + eps) {
//                        upd[y] = la[x] + lb[y] - w[x][y];
//                        last[y] = fa;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
