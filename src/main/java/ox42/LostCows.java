package ox42;

import common.io.InputReader;
import common.io.OutputWriter;

public class LostCows {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] a = new int[n];
        for (int i = 1; i < n; i++) {
            a[i] = in.nextInt();
        }
        init();
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int l = 1;
            int r = n;
            while (l < r) {
                int mid = l + r >> 1;
                if (sum(mid) >= a[i] + 1) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            res[i] = l;
            add(l, -1);
        }
        for (int i = 0; i < n; i++) {
            out.println(res[i]);
        }
    }

    int N = (int) (1e5 + 5);
    int[] t = new int[N];

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t[i] += c;
        }
    }

    void init() {
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + 1;
            t[i] = sum[i] - sum[i - lowbit(i)];
        }
    }

    int sum(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
        }
        return res;
    }
}
