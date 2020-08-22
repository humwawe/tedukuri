package ox14;

import common.io.InputReader;
import common.io.OutputWriter;

public class Palindrome {
    long[] h1;
    long[] h2;
    long[] p;
    int mod = (int) (1e9 + 7);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s = in.nextString();
        if (s.equals("END")) {
            return;
        }
        int n = s.length();
        int P = 131;
        h1 = new long[n + 2];
        h2 = new long[n + 2];
        p = new long[n + 1];
        p[0] = 1;
        for (int i = 1; i <= n; i++) {
            h1[i] = (h1[i - 1] * P + s.charAt(i - 1)) % mod;
            p[i] = p[i - 1] * P % mod;
        }
        for (int i = n; i > 0; i--) {
            h2[i] = (h2[i + 1] * P + s.charAt(i - 1)) % mod;
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            int l = 0;
            int r = Math.min(i - 1, n - i);
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (get1(i - mid, i - 1) == get2(i + 1, i + mid)) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }
            res = Math.max(res, l * 2 + 1);
            l = 0;
            r = Math.min(i - 1, n - i + 1);
            if (r * 2 <= res) {
                continue;
            }
            while (l < r) {
                int mid = l + r + 1 >> 1;
                if (get1(i - mid, i - 1) == get2(i, i + mid - 1)) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }
            res = Math.max(res, l * 2);
        }

        out.println(String.format("Case %d: %d", testNumber, res));
    }

    int get1(int l, int r) {
        return (int) ((h1[r] - h1[l - 1] * p[r - l + 1] % mod + mod) % mod);
    }

    int get2(int l, int r) {
        return (int) ((h2[l] - h2[r + 1] * p[r - l + 1] % mod + mod) % mod);
    }
}
