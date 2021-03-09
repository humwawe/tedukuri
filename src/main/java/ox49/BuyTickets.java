package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

public class BuyTickets {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        init();
        int[] tmp = new int[2 * n];
        for (int i = 0; i < n; i++) {
            tmp[i] = in.nextInt();
            tmp[i + n] = in.nextInt();
        }
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int x = tmp[i];
            int y = tmp[i + n];
            int l = 0;
            int r = n;
            while (l < r) {
                int mid = l + r >> 1;
                if (sum(mid) >= x + 1) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            res[l - 1] = y;
            add(l, -1);
        }
        for (int i = 0; i < n; i++) {
            out.print(res[i] + " ");
        }
        out.println();
    }

    int N = 200005;
    int[] t = new int[N];

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
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

    void init() {
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + 1;
            t[i] = sum[i] - sum[i - lowbit(i)];
        }
    }
}
