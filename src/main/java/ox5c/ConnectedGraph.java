package ox5c;

import common.io.InputReader;
import common.io.OutputWriter;

import java.math.BigInteger;

public class ConnectedGraph {
    int n;
    int N = 55;
    BigInteger[] facts = new BigInteger[N];
    BigInteger[] f = new BigInteger[N];

    {
        facts[0] = BigInteger.valueOf(1);
        for (int i = 1; i < N; i++) {
            facts[i] = facts[i - 1].multiply(BigInteger.valueOf(i));
        }
        f[1] = BigInteger.valueOf(1);
        for (int i = 2; i < N; i++) {
            f[i] = BigInteger.valueOf(2).pow(i * (i - 1) / 2);
            for (int j = 1; j < i; j++) {
                f[i] = f[i].subtract(f[j].multiply(c(i - 1, j - 1)).multiply(BigInteger.valueOf(2).pow((i - j) * (i - j - 1) / 2)));
            }
        }
    }

    private BigInteger c(int i, int j) {
        return facts[i].divide(facts[j]).divide(facts[i - j]);
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        out.println(f[n]);
    }
}
