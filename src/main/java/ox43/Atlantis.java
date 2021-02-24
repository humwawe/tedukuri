package ox43;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Atlantis {
    int n;
    Node[] tr;
    Point[] a;
    double[] b;
    int len;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        while (true) {
            n = in.nextInt();
            if (n == 0) {
                return;
            }
            out.printf("Test case #%d\n", testNumber++);
            a = new Point[n * 2];
            b = new double[n * 2];
            tr = new Node[n * 8];
            for (int i = 0; i < n; i++) {
                double x1 = in.nextDouble();
                double y1 = in.nextDouble();
                double x2 = in.nextDouble();
                double y2 = in.nextDouble();
                a[i] = new Point(x1, y1, y2, 1);
                a[i + n] = new Point(x2, y1, y2, -1);
                b[i] = y1;
                b[i + n] = y2;
            }

            Arrays.sort(a, (x, y) -> {
                if (x.x > y.x) {
                    return 1;
                } else if (x.x - y.x < 0) {
                    return -1;
                } else {
                    return 0;
                }
            });
            len = 0;
            discrete();
            build(1, 1, len - 1);
            modify(1, mapping(a[0].y1), mapping(a[0].y2) - 1, a[0].add);
            double res = 0;
            double lenSum = raw(tr[1].r + 1) - raw(tr[1].l);
            for (int i = 1; i < 2 * n; i++) {
                double len = lenSum;
                if (tr[1].min == 0) {
                    len -= tr[1].minLen;
                }
                res += len * (a[i].x - a[i - 1].x);
                modify(1, mapping(a[i].y1), mapping(a[i].y2) - 1, a[i].add);
            }
            out.printf("Total explored area: %.2f\n\n", res);
        }

    }

    void discrete() {
        Arrays.sort(b);
        for (int i = 0; i < b.length; i++) {
            if (i == 0 || b[i] != b[i - 1]) {
                b[len++] = b[i];
            }
        }
    }

    int mapping(double x) {
        return Arrays.binarySearch(b, 0, len, x) + 1;
    }

    double raw(int x) {
        return b[x - 1];
    }

    void pushUp(int u) {
        tr[u].min = Math.min(tr[u << 1].min, tr[u << 1 | 1].min);
        double minLen = 0;
        if (tr[u].min == tr[u << 1].min) {
            minLen += tr[u << 1].minLen;
        }
        if (tr[u].min == tr[u << 1 | 1].min) {
            minLen += tr[u << 1 | 1].minLen;
        }
        tr[u].minLen = minLen;
    }

    void pushDown(int u) {
        if (tr[u].add != 0) {
            tr[u << 1].add += tr[u].add;
            tr[u << 1].min += tr[u].add;
            tr[u << 1 | 1].add += tr[u].add;
            tr[u << 1 | 1].min += tr[u].add;
            tr[u].add = 0;
        }
    }

    // 从u开始，构建[l,r]的树，w[i]存每个节点的值(1开始)
    void build(int u, int l, int r) {
        tr[u] = new Node(l, r, 0, raw(r + 1) - raw(l), 0);
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }


    void modify(int u, int l, int r, int d) {
        if (tr[u].l >= l && tr[u].r <= r) {
            tr[u].min += d;
            tr[u].add += d;
            return;
        }
        pushDown(u);
        int mid = tr[u].l + tr[u].r >> 1;
        if (l <= mid) {
            modify(u << 1, l, r, d);
        }
        if (r > mid) {
            modify(u << 1 | 1, l, r, d);
        }
        pushUp(u);
    }

    class Point {
        double x, y1, y2;
        int add;

        public Point(double x, double y1, double y2, int add) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.add = add;
        }
    }

    class Node {
        int l;
        int r;
        int min;
        double minLen;
        // 懒标记，给儿子加上add，不包括自己
        int add;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, int min, double minLen, int add) {
            this.l = l;
            this.r = r;
            this.min = min;
            this.minLen = minLen;
            this.add = add;
        }
    }
}
