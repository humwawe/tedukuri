package ox42;

import common.io.InputReader;
import common.io.OutputWriter;

public class LoulanTotem {
    int n;
    int N = 200005;
    int[] t;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] left = new int[n];
        int[] right = new int[n];
        t = new int[N];
        add(a[n - 1], 1);
        for (int i = n - 2; i >= 0; i--) {
            right[i] = sum(a[i] - 1);
            add(a[i], 1);
        }

        t = new int[N];
        add(a[0], 1);
        for (int i = 1; i < n; i++) {
            left[i] = sum(a[i] - 1);
            add(a[i], 1);
        }
        long res1 = 0;
        long res2 = 0;
        for (int i = 1; i < n - 1; i++) {
            res1 += (long) (i - left[i]) * (n - i - 1 - right[i]);
            res2 += (long) left[i] * right[i];
        }
        out.println(res1 + " " + res2);
    }

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
}
