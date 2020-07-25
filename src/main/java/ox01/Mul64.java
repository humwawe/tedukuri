package ox01;

import common.io.InputReader;
import common.io.OutputWriter;

import java.math.BigInteger;

public class Mul64 {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        long a = in.nextLong();
        long b = in.nextLong();
        long p = in.nextLong();
        long res = 0;
        while (b != 0) {
            if ((b & 1) == 1) {
                res = (res + a) % p;
            }
            a = a * 2 % p;
            b >>= 1;
        }
        out.println(res);
    }

    public void solve2(int testNumber, InputReader in, OutputWriter out) {
        String a = in.nextString();
        String b = in.nextString();
        String p = in.nextString();
        out.println(new BigInteger(a).multiply(new BigInteger(b)).mod(new BigInteger(p)));
    }
}
