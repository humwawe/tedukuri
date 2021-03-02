package ox47;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class AngelDoll {
    int inf = 0x3f3f3f3f;
    int n, m;
    int[][] a, buf;
    int maxY;
    int[] res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = new int[n + m + 1][3];
        buf = new int[n + m + 1][3];
        res = new int[n + m + 1];
        Arrays.fill(res, inf);
        maxY = 0;
        for (int i = 1; i <= n; i++) {
            a[i][0] = 1;
            a[i][1] = in.nextInt();
            a[i][2] = in.nextInt();
            maxY = Math.max(maxY, a[i][2]);
        }
        for (int i = 1; i <= m; i++) {
            a[i + n][0] = in.nextInt();
            a[i + n][1] = in.nextInt();
            a[i + n][2] = in.nextInt();
            maxY = Math.max(maxY, a[i + n][2]);
        }
        t = new int[++maxY];
        Arrays.fill(t, -inf);

        cdq(1, n + m);

        for (int i = 1; i <= n + m; i++) {
            if (a[i][0] == 2) {
                out.println(res[i]);
            }
        }
    }

    private void cdq(int l, int r) {
        int mid = l + r >> 1;
        if (l < mid) {
            cdq(l, mid);
        }
        if (mid + 1 < r) {
            cdq(mid + 1, r);
        }
        int tot = 0;
        for (int i = l; i <= r; i++) {
            if ((i <= mid && a[i][0] == 1) || (i > mid && a[i][0] == 2)) {
                buf[++tot][1] = a[i][1];
                buf[tot][2] = a[i][2];
                buf[tot][0] = i;
            }
        }
        Arrays.sort(buf, 1, tot + 1, (x, y) -> {
            if (x[1] == y[1]) {
                return x[2] - y[2];
            }
            return x[1] - y[1];
        });
        work(1, tot + 1, 1, 1, 1);
        work(1, tot + 1, 1, 1, -1);
        work(tot, 0, -1, -1, 1);
        work(tot, 0, -1, -1, -1);

    }

    private void work(int st, int ed, int w, int dx, int dy) {
        for (int i = st; i != ed; i += w) {
            int num = dx * buf[i][1] + dy * buf[i][2];
            int y = dy == -1 ? maxY - buf[i][2] : buf[i][2];
            if (a[buf[i][0]][0] == 1) {

                add(y, num);
            } else {
                res[buf[i][0]] = Math.min(res[buf[i][0]], Math.abs(num - ask(y)));
            }
        }
        for (int i = st; i != ed; i += w) {
            int y = dy == -1 ? maxY - buf[i][2] : buf[i][2];
            if (a[buf[i][0]][0] == 1) {
                reset(y);
            }
        }
    }

    int[] t;

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i < maxY; i += lowbit(i)) {
            t[i] = Math.max(t[i], c);
        }
    }

    int ask(int x) {
        int res = -inf;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res = Math.max(res, t[i]);
        }
        return res;
    }

    void reset(int y) {
        for (; y < maxY; y += lowbit(y)) {
            t[y] = -inf;
        }
    }
}
