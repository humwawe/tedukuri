package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Picture {
    int n;
    Node[] tr;
    Point[] a;
    int[] b;
    int len;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = new Point[n * 2];
        b = new int[n * 2];
        tr = new Node[n * 8];
        for (int i = 0; i < n; i++) {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            a[i] = new Point(x1, y1, y2, 1);
            a[i + n] = new Point(x2, y1, y2, -1);
            b[i] = y1;
            b[i + n] = y2;
        }
        Arrays.sort(a, (x, y) -> {
            if (x.x == y.x) {
                return y.add - x.add;
            } else {
                return x.x - y.x;
            }
        });
        len = 0;
        discrete();
        build(1, 1, len - 1);
        int res = 0;
        int last = 0;
        for (int i = 0; i < n * 2; i++) {
            int sy = mapping(a[i].y1);
            int ey = mapping(a[i].y2);
            update(1, 1, len - 1, sy, ey - 1, a[i].add);
            if (i != 2 * n - 1) {
                res += (a[i + 1].x - a[i].x) * 2 * tr[1].num;
            }
            res += Math.abs(tr[1].len - last);
            last = tr[1].len;
        }
        out.println(res);
    }

    void pushUp(int u) {
        if (tr[u].cnt != 0) {
            tr[u].len = b[tr[u].r] - b[tr[u].l - 1];
            tr[u].num = tr[u].lc = tr[u].rc = 1;
        } else if (tr[u].l == tr[u].r) {
            tr[u].len = tr[u].num = tr[u].lc = tr[u].rc = 0;
        } else {
            tr[u].len = tr[u * 2].len + tr[u * 2 + 1].len;
            tr[u].num = tr[u * 2].num + tr[u * 2 + 1].num;
            if (tr[u * 2].rc != 0 && tr[u * 2 + 1].lc != 0) {
                --tr[u].num;
            }
            tr[u].lc = tr[u * 2].lc;
            tr[u].rc = tr[u * 2 + 1].rc;
        }
    }

    void update(int u, int l, int r, int y1, int y2, int add) {
        if (l >= y1 && r <= y2) {
            tr[u].cnt += add;
            pushUp(u);
            return;
        }
        int mid = (l + r) >> 1;
        if (y1 <= mid) {
            update(u << 1, l, mid, y1, y2, add);
        }
        if (y2 > mid) {
            update(u << 1 | 1, mid + 1, r, y1, y2, add);
        }
        pushUp(u);
    }

    void build(int u, int l, int r) {
        tr[u] = new Node(l, r);
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
        pushUp(u);
    }


    int mapping(int x) {
        return Arrays.binarySearch(b, 0, len, x) + 1;
    }

    void discrete() {
        Arrays.sort(b);
        for (int i = 0; i < b.length; i++) {
            if (i == 0 || b[i] != b[i - 1]) {
                b[len++] = b[i];
            }
        }
    }

    class Point {
        int x, y1, y2;
        int add;

        public Point(int x, int y1, int y2, int add) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.add = add;
        }
    }

    class Node {
        int l;
        int r;
        int cnt;
        int len;
        int num;
        int lc, rc;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }
}
