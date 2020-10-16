package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.HashSet;
import java.util.Set;

public class Matrix {
    int P = 131;
    int N = 1010;
    int M = N * N;
    long[][] h = new long[N][N];
    long[] p = new long[M];
    int mod = (int) (1e9 + 7);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        p[0] = 1;
        for (int i = 1; i <= n * m; i++) {
            p[i] = p[i - 1] * P % mod;
        }
        for (int i = 1; i <= n; i++) {
            String s = in.nextString();
            for (int j = 1; j <= m; j++) {
                h[i][j] = (h[i][j - 1] * P + s.charAt(j - 1) - '0') % mod;
            }
        }
        Set<Long> set = new HashSet<>();
        for (int i = b; i <= m; i++) {
            long sum = 0;
            int l = i - b + 1;
            int r = i;
            for (int j = 1; j <= n; j++) {
                sum = (sum * p[b] % mod + get(h[j], l, r)) % mod;
                if (j > a) {
                    sum = (sum - get(h[j - a], l, r) * p[a * b] % mod + mod) % mod;
                }
                if (j >= a) {
                    set.add(sum);
                }
            }
        }

        int k = in.nextInt();
        while (k-- > 0) {
            long sum = 0;
            for (int i = 0; i < a; i++) {
                String str = in.nextString();
                for (int j = 0; j < b; j++) {
                    sum = (sum * P + str.charAt(j) - '0') % mod;
                }
            }
            if (set.contains(sum)) {
                out.println(1);
            }else {
                out.println(0);
            }
        }

    }

    int get(long[] f, int l, int r) {
        return (int) ((f[r] - f[l - 1] * p[r - l + 1] % mod + mod) % mod);
    }
}
