package ox58;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TheBattleOfChibi {
    int N = 1010;
    int mod = (int) (1e9 + 7);
    int[] a, b;
    int n, m;
    int len;
    long[] t = new long[N];
    int[][] f = new int[N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        a = new int[n];
        b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            b[i] = a[i];
        }
        Arrays.sort(b);
        len = 0;
        for (int i = 0; i < b.length; i++) {
            if (i == 0 || b[i] != b[i - 1]) {
                b[len++] = b[i];
            }
        }
        for (int i = 0; i < n; i++) {
            a[i] = find(a[i]) + 5;
        }
        for (int i = 1; i <= m; i++) {
            Arrays.fill(t, 0);
            if (i == 1) {
                add(1, 1);
            }
            for (int j = 0; j < n; j++) {
                f[i][j] = (int) (sum(a[j] - 1) % mod);
                add(a[j], f[i - 1][j]);
            }
        }
        long res = 0;
        for (int i = 0; i < n; i++) {
            res = (res + f[m][i]) % mod;
        }
        out.printf("Case #%d: %d\n", testNumber, res);
    }

    int lowbit(int x) {
        return x & -x;
    }

    void add(int x, int c) {
        for (int i = x; i <= n + 5; i += lowbit(i)) {
            t[i] += c;
            t[i] %= mod;
        }
    }

    long sum(int x) {
        long res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            res += t[i];
            res %= mod;
        }
        return res;
    }

    public int find(int x) {
        return Arrays.binarySearch(b, 0, len, x);
    }
}
