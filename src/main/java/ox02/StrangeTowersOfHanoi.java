package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class StrangeTowersOfHanoi {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[] d = new int[15];
        int[] f = new int[15];
        Arrays.fill(f, 0x3f3f3f3f);
        f[1] = 1;
        for (int i = 1; i <= 12; i++) {
            d[i] = d[i - 1] * 2 + 1;
            for (int j = 1; j < i; j++) {
                f[i] = Math.min(f[i], 2 * f[j] + d[i - j]);
            }
        }
        for (int i = 1; i <= 12; i++) {
            out.println(f[i]);
        }
    }
}
