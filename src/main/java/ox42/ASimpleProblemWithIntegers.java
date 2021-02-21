package ox42;

import common.io.InputReader;
import common.io.OutputWriter;

public class ASimpleProblemWithIntegers {
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int[] a = in.nextIntArray(n);
        long[] sumA = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sumA[i + 1] = sumA[i] + a[i];
        }
        for (int i = 0; i < m; i++) {
            char c = in.nextCharacter();
            if (c == 'Q') {
                int x = in.nextInt();
                int y = in.nextInt();
                out.println(sumA[y] - sumA[x - 1] + ask(y) - ask(x - 1));
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                int d = in.nextInt();
                add(l, d);
                add(r + 1, -d);
                add2(l, d);
                add2(r + 1, -d);
            }
        }

    }

    int N = 100005;
    long[] t = new long[N];
    long[] t2 = new long[N];

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t[i] += c;
        }
    }

    long sum(int x) {
        long res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
        }
        return res;
    }

    void add2(int x, int c) {
        for (int i = x; i <= n; i += lowbit(i)) {
            t2[i] += x * c;
        }
    }

    long sum2(int x) {
        long res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t2[i];
        }
        return res;
    }

    long ask(int x) {
        return (x + 1) * sum(x) - sum2(x);
    }
}
