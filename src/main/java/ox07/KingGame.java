package ox07;

import common.io.InputReader;
import common.io.OutputWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public class KingGame {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] king = new int[2];
        king[0] = in.nextInt();
        king[1] = in.nextInt();
        int[][] a = new int[n][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        Arrays.sort(a, Comparator.comparingInt(x -> x[0] * x[1]));
        BigInteger res = new BigInteger("0");
        BigInteger mul = new BigInteger(String.valueOf(king[0]));
        for (int i = 0; i < n; i++) {
            BigInteger tmp = mul.divide(new BigInteger(String.valueOf(a[i][1])));
            if (res.compareTo(tmp) < 0) {
                res = tmp;
            }
            mul = mul.multiply(new BigInteger(String.valueOf(a[i][0])));
        }
        out.println(res.toString());
    }
}
