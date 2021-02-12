package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class MatrixPowerSeries {
    int n, k, p;
    int[][] a;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        p = in.nextInt();
        a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = in.nextInt();
            }
        }
        int[][] res = sum(a, k, p);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.print(res[i][j] + " ");
            }
            out.println();
        }
    }

    int[][] mul(int[][] a, int[][] b, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % p);
                }
            }
        }
        return c;
    }

    // 快速幂
    int[][] qp(int[][] a, int k, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            c[i][i] = 1;
        }
        while (k > 0) {
            if ((k & 1) == 1) {
                c = mul(c, a, p);
            }
            k >>= 1;
            a = mul(a, a, p);
        }
        return c;
    }

    // 求 a^1 + a^2 + a^3 + a^4 + .... + a^k
    // n为偶数：sum(n) = (a + a^(k/2))*sum(n/2)
    // 奇数：sum(n) = sum(n-1) + a^k，或者 a + a*sum(n-1) (可以少一次快速幂)
    int[][] sum(int[][] a, int k, int p) {
        if (k == 1) {
            return a;
        }
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            c[i][i] = 1;
        }
        c = add(c, qp(a, k >> 1, p), p);
        c = mul(c, sum(a, k >> 1, p), p);
        if ((k & 1) == 1) {
            c = add(c, qp(a, k, p), p);
        }
        return c;
    }

    // 矩阵加法
    int[][] add(int[][] a, int[][] b, int p) {
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = (a[i][j] + b[i][j]) % p;
            }
        }
        return c;
    }
}
