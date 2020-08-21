package ox01;

import common.io.InputReader;
import common.io.OutputWriter;

public class APowB {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        int p = in.nextInt();
        long res = 1 % p;
        while (b != 0) {
            if ((b & 1) == 1) {
                res = res * a % p;
            }
            a = (int) ((long) a * a % p);
            b = b >> 1;
        }
        out.println(res);
    }
}
