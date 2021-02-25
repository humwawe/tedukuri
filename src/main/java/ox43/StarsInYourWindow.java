package ox43;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class StarsInYourWindow {
    int n, w, h;
    long[][] a;
    long[] b;
    int len;
    Node[] tr;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        w = in.nextInt();
        h = in.nextInt();
        a = new long[n * 2][4];
        b = new long[n * 2];
        for (int i = 0; i < n; i++) {
            long x = in.nextLong();
            long y = in.nextLong();
            int c = in.nextInt();
            a[i] = new long[]{x, y, y + h - 1, c};
            a[i + n] = new long[]{x + w, y, y + h - 1, -c};
            b[i] = y;
            b[i + n] = y + h - 1;
        }
        Arrays.sort(a, (x, y) -> {
            if (x[0] == y[0]) {
                return conv(x[3] - y[3]);
            } else {
                return conv(x[0] - y[0]);
            }
        });
        len = 0;
        discrete();
        tr = new Node[len * 4];
        build(1, 1, len);
        long res = 0;
        for (int i = 0; i < 2 * n; i++) {
            modify(1, mapping(a[i][1]), mapping(a[i][2]), (int) a[i][3]);
            res = Math.max(res, tr[1].max);

        }
        out.println(res);
    }

    private int conv(long l) {
        if (l > 0) {
            return 1;
        } else if (l < 0) {
            return -1;
        } else {
            return 0;
        }
    }


    void modify(int u, int l, int r, int d) {
        if (tr[u].l >= l && tr[u].r <= r) {
            tr[u].max += d;
            tr[u].add += d;
            return;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (l <= mid) {
            modify(u << 1, l, r, d);
        }
        if (r > mid) {
            modify(u << 1 | 1, l, r, d);
        }
        pushUp(u);
    }

    void pushUp(int u) {
        tr[u].max = Math.max(tr[u << 1].max, tr[u << 1 | 1].max) + tr[u].add;
    }

    int mapping(long x) {
        return Arrays.binarySearch(b, 0, len, x) + 1;
    }

    void build(int u, int l, int r) {
        tr[u] = new Node(l, r, 0, 0);
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        build(u << 1, l, mid);
        build(u << 1 | 1, mid + 1, r);
    }

    void discrete() {
        Arrays.sort(b);
        for (int i = 0; i < b.length; i++) {
            if (i == 0 || b[i] != b[i - 1]) {
                b[len++] = b[i];
            }
        }
    }

    class Node {
        int l;
        int r;
        int max;
        int add;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public Node(int l, int r, int max, int add) {
            this.l = l;
            this.r = r;
            this.max = max;
            this.add = add;
        }
    }
}
