package ox14;

import common.io.InputReader;
import common.io.OutputWriter;

public class BunnyAndBunny {
    long[] h;
    long[] p;
    int mod = (int) (1e9 + 7);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s = in.nextString();
        int n = s.length();
        int P = 131;
        h = new long[n + 1];
        p = new long[n + 1];
        p[0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            h[i] = (h[i - 1] * P + s.charAt(i - 1)) % mod;
            p[i] = p[i - 1] * P % mod;
        }
        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int l1 = in.nextInt();
            int r1 = in.nextInt();
            int l2 = in.nextInt();
            int r2 = in.nextInt();
            if (get(l1, r1) == get(l2, r2)) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
    }

    int get(int l, int r) {
        return (int) ((h[r] - h[l - 1] * p[r - l + 1] % mod + mod) % mod);
    }
}
