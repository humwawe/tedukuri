package ox14;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class SuffixArray {
    long[] h;
    long[] p;
    int mod = (int) (1e9 + 7);
    int inf = 0x3f3f3f3f;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s = in.nextString();
        n = s.length();
        int P = 131;
        h = new long[n + 1];
        p = new long[n + 1];
        p[0] = 1;
        Integer[] sa = new Integer[n + 1];
        for (int i = 1; i <= s.length(); i++) {
            sa[i] = i;
            h[i] = (h[i - 1] * P + s.charAt(i - 1)) % mod;
            p[i] = p[i - 1] * P % mod;
        }
        Arrays.sort(sa, 1, n + 1, (x, y) -> {
            int k = prefixLength(x, y);
            int av = x + k > n ? (-inf) : s.charAt(x + k - 1);
            int bv = y + k > n ? (-inf) : s.charAt(y + k - 1);
            return av - bv;
        });
        for (int i = 1; i <= n; ++i) {
            out.print(sa[i] - 1 + " ");
        }
        out.print("\n0 ");
        for (int i = 2; i <= n; ++i) {
            out.print(prefixLength(sa[i], sa[i - 1]) + " ");
        }
    }

    private int prefixLength(int a, int b) {
        int l = 0;
        int r = Math.min(n - a + 1, n - b + 1);
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (get(a, a + mid - 1) == get(b, b + mid - 1)) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    int get(int l, int r) {
        return (int) ((h[r] - h[l - 1] * p[r - l + 1] % mod + mod) % mod);
    }
}
