package ox53;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Polygon {
    int N = 110;
    char[] c = new char[N];
    int[] w = new int[N];
    int[][] dpMin = new int[N][N];
    int[][] dpMax = new int[N][N];
    int n;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            c[i] = in.nextCharacter();
            w[i] = in.nextInt();
            c[i + n] = c[i];
            w[i + n] = w[i];
        }

        for (int i = 0; i < N; i++) {
            Arrays.fill(dpMin[i], inf);
            Arrays.fill(dpMax[i], -inf);
        }
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < 2 * n; i++) {
                int j = i + len - 1;
                if (len == 1) {
                    dpMin[i][j] = w[i];
                    dpMax[i][j] = w[i];
                } else {
                    for (int k = i; k < j; k++) {
                        char op = c[k + 1];
                        int lMin = dpMin[i][k], lMax = dpMax[i][k];
                        int rMin = dpMin[k + 1][j], rMax = dpMax[k + 1][j];
                        if (op == 't') {
                            dpMin[i][j] = Math.min(dpMin[i][j], lMin + rMin);
                            dpMax[i][j] = Math.max(dpMax[i][j], lMax + rMax);
                        } else {
                            int x1 = lMin * rMin, x2 = lMin * rMax, x3 = lMax * rMax, x4 = lMax * rMin;
                            dpMin[i][j] = Math.min(dpMin[i][j], x1);
                            dpMin[i][j] = Math.min(dpMin[i][j], x2);
                            dpMin[i][j] = Math.min(dpMin[i][j], x3);
                            dpMin[i][j] = Math.min(dpMin[i][j], x4);
                            dpMax[i][j] = Math.max(dpMax[i][j], x1);
                            dpMax[i][j] = Math.max(dpMax[i][j], x2);
                            dpMax[i][j] = Math.max(dpMax[i][j], x3);
                            dpMax[i][j] = Math.max(dpMax[i][j], x4);
                        }
                    }
                }
            }
        }

        int res = -inf;
        for (int i = 0; i < n; ++i) {
            res = Math.max(res, dpMax[i][i + n - 1]);
        }
        out.println(res);

        for (int i = 0; i < n; ++i) {
            if (dpMax[i][i + n - 1] == res) {
                out.print(i + 1 + " ");
            }
        }
    }
}
