package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Mokia {
    int s, w;
    int len = 0;
    int N = 200011;
    int W = 2000011;
    Node[] a = new Node[N];
    Node[] b = new Node[N];
    int tot = 0;
    int[] res = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        s = in.nextInt();
        w = in.nextInt();
        int t;
        int cnt = 0;
        while ((t = in.nextInt()) != 3) {
            if (t == 1) {
                int x = in.nextInt();
                int y = in.nextInt();
                int k = in.nextInt();
                a[++tot] = new Node(x, y, k, 0, tot);
            } else {
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();
                a[++tot] = new Node(--x1, --y1, 1, ++cnt, tot);
                a[++tot] = new Node(x1, y2, -1, cnt, tot);
                a[++tot] = new Node(x2, y1, -1, cnt, tot);
                a[++tot] = new Node(x2, y2, 1, cnt, tot);
            }
        }
        Arrays.sort(a, 1, tot + 1, (x, y) -> {
            if (x.x == y.x) {
                if (x.y == y.y) {
                    return x.o - y.o;
                } else {
                    return x.y - y.y;
                }
            } else {
                return x.x - y.x;
            }
        });

        cdq(1, tot);
        for (int i = 1; i <= cnt; i++) {
            out.println(res[i]);
        }
    }

    private void cdq(int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + r >> 1;
        int t1 = l;
        int t2 = mid + 1;
        for (int i = l; i <= r; i++) {
            if (a[i].id <= mid && a[i].o == 0) {
                add(a[i].y, a[i].z);
            } else if (a[i].id > mid && a[i].o != 0) {
                res[a[i].o] += sum(a[i].y) * a[i].z;
            }
        }
        for (int i = l; i <= r; i++) {
            if (a[i].id <= mid && a[i].o == 0) {
                add(a[i].y, -a[i].z);
            }
        }
        for (int i = l; i <= r; i++) {
            if (a[i].id <= mid) {
                b[t1++] = a[i];
            } else {
                b[t2++] = a[i];
            }
        }
        for (int i = l; i <= r; i++) {
            a[i] = b[i];
        }
        cdq(l, mid);
        cdq(mid + 1, r);
    }

    int[] t = new int[W];

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i <= W; i += lowbit(i)) {
            t[i] += c;
        }
    }

    int sum(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
        }
        return res;
    }

    class Node {
        int x, y, z, o, id;

        public Node(int x, int y, int z, int o, int id) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.o = o;
            this.id = id;
        }
    }
}
