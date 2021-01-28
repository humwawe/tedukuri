package ox34;

import common.io.InputReader;
import common.io.OutputWriter;

public class Fibonacci {
    int mod = (int) 1e4;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        if (n == -1) {
            return;
        }
        int[] f = new int[]{0, 1};
        int[][] a = new int[][]{{0, 1}, {1, 1}};

        while (n > 0) {
            if ((n & 1) == 1) {
                f = mul(f, a);
            }
            a = mulself(a);
            n >>= 1;
        }
        out.println(f[0]);
    }

    private int[][] mulself(int[][] a) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * a[k][j]) % mod);
                }
            }
        }
        return c;
    }

    private int[] mul(int[] f, int[][] a) {
        int[] c = new int[2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i] = (int) ((c[i] + (long) f[j] * a[j][i]) % mod);
            }
        }
        return c;
    }
}
