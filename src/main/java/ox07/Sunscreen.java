package ox07;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Sunscreen {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int c = in.nextInt();
        int l = in.nextInt();
        int[][] a = new int[c][2];
        int[] spf = new int[2505];
        for (int i = 0; i < c; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        for (int i = 0; i < l; i++) {
            spf[in.nextInt()] += in.nextInt();
        }
        Arrays.sort(a, (x, y) -> x[0] == y[0] ? y[1] - x[1] : y[0] - x[0]);
        int res = 0;
        for (int i = 0; i < c; i++) {
            for (int j = 2000; j >= 1; j--) {
                if (spf[j] == 0) {
                    continue;
                }
                if (j >= a[i][0] && j <= a[i][1]) {
                    spf[j]--;
                    res++;
                    break;
                }
            }
        }
        out.println(res);
    }
}
