package ox30;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimeDistance {
    int cnt;
    int N = (int) (5e5 + 10);
    int[] primes = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        getPrimes(N);
        try {
            while (true) {
                int l = in.nextInt();
                int r = in.nextInt();
                Set<Long> set = new HashSet<>();
                for (int i = 0; i < cnt; i++) {
                    long p = primes[i];
                    if (p > r) {
                        break;
                    }
                    for (long j = Math.max(2, (l + p - 1) / p); j <= r / p; j++) {
                        set.add(j * p);
                    }
                }
                List<Integer> list = new ArrayList<>();
                for (long i = Math.max(2, l); i <= r; i++) {
                    if (set.contains(i)) {
                        continue;
                    }
                    list.add((int) i);
                }
                if (list.size() < 2) {
                    out.println("There are no adjacent primes.");
                } else {
                    int minIdx = 0;
                    int maxIdx = 0;
                    for (int i = 0; i < list.size() - 1; i++) {
                        int d = list.get(i + 1) - list.get(i);
                        if (d < list.get(minIdx + 1) - list.get(minIdx)) {
                            minIdx = i;
                        }
                        if (d > list.get(maxIdx + 1) - list.get(maxIdx)) {
                            maxIdx = i;
                        }
                    }
                    out.println(list.get(minIdx) + "," + list.get(minIdx + 1) + " are closest, " + list.get(maxIdx) + "," + list.get(maxIdx + 1) + " are most distant.");
                }
            }
        } catch (UnknownError e) {
            out.close();
        }

    }

    void getPrimes(int n) {
        boolean[] vis = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            if (!vis[i]) {
                primes[cnt++] = i;
            }
            for (int j = 0; primes[j] <= n / i; j++) {
                vis[primes[j] * i] = true;
                if (i % primes[j] == 0) {
                    break;
                }
            }
        }
    }
}
