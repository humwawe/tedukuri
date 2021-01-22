package ox31;

import common.io.InputReader;
import common.io.OutputWriter;

public class FactorialDecomposition {
    int N = (int) (1e6 + 10);
    int[] primes = new int[N];
    int cnt = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        getPrime(n);
        for (int i = 0; i < cnt; i++) {
            int p = primes[i];
            int s = 0;
            int tmp = n;
            while (tmp > 0) {
                s += tmp / p;
                tmp /= p;
            }
            out.println(p + " " + s);
        }
    }

    private void getPrime(int n) {
        boolean[] vis = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            if (!vis[i]) {
                primes[cnt++] = i;
            }
            for (int j = 0; primes[j] * i <= n; j++) {
                vis[primes[j] * i] = true;
                if (i % primes[j] == 0) {
                    break;
                }
            }
        }
    }
}
