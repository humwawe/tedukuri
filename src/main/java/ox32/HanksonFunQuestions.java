package ox32;

import common.io.InputReader;
import common.io.OutputWriter;

public class HanksonFunQuestions {
    int N = 45000, M = 50;
    int[] primes = new int[N];
    int cnt;

    int[][] factor = new int[M][2];
    int cntF;
    int[] divider = new int[N];
    int cntD;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        getPrimes(N);
        int n = in.nextInt();
        while (n-- > 0) {
            int a0 = in.nextInt();
            int a1 = in.nextInt();
            int b0 = in.nextInt();
            int b1 = in.nextInt();
            divide(b1);
            cntD = 0;
            dfs(0, 1);

            int res = 0;
            for (int i = 0; i < cntD; i++) {
                int x = divider[i];
                if (gcd(x, a0) == a1 && (long) x * b0 / gcd(x, b0) == b1) {
                    res++;
                }
            }
            out.println(res);
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private void dfs(int u, int s) {
        if (u == cntF) {
            divider[cntD++] = s;
            return;
        }
        for (int i = 0; i <= factor[u][1]; i++) {
            dfs(u + 1, s);
            s *= factor[u][0];
        }
    }

    private void divide(int d) {
        cntF = 0;
        for (int i = 0; primes[i] <= d / primes[i]; i++) {
            int p = primes[i];
            if (d % p == 0) {
                int s = 0;
                while (d % p == 0) {
                    d /= p;
                    s++;
                }
                factor[cntF][0] = p;
                factor[cntF][1] = s;
                cntF++;
            }
        }
        if (d > 1) {
            factor[cntF][0] = d;
            factor[cntF][1] = 1;
            cntF++;
        }
    }

    void getPrimes(int n) {
        boolean[] st = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            if (!st[i]) {
                primes[cnt++] = i;
            }
            for (int j = 0; primes[j] <= n / i; j++) {
                st[primes[j] * i] = true;
                if (i % primes[j] == 0) {
                    break;
                }
            }
        }
    }
}
