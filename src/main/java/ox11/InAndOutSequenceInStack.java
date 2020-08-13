package ox11;

import common.io.InputReader;
import common.io.OutputWriter;

import java.math.BigInteger;

public class InAndOutSequenceInStack {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        out.println(c(2 * n, n).divide(BigInteger.valueOf(n + 1)));
    }

    private BigInteger c(int n, int m) {
        return cal(n - m + 1, n).divide(cal(1, m));
    }

    public BigInteger cal(int l, int r) {
        if (l == r) {
            return BigInteger.valueOf(l);
        }
        int m = (l * 3 + r * 4) / 7;
        return cal(l, m).multiply(cal(m + 1, r));
    }
}
