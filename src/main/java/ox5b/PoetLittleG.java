package ox5b;

import common.io.InputReader;
import common.io.OutputWriter;

public class PoetLittleG {
    int N = 100005;
    String[] str = new String[N];
    int[] s = new int[N];
    int[] g = new int[N];
    int[] res = new int[N];
    int[] q = new int[N];
    int[] L = new int[N];
    double[] f = new double[N];
    int hh, tt;
    int n, l, p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        l = in.nextInt();
        p = in.nextInt();
        for (int i = 1; i <= n; i++) {
            str[i] = in.nextString();
            s[i] = s[i - 1] + str[i].length() + 1;
        }
        hh = tt = 1;
        q[1] = 0;
        for (int i = 1; i <= n; i++) {
            while (hh < tt && L[hh] <= i) {
                hh++;
            }
            g[i] = q[hh];
            f[i] = work(i, g[i]);
            while (hh < tt && L[tt - 1] >= find(q[tt], i)) {
                tt--;
            }
            L[tt] = find(q[tt], i);
            q[++tt] = i;
        }
        if (f[n] > 1e18) {
            out.println("Too hard to arrange");
        } else {
            out.println((long) f[n]);
            int top = 0;
            for (int i = n; i > 0; i = g[i]) {
                res[++top] = g[i];
            }
            res[0] = n;
            while (top-- > 0) {
                int l = res[top + 1] + 1;
                int r = res[top];
                for (int i = l; i < r; i++) {
                    out.print(str[i] + " ");
                }
                out.println(str[r]);
            }
        }
        out.println("--------------------");

    }

    double ksm(double x) {
        int base = p;
        double ans = 1;
        while (base > 0) {
            if ((base & 1) == 1) {
                ans = ans * x;
            }
            x = x * x;
            base >>= 1;
        }
        return ans;
    }

    double work(int i, int j) {
        return f[j] + ksm(Math.abs(s[i] - s[j] - 1 - l));
    }

    int find(int x, int y) {
        int l = x;
        int r = n + 1;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (work(mid, x) >= work(mid, y)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

}
