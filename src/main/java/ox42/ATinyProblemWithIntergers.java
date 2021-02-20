package ox42;

import common.io.InputReader;
import common.io.OutputWriter;

public class ATinyProblemWithIntergers {
    int n, m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int[] a = in.nextIntArray(n);
        for (int i = 0; i < m; i++) {
            char c = in.nextCharacter();
            if (c == 'Q') {
                int index = in.nextInt();
                out.println(a[index - 1] + sum(index));
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                int d = in.nextInt();
                add(l, d);
                add(r + 1, -d);
            }
        }
    }

    int N = 100005;
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
}
